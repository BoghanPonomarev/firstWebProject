package ua.nure.ponomarev.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.nure.ponomarev.criteria.UserCriteria;
import ua.nure.ponomarev.dao.AccountDao;
import ua.nure.ponomarev.entity.Account;
import ua.nure.ponomarev.exception.DBException;

import ua.nure.ponomarev.entity.Account.Card;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * @author Bogdan_Ponamarev.
 */
public class SqlAccountDao implements AccountDao {
    private static final String SQL_QUERY_DELETE = "DELETE FROM webproject.accounts WHERE id=?";
    private static final String SQL_SET_BAN_QUERY = "UPDATE webproject.accounts SET banned=? WHERE id=?";
    private static Logger logger = LogManager.getLogger(SqlUserDao.class);
    private SqlDaoConnectionManager connectionManager;
    private static final String SQL_GET_ACCOUNTS_BY_ID_QUERY = "SELECT * FROM webproject.accounts WHERE id in (SELECT account_id FROM webproject.users_accounts WHERE user_id=?)";
    private static final String SQL_GET_ACCOUNT_QUERY_TO_FILL = "SELECT * FROM webproject.accounts WHERE id in " +
            "(SELECT account_id FROM webproject.users_accounts WHERE user_id in " +
            "(SELECT id FROM webproject.users WHERE ";
    private static final String SQL_PUT_ACCOUNT_QUERY = "INSERT INTO webproject.accounts(card_number,card_end_date,CVV,card_amount) VALUES(?,?,?,?)";
    private static final String SQL_PUT_ACCOUNT_AND_USER_ID_QUERY = "INSERT INTO webproject.users_accounts(user_id,account_id) VALUES(?,?)";
    private static final String SQL_IS_EXIST_ACCOUNT_QUERY = "SELECT * FROM webproject.accounts WHERE id=? OR card_number=?";

    public SqlAccountDao(DataSource dataSource) {
        connectionManager = new SqlDaoConnectionManager(dataSource);
    }

    @Override
    public List<Account> getAll(UserCriteria userCriteria) throws DBException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Account> resultList = new ArrayList<>();
        try {
            Connection connection = connectionManager.getConnection();
            if (userCriteria.getCriteria().get("id") != null) {
                preparedStatement = connection.prepareStatement(SQL_GET_ACCOUNTS_BY_ID_QUERY);
                preparedStatement.setInt(1, Integer.parseInt(userCriteria.getCriteria().get("id")));
            } else {
                preparedStatement = connection.prepareStatement(createSelectQuery(userCriteria));
            }
            resultSet = preparedStatement.executeQuery();
            Account account;
            while ((account=fillAccount(resultSet))!=null) {
                resultList.add(account);
            }
        } catch (SQLException e) {
            logger.error("Can`t get user accounts " + e);
            throw new DBException("Can`t get user accounts", DBException.ExceptionType.SERVER_EXCEPTION, e);
        } finally {
            connectionManager.closeResultSet(resultSet);
            connectionManager.closePrepareStatement(preparedStatement);
        }
        return resultList;
    }

    private String createSelectQuery(UserCriteria userCriteria) {
        StringBuilder stringBuilder = new StringBuilder(SQL_GET_ACCOUNT_QUERY_TO_FILL);
        boolean isPrevious = false;
        Map<String, String> parameters = userCriteria.getCriteria();
        for (String key : parameters.keySet()) {
            if (parameters.get(key) != null) {
                if (isPrevious) {
                    stringBuilder.append(" and");
                }
                stringBuilder.append(" ").append(key).append("=\'").append(parameters.get(key)).append('\'');
                isPrevious = true;
            }
        }
        return stringBuilder.append("))").toString();
    }

    private Account fillAccount(ResultSet resultSet) throws SQLException {
        if(resultSet.next()) {
            Card tmpCard = new Card();
            tmpCard.setAmount(BigDecimal.valueOf(resultSet.getInt("card_amount")));
            tmpCard.setCardNumber(resultSet.getString("card_number"));
            tmpCard.setCVV(resultSet.getString("CVV"));
            tmpCard.setValidThru(resultSet.getDate("card_end_date").toLocalDate());
            return new Account(tmpCard, resultSet.getInt("id"),resultSet.getBoolean("banned"));
        }
        return null;
    }

    @Override
    public void put(Account account, int userId) throws DBException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            Connection connection = connectionManager.getConnection();
            preparedStatement = connection.prepareStatement(SQL_PUT_ACCOUNT_QUERY, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, account.getCard().getCardNumber());
            preparedStatement.setDate(2, Date.valueOf(account.getCard().getValidThru()));
            preparedStatement.setString(3, account.getCard().getCVV());
            preparedStatement.setDouble(4, account.getCard().getAmount().doubleValue());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                preparedStatement = connection.prepareStatement(SQL_PUT_ACCOUNT_AND_USER_ID_QUERY);
                preparedStatement.setInt(1, userId);
                preparedStatement.setInt(2, resultSet.getInt(1));
                preparedStatement.execute();
            } else {
                throw new DBException("There isn`t user or account with this id");
            }
        } catch (SQLException ex) {
            logger.error("Could not create account", ex);
            throw new DBException("Could not create account", DBException.ExceptionType.SERVER_EXCEPTION, ex);
        } finally {
            connectionManager.closeResultSet(resultSet);
            connectionManager.closePrepareStatement(preparedStatement);
        }
    }

    @Override
    public void setBanOfAccount(boolean value, int oldAccountId) throws DBException {
        PreparedStatement preparedStatement=null;
        try{
            Connection connection = connectionManager.getConnection();
            preparedStatement = connection.prepareStatement(SQL_SET_BAN_QUERY);
            preparedStatement.setBoolean(1,value);
            preparedStatement.setInt(2,oldAccountId);
            preparedStatement.execute();
        } catch (SQLException e) {
            logger.error("Can`t update account "+e);
            throw new DBException("Sorry , now we can`t update your account data");
        }finally {
            connectionManager.closePrepareStatement(preparedStatement);
        }
    }

    @Override
    public boolean isExist(int id, String cardNumber) throws DBException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            Connection connection = connectionManager.getConnection();
            preparedStatement = connection.prepareStatement(SQL_IS_EXIST_ACCOUNT_QUERY);
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, cardNumber);
            resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            logger.error("Can`t get connection " + e);
            throw new DBException("Can`t get account " + e);
        } finally {
            connectionManager.closeResultSet(resultSet);
            connectionManager.closePrepareStatement(preparedStatement);
        }
    }

    @Override
    public void delete(int id) throws DBException {
        PreparedStatement preparedStatement = null;
        try {
            Connection  connection = connectionManager.getConnection();
            preparedStatement = connection.prepareStatement(SQL_QUERY_DELETE);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            logger.error("Can`t delete account" + e);
            throw new DBException("Sorry but now we can`t delete your account,we give our apologise");
        }
        finally {
            connectionManager.closePrepareStatement(preparedStatement);
        }
    }
}
