package ua.nure.ponomarev.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.nure.ponomarev.criteria.UserCriteria;
import ua.nure.ponomarev.dao.AccountDao;
import ua.nure.ponomarev.dao.PaymentDao;
import ua.nure.ponomarev.dao.SqlDaoConnectionManager;
import ua.nure.ponomarev.entity.Account;
import ua.nure.ponomarev.entity.Payment;
import ua.nure.ponomarev.entity.User;
import ua.nure.ponomarev.exception.DbException;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Bogdan_Ponamarev.
 */
public class SqlPaymentDao implements PaymentDao {
    private static Logger logger = LogManager.getLogger(SqlPaymentDao.class);
    private static final String SQL_INSERT_QUERY = "INSERT INTO webproject.payments(date,amount,recipient_account_id,sender_account_id,currency_id,type,status) VALUES " +
            "(?,?,?,?,(SELECT id FROM webproject.currency WHERE name=?),?,?)";
    private static final String SQL_GET_QUERY = "SELECT * FROM webproject.payments WHERE id =?";
    private static final String SQL_DELETE_PAYMENT_QUERY = "DELETE FROM webproject.payments WHERE id=?";
    private SqlDaoConnectionManager connectionManager;
    private AccountDao accountDao;

    public SqlPaymentDao(DataSource dataSource,AccountDao accountDao) {

        connectionManager = new SqlDaoConnectionManager(dataSource);
        this.accountDao =accountDao;
    }

    @Override
    public Payment get(int id) throws DbException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            Connection connection = connectionManager.getConnection();
            preparedStatement = connection.prepareStatement(SQL_GET_QUERY);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            return fillPayment(resultSet, connection);
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
        ResultSet resultSet = null;
        try {
            Connection connection = connectionManager.getConnection();
            preparedStatement = connection.prepareStatement(SQL_INSERT_QUERY, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setTimestamp(1, Timestamp.valueOf(payment.getDate()));
            preparedStatement.setDouble(2, payment.getAmount().doubleValue());
            preparedStatement.setInt(3, payment.getRecipientId());
            preparedStatement.setInt(4, payment.getSenderId());
            preparedStatement.setString(5, payment.getCurrency());
            preparedStatement.setString(6, payment.getType().name());
            preparedStatement.setString(7, payment.getStatus().name());
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

    private String createGetAllQuery(int userId, int startCount, int quantity, boolean onlyReadyPayments,String sortedColumn) throws DbException {
        User user = new User();
        user.setId(userId);
        StringBuilder stringBuilder = new StringBuilder("SELECT * FROM webproject.payments WHERE ");
        boolean isPrevious = false;
        for (Account account : accountDao.getAll(new UserCriteria(user),"Id")) {
            if (isPrevious) {
                stringBuilder.append(" OR ");
            }
            stringBuilder.append("sender_account_id=").append(account.getId());
            isPrevious = true;
        }
        if (onlyReadyPayments) {
            return stringBuilder.append(" AND ").append("STATUS='PREPARED' ORDER BY ").append(sortedColumn).append(" LIMIT").append(startCount)
                    .append(',').append(quantity).toString();
        } else {
            return stringBuilder.append(" ORDER BY ").append(sortedColumn).append(" LIMIT ").append(startCount)
                    .append(',').append(quantity).toString();
        }
    }

    @Override
    public List<Payment> getAll(int userId, int startCount, int quantity,String sortedColumn) throws DbException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Payment> resultList = new ArrayList<>();
        try {
            Connection connection = connectionManager.getConnection();
            preparedStatement = connection.prepareStatement(createGetAllQuery(userId, startCount, quantity, false,sortedColumn));
            resultSet = preparedStatement.executeQuery();
            Payment payment;
            while ((payment = fillPayment(resultSet, connection)) != null) {
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

    @Override
    public List<Payment> getAll(int userId, int startCount, int quantity,String sortedColumn , boolean onlyReadyPayments) throws DbException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Payment> resultList = new ArrayList<>();
        try {
            Connection connection = connectionManager.getConnection();
            preparedStatement = connection.prepareStatement(createGetAllQuery(userId, startCount, quantity, true,sortedColumn));
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, startCount);
            preparedStatement.setInt(3, quantity);
            resultSet = preparedStatement.executeQuery();
            Payment payment;
            while ((payment = fillPayment(resultSet, connection)) != null) {
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

    private Payment fillPayment(ResultSet resultSet, Connection connection) throws SQLException, DbException {
        if (resultSet.next()) {
            PreparedStatement currencyNameStatement = connection.prepareStatement("SELECT name FROM currency WHERE id='"
                    + resultSet.getInt("currency_id") + "'");
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
