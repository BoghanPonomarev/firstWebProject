package ua.nure.ponomarev.dao.api;

import ua.nure.ponomarev.web.exception.DBException;
import ua.nure.ponomarev.entity.User;

import java.sql.Connection;
import java.util.List;

/**
 * @author Bogdan_Ponamarev.
 */


public interface UserDao {
    boolean create(User user,Connection connection) throws DBException;

     User getBy(String login,Connection connection) throws DBException;

    boolean isExistEmail(String email,Connection connection) throws DBException;

    boolean isExistPhone(String phoneNumber,Connection connection) throws DBException;

    boolean activateEmail(String email,Connection connetion) throws DBException;

    List<User> getAll(Connection connection) throws DBException;
}
