package ua.nure.ponomarev.db.connectionPool;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.sql.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.*;

/**
 * @author Bogdan_Ponamarev.
 */
public class ConnectionPoolImpl implements ConnectionPool{
    private static final Logger logger = LogManager.getLogger(ConnectionPoolImpl.class);

    private static ConnectionPoolImpl instance;

    private BlockingQueue<Connection> freeConnections;

    private Set<Connection> copyConnections;

    private String driverName;
    private String url;
    private String user;
    private String password;

    private ConnectionPoolImpl(String driverName, String url, String user, String password, int initConnectionsNumber) {
        this.driverName = driverName;
        this.url = url;
        this.user = user;
        this.password = password;
        freeConnections = new ArrayBlockingQueue<Connection>(initConnectionsNumber);
        copyConnections = Collections.synchronizedSet(new HashSet<Connection>(initConnectionsNumber));
        initDriver();
        for(int i=0;i<initConnectionsNumber;i++) {
            newConnection();
        }
    }

    private void initDriver() {
        try {
            Driver driver = (Driver) Class.forName(driverName).newInstance();
            DriverManager.registerDriver(driver);
            logger.info("Driver was registered");
        } catch (Exception e) {
            logger.fatal("Driver was`nt registered");
        }
    }

    public static synchronized ConnectionPoolImpl getInstance(String DRIVER_NAME, String URL, String user, String password, int initConnectionsNumber) {
        if (instance == null) {
            instance = new ConnectionPoolImpl(DRIVER_NAME, URL, user, password,initConnectionsNumber);
        }
        return instance;
    }

    public Connection getConnection(){
        try {
            return freeConnections.poll(2, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            logger.info("New connection was created because pool ended");
            newConnection();
            return freeConnections.poll();
        }
    }
    private synchronized void newConnection(){
        try {
        if(user==null||password==null) {
             freeConnections.add(new HashConnection(DriverManager.getConnection(url)));
             copyConnections.add(freeConnections.peek());
        }
        else {
                freeConnections.add(new HashConnection(DriverManager.getConnection(url,user,password)));
                copyConnections.add(freeConnections.peek());
        }
        } catch (SQLException e) {
            logger.error("Can`t getByLogin connection from data base");
        }
    }

    public  void putConnection(Connection connection){
        if(connection!=null&& copyConnections.contains(connection)) {
            freeConnections.add(connection);
            logger.info("Connection was returned to pool");
        }
        else{
            logger.info("Connection was`nt returned to pool");
        }
    }
    public synchronized void closeAll(){
        Iterator<Connection> it = freeConnections.iterator();
        while(it.hasNext()){
            try {
                it.next().close();
            } catch (SQLException e) {
                logger.error("Connection has`nt been close");
            }
        }
        freeConnections.clear();
        copyConnections.clear();
    }
}
