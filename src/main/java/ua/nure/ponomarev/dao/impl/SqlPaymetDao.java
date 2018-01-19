package ua.nure.ponomarev.dao.impl;

import java.math.BigDecimal;
import java.sql.*;

import org.apache.logging.log4j.Logger;
import ua.nure.ponomarev.dao.PaymentDao;
import ua.nure.ponomarev.entity.Payment;
import ua.nure.ponomarev.exception.DBException;

import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

/**
 * @author Bogdan_Ponamarev.
 */
public class SqlPaymetDao implements PaymentDao{
    private static final String SQL_QUERY_INSERT = "INSERT INTO webproject.payments(date,amount,user_id,account_id,sender,recipient) VALUES (?,?,?,?,?,?)";
    private static final String SQL_QUERY_SELECT_PAYMENTS = "SELECT * FROM webproject.payments WHERE user_id=?";
    private static Logger logger;
    private SqlDaoConnectionManager connectionManager;
    public SqlPaymetDao(DataSource dataSource){
        connectionManager = new SqlDaoConnectionManager(dataSource);
    }
    public void put(Payment payment) throws DBException {
        PreparedStatement preparedStatement=null;
        try{
            Connection connection = connectionManager.getConnection();
          preparedStatement = connection.prepareStatement(SQL_QUERY_INSERT);
          preparedStatement.setTimestamp(1, Timestamp.valueOf(payment.getDate()));
          preparedStatement.setDouble(2,payment.getAmount().doubleValue());
          preparedStatement.setInt(3,payment.getUserId());
          preparedStatement.setInt(4,payment.getAccountId());
          preparedStatement.setString(5,payment.getSender());
          preparedStatement.setString(6,payment.getRecipient());
          preparedStatement.execute();
        } catch (SQLException e) {
            logger.error("Can`t put payment "+e);
            throw new DBException("Dear user now we can`t process any operation,we give our apologise");
        }
        finally {
            connectionManager.closePrepareStatement(preparedStatement);
        }
    }

    public List<Payment> getAll(int userId) throws DBException {
        PreparedStatement preparedStatement=null;
        ResultSet resultSet =null;
        List<Payment> resultList = new ArrayList<>();
        try{
            Connection connection=connectionManager.getConnection();
            preparedStatement = connection.prepareStatement(SQL_QUERY_SELECT_PAYMENTS);
            preparedStatement.setInt(1,userId);
            resultSet = preparedStatement.executeQuery();
            Payment payment;
            while((payment=fillPayment(resultSet))!=null){
                resultList.add(payment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            connectionManager.closeResultSet(resultSet);
            connectionManager.closePrepareStatement(preparedStatement);
        }
        return resultList;
    }
    private Payment fillPayment(ResultSet resultSet) throws SQLException {
        if(resultSet.next()){
            return new Payment().builder().accountId(resultSet.getInt("id"))
                    .amount(BigDecimal.valueOf(resultSet.getDouble("amount")))
                    .date(resultSet.getTimestamp("date").toLocalDateTime())
                    .userId(resultSet.getInt("user_id"))
                    .accountId(resultSet.getInt("account_id"))
                    .recipient(resultSet.getString("recipient"))
                    .sender(resultSet.getString("sender")).build();

        }
        return null;
    }
}
