package ua.nure.ponomarev.transaction;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.nure.ponomarev.exception.DbException;
import ua.nure.ponomarev.exception.LogicException;
import ua.nure.ponomarev.holder.SqlConnectionHolder;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Bogdan_Ponamarev.
 */
public class TransactionManager {

    private static DataSource dataSource;
    private final Logger logger = LogManager.getLogger(TransactionManager.class);

    public TransactionManager(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public <T> T doWithTransaction(TransactionOperation<T> transactionOperation) throws DbException {
        T result = null;
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            SqlConnectionHolder.setConnection(connection);
            if (connection == null) {
                throw new DbException("It can`t get connection", LogicException.ExceptionType.SERVER_EXCEPTION, new SQLException());
            }
            connection.setAutoCommit(false);
            result = transactionOperation.execute();
            connection.commit();
        } catch (SQLException e) {
            logger.error(e);
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException e1) {
                    logger.error(e1);
                }
            }
        } finally {
            close(connection);
        }
        return result;
    }

    public <T> T doWithoutTransaction(TransactionOperation<T> transactionOperation) throws DbException {
        T result = null;
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            SqlConnectionHolder.setConnection(connection);
            if (connection == null) {
                throw new DbException("It can`t get connection", LogicException.ExceptionType.SERVER_EXCEPTION, new SQLException());
            }
            result = transactionOperation.execute();
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            close(connection);
        }
        return result;
    }

    private void close(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            logger.error("Connection was not closed closed", e);
        }
    }
}
