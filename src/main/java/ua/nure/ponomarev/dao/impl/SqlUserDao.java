package ua.nure.ponomarev.dao.impl;

import ua.nure.ponomarev.dao.UserDao;
import ua.nure.ponomarev.holder.SqlConnectionHolder;
import ua.nure.ponomarev.web.exception.DBException;
import ua.nure.ponomarev.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 * @author Bogdan_Ponamarev.
 * Waiting for task(Need changing) WHEN YOU WILL WRITE MY_RES_SET -CHANGE THIS CLASS(CLOSE RESULT SET)
 */
public class SqlUserDao implements UserDao {

    private static final String SQL_QUERY_CREATE = "INSERT INTO webproject.users (login,password,phone_number,email) VALUES (?,?,?,?)";
    private static final String SQL_QUERY_SELECT_BY_LOGIN = "SELECT * FROM webproject.users WHERE login=?";
    private static final String SQL_QUERY_IS_EXIST_EMAIL = "SELECT * FROM webproject.users WHERE email=?";
    private static final String SQL_QUERY_IS_EXIST_PHONE_NUMBER = "SELECT * FROM webproject.users WHERE phone_number=?";
    private static final String SQL_QUERY_GET_ALL = "SELECT * FROM webproject.users";
    private static final String SQL_QUERY_ACTIVATE_EMAIL = "SET webproject.users is_activated_email = 1 WHERE email = ?";
    private Logger logger = LogManager.getLogger(SqlUserDao.class);
    private DataSource dataSource;

    public SqlUserDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private Connection getConnection() throws SQLException {

        Connection connection =  SqlConnectionHolder.getConnection();
        if(connection==null){
            connection = dataSource.getConnection();
        }
        return connection;
    }

    /**
     *
     * @return integer number that is an id of particular user , or -1 if result set was empty
     * @throws DBException
     */
    @Override
    public int create(User user) throws DBException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet=null;
        try {
            Connection connection = getConnection();
            preparedStatement = connection.prepareStatement(SQL_QUERY_CREATE, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getPhoneNumber());
            preparedStatement.setString(4, user.getEmail());
             preparedStatement.executeUpdate();
             resultSet = preparedStatement.getGeneratedKeys();
             if(resultSet.next()){
                 return resultSet.getInt(1);
             }
        } catch (SQLException ex) {
            logger.error("Could not create user", ex);
            throw new DBException(ex);
        } finally {
            closeResultSet(resultSet);
            closePrepareStatement(preparedStatement);
        }
        return -1;
    }

    private User fillUser(ResultSet resultSet) throws DBException {
        User user = null;
        try {
            if (resultSet.next()) {
                user = new User();
                user.setLogin(resultSet.getString(2));
                user.setPassword(resultSet.getString(3));
                user.setPhoneNumber(resultSet.getString(4));
                user.setEmail(resultSet.getString(5));
            }
        } catch (SQLException ex) {
            logger.error("Something wrong with data filling", ex);
            throw new DBException(ex);
        }
        return user;
    }

    @Override
    public User get(String login) throws DBException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet=null;
        try {
            Connection connection = getConnection();
            preparedStatement = connection.prepareStatement(SQL_QUERY_SELECT_BY_LOGIN);
            preparedStatement.setString(1, login);
           resultSet = preparedStatement.executeQuery();
            return fillUser(resultSet);
        } catch (SQLException ex) {
            logger.error("Could not get user", ex);
            throw new DBException(ex);
        } finally {
            closeResultSet(resultSet);
           closePrepareStatement(preparedStatement);
        }
    }
    @Override
    public boolean isExistEmail(String email) throws DBException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet= null;
        try {
            Connection connection = getConnection();
            preparedStatement = connection.prepareStatement(SQL_QUERY_IS_EXIST_EMAIL);
            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        }
        catch (SQLException ex) {
            logger.error("Could not find user", ex);
            throw new DBException(ex);
        }
        finally {
         closeResultSet(resultSet);
           closePrepareStatement(preparedStatement);
        }
    }
    @Override
    public boolean isExistPhone(String phoneNumber) throws DBException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet= null;
        try {
            Connection connection = getConnection();
            preparedStatement = connection.prepareStatement(SQL_QUERY_IS_EXIST_PHONE_NUMBER);
            preparedStatement.setString(1, phoneNumber);
            resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        }
        catch (SQLException ex) {
            logger.error("Could not find user", ex);
            throw new DBException(ex);
        }
        finally {
          closeResultSet(resultSet);
           closePrepareStatement(preparedStatement);
        }
    }

    @Override
    public boolean activateEmail(String email) throws DBException {
        PreparedStatement preparedStatement= null;
        try {
            Connection connection = getConnection();
            preparedStatement=connection.prepareStatement(SQL_QUERY_ACTIVATE_EMAIL);
            preparedStatement.setString(1,email);
            return preparedStatement.executeUpdate()!=0;
        } catch (SQLException e) {
            logger.error("Email was`nt activated",e);
            throw new DBException(e);
        }
        finally {
            closePrepareStatement(preparedStatement);
        }
    }

    @Override
    public List<User> getAll() throws DBException {
        List<User> list = new ArrayList<>();
        ResultSet resultSet = null;
        PreparedStatement preparedStatement=null;
        try {
            Connection connection = getConnection();
            preparedStatement=connection.prepareStatement(SQL_QUERY_GET_ALL);
            resultSet = preparedStatement.executeQuery();
            User user = null;
            while ((user = fillUser(resultSet)) != null) {
                list.add(user);
            }
        }
        catch (SQLException ex) {
            logger.error("Could not get users", ex);
            throw new DBException(ex);
        }
        finally {
         closeResultSet(resultSet);
         closePrepareStatement(preparedStatement);
        }
        return list;
    }

    private void closeResultSet(ResultSet resultSet) throws DBException {
        try {
            if(resultSet!=null) {
                resultSet.close();
            }
        } catch (SQLException e) {
            logger.error("Result set has`nt been closed");
            throw  new DBException(e);
        }
    }
    private void closePrepareStatement(PreparedStatement preparedStatement){
        if(preparedStatement!=null){
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                logger.error("PreparedStatement was not closed",e);
            }
        }
    }
}
