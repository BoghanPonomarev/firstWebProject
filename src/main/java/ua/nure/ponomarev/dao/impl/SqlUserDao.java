package ua.nure.ponomarev.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.nure.ponomarev.criteria.UserCriteria;
import ua.nure.ponomarev.dao.SqlDaoConnectionManager;
import ua.nure.ponomarev.dao.UserDao;
import ua.nure.ponomarev.entity.User;
import ua.nure.ponomarev.exception.DbException;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Bogdan_Ponamarev.
 * Waiting for task(Need changing) WHEN YOU WILL WRITE MY_RES_SET -CHANGE THIS CLASS(CLOSE RESULT SET)
 */
public class SqlUserDao implements UserDao {
    private static final String SQL_UPDATE_QUERY_TO_FILL = "UPDATE webproject.users SET ";
    private static final String SQL_CREATE_QUERY = "INSERT INTO webproject.users (password,phone_number,language_id) VALUES (?,?,(SELECT id FROM webproject.languages WHERE name = 'en'))";
    private static final String SQL_SELECT_QUERY_TO_FILL = "SELECT * FROM webproject.users WHERE ";
    private static final String SQL_SET_LANGUAGE_QUERY = "UPDATE webproject.users SET language_id = (SELECT id FROM webproject.languages WHERE name = ?) WHERE id=?";
    public static final String SQL_GET_LANGUAGE_QUERY = "SELECT name FROM webproject.languages WHERE id in (SELECT language_id FROM webproject.users WHERE id = ?)";
    private static Logger logger = LogManager.getLogger(SqlUserDao.class);
    private SqlDaoConnectionManager connectionManager;

    public SqlUserDao(DataSource dataSource) {
        connectionManager = new SqlDaoConnectionManager(dataSource);
    }


    /**
     * @return integer number that is an id of particular user , or -1 if result set was empty
     * @throws DbException
     */
    @Override
    public int put(User user) throws DbException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            Connection connection = connectionManager.getConnection();
            preparedStatement = connection.prepareStatement(SQL_CREATE_QUERY, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, user.getPassword());
            preparedStatement.setString(2, user.getPhoneNumber());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException ex) {
            logger.error("Could not create user", ex);
            throw new DbException("Could not create user", DbException.ExceptionType.SERVER_EXCEPTION, ex);
        } finally {
            connectionManager.closeResultSet(resultSet);
            connectionManager.closePrepareStatement(preparedStatement);
        }
        return -1;
    }

    @Override
    public void setLanguage(int userId,Locale language) throws DbException {
        PreparedStatement preparedStatement = null;

        try {
            Connection connection = connectionManager.getConnection();
            preparedStatement = connection.prepareStatement(SQL_SET_LANGUAGE_QUERY);
            preparedStatement.setString(1, language.getLanguage());
            preparedStatement.setInt(2, userId);
            preparedStatement.execute();
        } catch (SQLException ex) {
            logger.error("Could not set language", ex);
            throw new DbException("Could not set language", DbException.ExceptionType.SERVER_EXCEPTION, ex);
        } finally {
            connectionManager.closePrepareStatement(preparedStatement);
        }
    }

    @Override
    public Locale getLanguage(int userId) throws DbException {
        PreparedStatement preparedStatement= null;
        ResultSet resultSet = null;
        try{
            Connection connection = connectionManager.getConnection();
            preparedStatement = connection.prepareStatement(SQL_GET_LANGUAGE_QUERY);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return new Locale(resultSet.getString(1));
        } catch (SQLException e) {
            logger.error("Could not get locale "+e);
            throw new DbException("Could not get locale ");
        }finally {
            connectionManager.closeResultSet(resultSet);
            connectionManager.closePrepareStatement(preparedStatement);
        }
    }

    @Override
    public void set(UserCriteria userCriteria, int oldUserID) throws DbException {
        PreparedStatement preparedStatement = null;
        try {
            Connection connection = connectionManager.getConnection();
            preparedStatement = connection.prepareStatement(createUpdateQuery(userCriteria, oldUserID));
            preparedStatement.execute();
        } catch (SQLException e) {
            logger.error("can`t update user" + e);
            throw new DbException("Currently, we can`t update your data");
        } finally {
            connectionManager.closePrepareStatement(preparedStatement);
        }
    }

    private String createUpdateQuery(UserCriteria userCriteria, int oldId) {
        StringBuilder stringBuilder = new StringBuilder(SQL_UPDATE_QUERY_TO_FILL);
        boolean isPrevious = false;
        Map<String, String> parameters = userCriteria.getCriteria();
        for (String key : parameters.keySet()) {
            if (parameters.get(key) != null) {
                if (isPrevious) {
                    stringBuilder.append(" , ");
                }
                stringBuilder.append(" ").append(key).append("=\'").append(parameters.get(key)).append('\'');
                isPrevious = true;
            }
        }
        return stringBuilder.append(" WHERE id=\'").append(oldId).append('\'').toString();
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

    /**
     * give user with special criteria but only one
     * if there are more users it will give the first
     *
     * @param userCriteria criteria of user
     * @return the first user, who is match to this criteria
     * @throws DbException if there is some wrong with DB
     */
    @Override
    public User get(UserCriteria userCriteria) throws DbException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            Connection connection = connectionManager.getConnection();
            preparedStatement = connection.prepareStatement(createSelectQuery(userCriteria));
            resultSet = preparedStatement.executeQuery();
            return fillUser(resultSet);
        } catch (SQLException ex) {
            logger.error("Could not get user", ex);
            throw new DbException("Could not get user", DbException.ExceptionType.SERVER_EXCEPTION, ex);
        } finally {
            connectionManager.closeResultSet(resultSet);
            connectionManager.closePrepareStatement(preparedStatement);
        }
    }

    private String createSelectQuery(UserCriteria userCriteria) {
        StringBuilder stringBuilder = new StringBuilder(SQL_SELECT_QUERY_TO_FILL);
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
        if (!isPrevious) {
            return stringBuilder.delete(stringBuilder.indexOf("WHERE"), stringBuilder.length() - 1).toString();
        }
        return stringBuilder.toString();
    }
    @Override
    public List<User> getAll(UserCriteria userCriteria,int startCount,int quantity) throws DbException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<User> resultUsers = new ArrayList<>();
        try {
            Connection connection = connectionManager.getConnection();
            preparedStatement = connection.prepareStatement(createSelectQuery(userCriteria) + " ORDER BY id LIMIT "
                    +startCount+","+quantity);
            resultSet = preparedStatement.executeQuery();
            User user;
            while ((user = fillUser(resultSet)) != null) {
                resultUsers.add(user);
            }
        } catch (SQLException ex) {
            logger.error("Could not get user", ex);
            throw new DbException("Could not get user", DbException.ExceptionType.SERVER_EXCEPTION, ex);
        } finally {
            connectionManager.closeResultSet(resultSet);
            connectionManager.closePrepareStatement(preparedStatement);
        }
        return resultUsers;
    }

    @Override
    public List<User> getAll(int startCount,int quantity) throws DbException {
        return getAll(new UserCriteria(new User()),startCount,quantity);
    }


}
