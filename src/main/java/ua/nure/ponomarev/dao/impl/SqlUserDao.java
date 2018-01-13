package ua.nure.ponomarev.dao.impl;

import ua.nure.ponomarev.criteria.UserCriteria;
import ua.nure.ponomarev.dao.UserDao;
import ua.nure.ponomarev.exception.LogicException;
import ua.nure.ponomarev.holder.SqlConnectionHolder;
import ua.nure.ponomarev.exception.DBException;
import ua.nure.ponomarev.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Bogdan_Ponamarev.
 * Waiting for task(Need changing) WHEN YOU WILL WRITE MY_RES_SET -CHANGE THIS CLASS(CLOSE RESULT SET)
 */
public class SqlUserDao implements UserDao {
    private static Logger logger = LogManager.getLogger(SqlUserDao.class);
    private static final String SQL_QUERY_CREATE = "INSERT INTO webproject.users (login,password,phone_number,email) VALUES (?,?,?,?)";
    private static final String SQL_SELECT_QUERY = "SELECT * FROM webproject.users WHERE";
    private static final String SQL_QUERY_GET_ALL = "SELECT * FROM webproject.users";
    private static final String SQL_QUERY_ACTIVATE_EMAIL = "UPDATE webproject.users SET is_activated_email=false WHERE email = ?";
    private DataSource dataSource;
    private SqlDaoConnectionManager connectionManager;

    public SqlUserDao(DataSource dataSource) {
        this.dataSource = dataSource;
        connectionManager = new SqlDaoConnectionManager(dataSource);
    }


    /**
     * @return integer number that is an id of particular user , or -1 if result set was empty
     * @throws DBException
     */
    @Override
    public int put(User user) throws DBException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            Connection connection = connectionManager.getConnection();
            preparedStatement = connection.prepareStatement(SQL_QUERY_CREATE, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getPhoneNumber());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException ex) {
            logger.error("Could not create user", ex);
            throw new DBException("Could not create user",DBException.ExceptionType.SERVER_EXCEPTION,ex);
        } finally {
            connectionManager.closeResultSet(resultSet);
            connectionManager.closePrepareStatement(preparedStatement);
        }
        return -1;
    }

    private User fillUser(ResultSet resultSet) throws DBException {
        User user = null;
        try {
            if (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getInt(1));
                user.setLogin(resultSet.getString(2));
                user.setPassword(resultSet.getString(3));
                user.setPhoneNumber(resultSet.getString(4));
                user.setEmail(resultSet.getString(5));
                user.setActiveEmail(resultSet.getBoolean(6));
            }
        } catch (SQLException ex) {
            logger.error("Something wrong with data filling", ex);
            throw new DBException("Something wrong with data filling",DBException.ExceptionType.SERVER_EXCEPTION,ex);
        }
        return user;
    }

    @Override
    public User get(UserCriteria userCriteria) throws DBException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            Connection connection = connectionManager.getConnection();
            preparedStatement = connection.prepareStatement(createSelectQuery(userCriteria));
            resultSet = preparedStatement.executeQuery();
            return fillUser(resultSet);
        } catch (SQLException ex) {
            logger.error("Could not get user", ex);
            throw new DBException("Could not get user",DBException.ExceptionType.SERVER_EXCEPTION,ex);
        } finally {
            connectionManager.closeResultSet(resultSet);
            connectionManager.closePrepareStatement(preparedStatement);
        }
    }

    private String createSelectQuery(UserCriteria userCriteria) {
        StringBuilder stringBuilder = new StringBuilder(SQL_SELECT_QUERY);
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
        return stringBuilder.toString();
    }

    @Override
    public boolean activateEmail(String email) throws DBException {
        PreparedStatement preparedStatement = null;
        try {
            Connection connection = connectionManager.getConnection();
            preparedStatement = connection.prepareStatement(SQL_QUERY_ACTIVATE_EMAIL);
            preparedStatement.setString(1, email);
            return preparedStatement.executeUpdate() != 0;
        } catch (SQLException e) {
            logger.error("Email was`nt activated", e);
            throw new DBException("Email was`nt activated",DBException.ExceptionType.SERVER_EXCEPTION,e);
        } finally {
            connectionManager.closePrepareStatement(preparedStatement);
        }
    }

    @Override
    public List<User> getAll() throws DBException {
        List<User> list = new ArrayList<>();
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            Connection connection = connectionManager.getConnection();
            preparedStatement = connection.prepareStatement(SQL_QUERY_GET_ALL);
            resultSet = preparedStatement.executeQuery();
            User user;
            while ((user = fillUser(resultSet)) != null) {
                list.add(user);
            }
        } catch (SQLException ex) {
            logger.error("Could not get users", ex);
            throw new DBException("Could not get users",DBException.ExceptionType.SERVER_EXCEPTION,ex);
        } finally {
            connectionManager.closeResultSet(resultSet);
            connectionManager.closePrepareStatement(preparedStatement);
        }
        return list;
    }


}
