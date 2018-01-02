package ua.nure.ponomarev.dao;

import ua.nure.ponomarev.web.exception.DBException;
import ua.nure.ponomarev.entity.User;

import java.sql.Connection;
import java.util.List;

/**
 * @author Bogdan_Ponamarev.
 */


public interface UserDao {
    int create(User user) throws DBException;

     User get(String login) throws DBException;

    boolean isExistEmail(String email) throws DBException; //Question how realize it

    boolean isExistPhone(String phoneNumber) throws DBException;

    boolean activateEmail(String email) throws DBException;

    List<User> getAll() throws DBException;
}
