package ua.nure.ponomarev.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.nure.ponomarev.criteria.AccountCriteria;
import ua.nure.ponomarev.criteria.UserCriteria;
import ua.nure.ponomarev.dao.AccountDao;
import ua.nure.ponomarev.entity.Account;
import ua.nure.ponomarev.entity.Account.Card;
import ua.nure.ponomarev.entity.User;
import ua.nure.ponomarev.exception.DbException;

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
    private static final String SQL_GET_ACCOUNT_QUERY_TO_FILL = "SELECT * FROM webproject.accounts WHERE id in " +
            "(SELECT account_id FROM webproject.users_accounts WHERE user_id in " +
            "(SELECT id FROM webproject.users WHERE ";
    private static final String SQL_PUT_ACCOUNT_QUERY = "INSERT INTO webproject.accounts(card_number,card_end_date,CVV,name,card_amount,currency_id)" +
            " VALUES(?,?,?,?,?,(SELECT id FROM webproject.currency WHERE name=?))";
    private static final String SQL_PUT_ACCOUNT_AND_USER_ID_QUERY = "INSERT INTO webproject.users_accounts(user_id,account_id) VALUES(?,?)";
    private static final String SQL_SELECT_USER_BY_ACCOUNT_ID_QUERY = "SELECT * FROM webproject.users WHERE id in(SELECT user_id FROM webproject.users_accounts WHERE account_id=?)";
    private static Logger logger = LogManager.getLogger(SqlUserDao.class);
    private SqlDaoConnectionManager connectionManager;

    public SqlAccountDao(DataSource dataSource) {
        connectionManager = new SqlDaoConnectionManager(dataSource);
    }

    @Override
    public List<Account> getAll(UserCriteria userCriteria) throws DbException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Account> resultList = new ArrayList<>();
        try {
            Connection connection = connectionManager.getConnection();
            preparedStatement = connection.prepareStatement(createSelectUserQuery(userCriteria));
            resultSet = preparedStatement.executeQuery();
            Account account;
            while ((account = fillAccount(resultSet, connection)) != null) {
                resultList.add(account);
            }
        } catch (SQLException e) {
            logger.error("Can`t get user accounts " + e);
            throw new DbException("Can`t get user accounts", DbException.ExceptionType.SERVER_EXCEPTION, e);
        } finally {
            connectionManager.closeResultSet(resultSet);
            connectionManager.closePrepareStatement(preparedStatement);
        }
        return resultList;
    }

    @Override
    public List<Account> getAll(AccountCriteria accountCriteria) throws DbException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Account> resultAccounts = new ArrayList<>();
        try {
            Connection connection = connectionManager.getConnection();
            preparedStatement = connection.prepareStatement(createSelectQuery(accountCriteria));
            resultSet = preparedStatement.executeQuery();
            Account account;
            while ((account = fillAccount(resultSet, connection)) != null) {
                resultAccounts.add(account);
            }
        } catch (SQLException e) {
            logger.error("Can`nt get all accounts" + e);
            throw new DbException("Trouble refer with data base");
        }
        return resultAccounts;
    }

    @Override
    public User getUser(int accountId) throws DbException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            Connection connection = connectionManager.getConnection();
            preparedStatement = connection.prepareStatement(SQL_SELECT_USER_BY_ACCOUNT_ID_QUERY);
            preparedStatement.setInt(1, accountId);
            resultSet = preparedStatement.executeQuery();
            return fillUser(resultSet);
        } catch (SQLException e) {
            logger.error("Can`t give user by id " + e);
            throw new DbException("Data base or connection problem");
        } finally {
            connectionManager.closeResultSet(resultSet);
            connectionManager.closePrepareStatement(preparedStatement);
        }
    }

    @Override
    public Account getAccount(AccountCriteria accountCriteria) throws DbException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            Connection connection = connectionManager.getConnection();
            preparedStatement = connection.prepareStatement(createSelectQuery(accountCriteria));
            resultSet = preparedStatement.executeQuery();
            return fillAccount(resultSet, connection);
        } catch (SQLException e) {
            logger.error("Can`t get connection " + e);
            throw new DbException("Can`t get account " + e);
        } finally {
            connectionManager.closeResultSet(resultSet);
            connectionManager.closePrepareStatement(preparedStatement);
        }
    }

    private String createSelectQuery(AccountCriteria accountCriteria) {
        StringBuilder stringBuilder = new StringBuilder("SELECT * FROM webproject.accounts ").append((!accountCriteria.getCriteria().isEmpty()) ? " WHERE " : "");
        boolean isPrevious = false;
        Map<String, String> parameters = accountCriteria.getCriteria();
        for (String key : parameters.keySet()) {
            if (parameters.get(key) != null) {
                if (isPrevious) {
                    stringBuilder.append(" AND  ");
                }
                if (key.equals("currency_id")) {
                    stringBuilder.append(' ').append(key)
                            .append(" in (SELECT id FROM webproject.currency WHERE name='")
                            .append(parameters.get(key))
                            .append("\')");
                    continue;
                }
                stringBuilder.append(' ').append(key).append("=\'").append(parameters.get(key)).append('\'');
                isPrevious = true;
            }
        }
        return stringBuilder.toString();
    }


    private String createSelectUserQuery(UserCriteria userCriteria) {
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

    private Account fillAccount(ResultSet resultSet, Connection connection) throws SQLException, DbException {
        if (resultSet.next()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT name " +
                    "FROM webproject.currency WHERE id=\'" + resultSet.getInt("currency_id") + "\'");
            ResultSet currencyId = preparedStatement.executeQuery();
            currencyId.next();
            Card tmpCard = new Account.Card().builder()
                    .amount(BigDecimal.valueOf(resultSet.getInt("card_amount")))
                    .cardNumber(resultSet.getString("card_number"))
                    .validThru(resultSet.getDate("card_end_date").toLocalDate())
                    .CVV(resultSet.getString("CVV"))
                    .currency(currencyId.getString(1)).build();
            connectionManager.closeResultSet(currencyId);
            connectionManager.closePrepareStatement(preparedStatement);
            return new Account(tmpCard, resultSet.getInt("id")
                    , resultSet.getString("name")
                    , resultSet.getBoolean("banned")
            , resultSet.getBoolean("is_requested_for_unban"));
        }
        return null;
    }

    private User fillUser(ResultSet resultSet) throws DbException {
        User user = null;
        try {
            if (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getInt(1));
                user.setPassword(resultSet.getString(2));
                user.setPhoneNumber(resultSet.getString(3));
                user.setEmail(resultSet.getString(4));
                user.setFirstName(resultSet.getString(5));
                user.setSecondName(resultSet.getString(6));
                user.setThirdName(resultSet.getString(7));
                user.setRole(User.Role.valueOf(resultSet.getString(8)));
                user.setBanned(resultSet.getBoolean(9));
            }
        } catch (SQLException ex) {
            logger.error("Something wrong with data filling", ex);
            throw new DbException("Something wrong with data filling", DbException.ExceptionType.SERVER_EXCEPTION, ex);
        }
        return user;
    }

    @Override
    public void put(Account account, int userId) throws DbException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            Connection connection = connectionManager.getConnection();
            preparedStatement = connection.prepareStatement(SQL_PUT_ACCOUNT_QUERY, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, account.getCard().getCardNumber());
            preparedStatement.setDate(2, Date.valueOf(account.getCard().getValidThru()));
            preparedStatement.setString(3, account.getCard().getCVV());
            preparedStatement.setString(4, account.getName());
            preparedStatement.setDouble(5, account.getCard().getAmount().doubleValue());
            preparedStatement.setString(6, account.getCard().getCurrency());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                preparedStatement = connection.prepareStatement(SQL_PUT_ACCOUNT_AND_USER_ID_QUERY);
                preparedStatement.setInt(1, userId);
                preparedStatement.setInt(2, resultSet.getInt(1));
                preparedStatement.execute();
            } else {
                throw new DbException("There isn`t user or account with this id");
            }
        } catch (SQLException ex) {
            logger.error("Could not create account", ex);
            throw new DbException("Could not create account", DbException.ExceptionType.SERVER_EXCEPTION, ex);
        } finally {
            connectionManager.closeResultSet(resultSet);
            connectionManager.closePrepareStatement(preparedStatement);
        }
    }

    @Override
    public void setBanOfAccount(boolean value, int oldAccountId) throws DbException {
        PreparedStatement preparedStatement = null;
        try {
            Connection connection = connectionManager.getConnection();
            preparedStatement = connection.prepareStatement(SQL_SET_BAN_QUERY);
            preparedStatement.setBoolean(1, value);
            preparedStatement.setInt(2, oldAccountId);
            preparedStatement.execute();
        } catch (SQLException e) {
            logger.error("Can`t update account " + e);
            throw new DbException("Sorry , now we can`t update your account data");
        } finally {
            connectionManager.closePrepareStatement(preparedStatement);
        }
    }


    @Override
    public void delete(int id) throws DbException {
        PreparedStatement preparedStatement = null;
        try {
            Connection connection = connectionManager.getConnection();
            preparedStatement = connection.prepareStatement(SQL_QUERY_DELETE);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            logger.error("Can`t delete account" + e);
            throw new DbException("Sorry but now we can`t delete your account,we give our apologise");
        } finally {
            connectionManager.closePrepareStatement(preparedStatement);
        }
    }

}
