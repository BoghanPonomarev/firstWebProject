package ua.nure.ponomarev.holder;

import java.sql.Connection;

/**
 * @author Bogdan_Ponamarev.
 */
public class SqlConnectionHolder {
    private static ThreadLocal<Connection> threadLocal = new ThreadLocal<>();

    public static Connection getConnection() {
        return threadLocal.get();
    }

    public static void setConnection(Connection connection) {
        if (connection != null) {
            threadLocal.set(connection);
        }
    }
}
