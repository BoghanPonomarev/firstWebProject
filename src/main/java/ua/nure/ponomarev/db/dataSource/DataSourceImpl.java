package ua.nure.ponomarev.db.dataSource;

import ua.nure.ponomarev.db.connectionPool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * @author Bogdan_Ponamarev.
 */
public class DataSourceImpl implements  DataSource{
    private static  Logger logger = LogManager.getLogger(DataSourceImpl.class);
    private static ConnectionPool connectionPool;


    public DataSourceImpl() {
    }

    private synchronized void initConnectionPool(){
            /*
        Correct when jndi will be joined
         */
            connectionPool = ConnectionPool.getInstance("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/web_store?characterEncoding=utf8", "root", "root", 5);
    }
    public synchronized void initConnectionPool(ConnectionPool connectionPool){
            /*
        Correct when jndi will be joined
         */
        this.connectionPool = connectionPool;
    }
    public Connection getConnection(){
        if(connectionPool==null){
            initConnectionPool();
        }
        logger.info("User got their connection");
        return connectionPool.getConnection();
    }

    public void close(Connection connection){
        connectionPool.putConnection(connection);
    }

    public void close(PreparedStatement preparedStatement){}

}
