package ua.nure.ponomarev.dao.impl;

import org.apache.logging.log4j.Logger;
import ua.nure.ponomarev.dao.PaymentDao;
import ua.nure.ponomarev.dao.SqlDaoConnectionManager;
import ua.nure.ponomarev.entity.Payment;
import ua.nure.ponomarev.exception.DbException;

import javax.sql.DataSource;
import java.awt.*;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Bogdan_Ponamarev.
 */
public class SqlPaymetDao implements PaymentDao {
    private static final String SQL_QUERY_INSERT = "INSERT INTO webproject.payments(date,amount,recipient_account_id,sender_account_id,currency_id,type,status) VALUES " +
            "(?,?,?,?,(SELECT id FROM webproject.currency WHERE name=?),?,?)";
    private static final String SQL_QUERY_SELECT_PAYMENTS = "SELECT * FROM webproject.payments WHERE recipient_account_id=? OR sender_account_id=?";
    private static final String SQL_SET_STATUS_QUERY = "UPDATE webproject.payments SET status=? WHERE id=?";
    private static final String SQL_GET_QUERY = "SELECT * FROM webproject.payments WHERE id =?";
    public static final String SQL_DELETE_PAYMENT_QUERY = "DELETE FROM webproject.payments WHERE id=?";
    private static Logger logger;
    private SqlDaoConnectionManager connectionManager;

    public SqlPaymetDao(DataSource dataSource) {
        connectionManager = new SqlDaoConnectionManager(dataSource);
    }

    @Override
    public Payment get(int id) throws DbException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet= null;
        try {
            Connection connection = connectionManager.getConnection();
            preparedStatement = connection.prepareStatement(SQL_GET_QUERY);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            return fillPayment(resultSet,connection);
        } catch (SQLException e) {
            logger.error("Can`t put payment " + e);
            throw new DbException("Dear user now we can`t process any operation,we give our apologise");
        } finally {
            connectionManager.closePrepareStatement(preparedStatement);
            connectionManager.closeResultSet(resultSet);
        }
    }

    public int put(Payment payment) throws DbException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet =null;
        try {
            Connection connection = connectionManager.getConnection();
            preparedStatement = connection.prepareStatement(SQL_QUERY_INSERT,PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setTimestamp(1, Timestamp.valueOf(payment.getDate()));
            preparedStatement.setDouble(2, payment.getAmount().doubleValue());
            preparedStatement.setInt(3, payment.getRecipientId());
            preparedStatement.setInt(4, payment.getSenderId());
            preparedStatement.setString(5,payment.getCurrency());
            preparedStatement.setString(6,payment.getType().name());
            preparedStatement.setString(7,payment.getStatus().name());
            preparedStatement.execute();
            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            logger.error("Can`t put payment " + e);
            throw new DbException("Dear user now we can`t process any operation,we give our apologise");
        } finally {
            connectionManager.closeResultSet(resultSet);
            connectionManager.closePrepareStatement(preparedStatement);
        }
        return -1;
    }

    @Override
    public void deletePayment(int paymentId) throws DbException {
        PreparedStatement preparedStatement = null;
        try {
            Connection connection = connectionManager.getConnection();
            preparedStatement = connection.prepareStatement(SQL_DELETE_PAYMENT_QUERY);
            preparedStatement.setInt(1, paymentId);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionManager.closePrepareStatement(preparedStatement);
        }
    }

    public List<Payment> getAll(int userId) throws DbException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Payment> resultList = new ArrayList<>();
        try {
            Connection connection = connectionManager.getConnection();
            preparedStatement = connection.prepareStatement(SQL_QUERY_SELECT_PAYMENTS);
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, userId);
            resultSet = preparedStatement.executeQuery();
            Payment payment;
            while ((payment = fillPayment(resultSet,connection)) != null) {
                resultList.add(payment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionManager.closeResultSet(resultSet);
            connectionManager.closePrepareStatement(preparedStatement);
        }
        return resultList;
    }

    private Payment fillPayment(ResultSet resultSet,Connection connection) throws SQLException, DbException {
        if (resultSet.next()) {
            PreparedStatement currencyNameStatement = connection.prepareStatement("SELECT name FROM currency WHERE id='"
                    +resultSet.getInt("currency_id")+"'");
            ResultSet currencyNameResult = currencyNameStatement.executeQuery();
            currencyNameResult.next();
            String currencyName = currencyNameResult.getString("name");
            connectionManager.closeResultSet(currencyNameResult);
            connectionManager.closePrepareStatement(currencyNameStatement);
            return new Payment().builder().id(resultSet.getInt("id"))
                    .amount(BigDecimal.valueOf(resultSet.getDouble("amount")))
                    .date(resultSet.getTimestamp("date").toLocalDateTime())
                    .recipientId(resultSet.getInt("recipient_account_id"))
                    .senderId(resultSet.getInt("sender_account_id"))
                    .currency(currencyName).type(Payment.Type.valueOf(resultSet.getString("type")))
                    .status(Payment.Status.valueOf(resultSet.getString("status"))).build();
        }
        return null;
    }
}
