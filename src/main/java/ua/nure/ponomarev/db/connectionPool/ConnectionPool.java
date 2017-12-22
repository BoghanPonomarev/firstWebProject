package ua.nure.ponomarev.db.connectionPool;

import java.sql.Connection;

/**
 * @author Bogdan_Ponamarev.
 */
public interface ConnectionPool {
     static ConnectionPoolImpl getInstance(String DRIVER_NAME, String URL, String user, String password, int initConnectionsNumber){
         return null;
     }
    Connection getConnection();

    void putConnection(Connection connection);

     void closeAll();
}
