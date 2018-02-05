package ua.nure.ponomarev.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.nure.ponomarev.criteria.AccountCriteria;
import ua.nure.ponomarev.criteria.UserCriteria;
import ua.nure.ponomarev.dao.AccountDao;
import ua.nure.ponomarev.dao.SqlDaoConnectionManager;
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
    private static Logger logger = LogManager.getLogger(SqlUserDao.class);
    private SqlDaoConnectionManager connectionManager;
    private static final String SQL_INSERT_CARD_QUERY = "INSERT INTO webproject.cards(number,end_date,CVV) VALUES (?,?,?)";
    private static final String SQL_QUERY_DELETE = "DELETE FROM webproject.accounts WHERE id=?";
    private static final String SQL_GET_ACCOUNT_QUERY_TO_FILL = "SELECT * FROM webproject.accounts WHERE id in " +
            "(SELECT account_id FROM webproject.users_accounts WHERE user_id in " +
            "(SELECT id FROM webproject.users WHERE ";
    private static final String SQL_PUT_ACCOUNT_QUERY = "INSERT INTO webproject.accounts(name,balance,currency_id,card_id)" +
            " VALUES(?,?,(SELECT id FROM webproject.currency WHERE name=?),?)";
    private static final String SQL_PUT_ACCOUNT_AND_USER_ID_QUERY = "INSERT INTO webproject.users_accounts(user_id,account_id) VALUES(?,?)";
    private static final String SQL_SELECT_USER_BY_ACCOUNT_ID_QUERY = "SELECT * FROM webproject.users WHERE id in(SELECT user_id FROM webproject.users_accounts WHERE account_id=?)";

    public SqlAccountDao(DataSource dataSource) {
        connectionManager = new SqlDaoConnectionManager(dataSource);
    }

    @Override
    public List<Account> getAll(UserCriteria userCriteria,String sortedColumn) throws DbException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Account> resultList = new ArrayList<>();
        try {
            Connection connection = connectionManager.getConnection();
            preparedStatement = connection.prepareStatement(createSelectUserQuery(userCriteria)+ "ORDER BY "+ sortedColumn);
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
    public List<Account> getAll(AccountCriteria accountCriteria,String sortedColumn) throws DbException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Account> resultAccounts = new ArrayList<>();
        try {
            Connection connection = connectionManager.getConnection();
            preparedStatement = connection.prepareStatement(createSelectQuery(accountCriteria)+ "ORDER BY "+ sortedColumn);
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
    public List<Account> getAll(AccountCriteria accountCriteria, String sortedColumn, int start, int quantity) throws DbException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Account> resultAccounts = new ArrayList<>();
        try {
            Connection connection = connectionManager.getConnection();
            preparedStatement = connection.prepareStatement(createSelectQuery(accountCriteria)+
                    "ORDER BY "+ sortedColumn + " LIMIT " + start + ","+quantity);
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
                if (key.equals("card_number")) {
                    stringBuilder.append(' ').append("card_id")
                            .append(" in (SELECT id FROM webproject.cards WHERE number='")
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
                    stringBuilder.append(" and ");
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
            ResultSet card = connection.prepareStatement(
                    "SELECT * FROM webproject.cards WHERE id=\'"+resultSet.getInt("card_id")+"\'")
                    .executeQuery();
            card.next();
            Account account = new Account().builder()
                    .balance(BigDecimal.valueOf(resultSet.getInt("balance")))
                    .card(new Card(card.getString("number"),card.getDate("end_date").toLocalDate()
                            ,card.getString("CVV")))
                    .currency(currencyId.getString("name"))
                    .id( resultSet.getInt("id")).name(resultSet.getString("name"))
                    .isBanned(resultSet.getBoolean("is_banned"))
                    .isRequestedForUnban(resultSet.getBoolean("is_requested_for_unban")).build();
            connectionManager.closeResultSet(currencyId);
            connectionManager.closeResultSet(card);
            connectionManager.closePrepareStatement(preparedStatement);
                return account;
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
                user.setRole(User.Role.valueOf(resultSet.getString(5)));
                user.setBanned(resultSet.getBoolean(6));
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
            int id = createCard(account, connection);
            preparedStatement = connection.prepareStatement(SQL_PUT_ACCOUNT_QUERY, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, account.getName());
            preparedStatement.setBigDecimal(2, account.getBalance());
            preparedStatement.setString(3, account.getCurrency());
            preparedStatement.setInt(4, id);
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

    private int createCard(Account account, Connection connection) throws SQLException, DbException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        preparedStatement = connection.prepareStatement(SQL_INSERT_CARD_QUERY, PreparedStatement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, account.getCard().getCardNumber());
        preparedStatement.setDate(2, Date.valueOf(account.getCard().getValidThru()));
        preparedStatement.setString(3, account.getCard().getCVV());
        preparedStatement.executeUpdate();
        resultSet = preparedStatement.getGeneratedKeys();
        try {
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
            else {
                return -1;
            }
        }finally {
            connectionManager.closeResultSet(resultSet);
            connectionManager.closePrepareStatement(preparedStatement);
        }
    }

    @Override
    public void setAccount(AccountCriteria accountCriteria, int oldAccountId) throws DbException {
        PreparedStatement preparedStatement = null;
        try {
            Connection connection = connectionManager.getConnection();
            preparedStatement = connection.prepareStatement(createUpdateQuery(accountCriteria, oldAccountId));
            preparedStatement.execute();
        } catch (SQLException e) {
            logger.error("Could not update account " + e);
            throw new DbException("Could not update account");
        } finally {
            connectionManager.closePrepareStatement(preparedStatement);
        }
    }

    private String createUpdateQuery(AccountCriteria accountCriteria, int oldId) {
        StringBuilder stringBuilder = new StringBuilder("UPDATE webproject.accounts SET ");
        boolean isPrevious = false;
        Map<String, String> parameters = accountCriteria.getCriteria();
        for (String key : parameters.keySet()) {
            if (parameters.get(key) != null) {
                if (isPrevious) {
                    stringBuilder.append(" , ");
                }
                if (key.equals("currency_id")) {
                    stringBuilder.append(' ').append(key)
                            .append(" = (SELECT id FROM webproject.currency WHERE name='")
                            .append(parameters.get(key))
                            .append("\')");
                    continue;
                }
                if (key.equals("card_number")) {
                    isPrevious = false;
                    continue;
                }
                stringBuilder.append(" ").append(key).append("=\'").append(parameters.get(key)).append('\'');
                isPrevious = true;
            }
        }
        return stringBuilder.append(" WHERE id=\'").append(oldId).append('\'').toString();
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
            logger.error("Can`t remove account" + e);
            throw new DbException("Sorry but now we can`t remove your account,we give our apologise");
        } finally {
            connectionManager.closePrepareStatement(preparedStatement);
        }
    }

}
