package ua.nure.ponomarev.transactions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.nure.ponomarev.holder.SqlConnectionHolder;
import ua.nure.ponomarev.web.exception.DBException;
import ua.nure.ponomarev.web.exception.LogicException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author Bogdan_Ponamarev.
 */
public class Transaction{

    private final Logger logger = LogManager.getLogger(Transaction.class);

    private static DataSource dataSource;

    public Transaction(DataSource dataSource){
        this.dataSource = dataSource;
    }

    public <T> Iterator<T> doWithTransaction(TransactionOperation<T> []  transactionOperation) throws DBException {
        ArrayList<T> result=new ArrayList<>();
        Connection connection=null;
        try{
           connection = dataSource.getConnection();
           SqlConnectionHolder.setConnection(connection);
           if(connection==null){
            throw new DBException("It can`t getByLogin connection", LogicException.ExceptionType.SERVER_EXCEPTION,new SQLException());
           }
           connection.setAutoCommit(false);
           for(int i=0;i<transactionOperation.length;i++) {
                result.add(transactionOperation[i].doWitTransaction());
           }
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
        return result.iterator();
    }
    public <T> T doWithSingleTransaction(TransactionOperation<T>  transactionOperation) throws DBException {
        T result=null;
        Connection connection=null;
        try{
            connection = dataSource.getConnection();
            SqlConnectionHolder.setConnection(connection);
            if(connection==null){
                throw new DBException("It can`t getByLogin connection", LogicException.ExceptionType.SERVER_EXCEPTION,new SQLException());
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
