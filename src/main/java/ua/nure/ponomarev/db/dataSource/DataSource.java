package ua.nure.ponomarev.db.dataSource;

import ua.nure.ponomarev.db.connectionPool.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * @author Bogdan_Ponamarev.
 */
public interface DataSource {

    Connection getConnection();

    void close(Connection connection);

    void close(PreparedStatement preparedStatement);

     void initConnectionPool(ConnectionPool connectionPool);

}
