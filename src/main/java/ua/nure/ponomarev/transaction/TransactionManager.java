package ua.nure.ponomarev.transaction;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.nure.ponomarev.holder.SqlConnectionHolder;
import ua.nure.ponomarev.exception.DBException;
import ua.nure.ponomarev.exception.LogicException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Bogdan_Ponamarev.
 */
public class TransactionManager {

    private final Logger logger = LogManager.getLogger(TransactionManager.class);

    private static DataSource dataSource;

    public TransactionManager(DataSource dataSource){
        this.dataSource = dataSource;
    }

    public <T> T doWithTransaction(TransactionOperation<T>  transactionOperation) throws DBException {
        T result=null;
        Connection connection=null;
        try{
            connection = dataSource.getConnection();
            SqlConnectionHolder.setConnection(connection);
           if(connection==null){
                throw new DBException("It can`t get connection", LogicException.ExceptionType.SERVER_EXCEPTION,new SQLException());
            }
            connection.setAutoCommit(false);
            result = transactionOperation.doWitTransaction();
            connection.commit();
        } catch (SQLException e) {
            logger.error(e);
            if(connection!=null){
                try {
                    connection.rollback();
                } catch (SQLException e1) {
                    logger.error(e1);
                }
            }
        }
        finally {
            close(connection);
        }
        return result;
    }
    private void close(Connection connection){
        try {
            if(connection!=null) {
                connection.close();
            }
        } catch (SQLException e) {
            logger.error("Connection was not closed closed",e);
        }
    }
}
