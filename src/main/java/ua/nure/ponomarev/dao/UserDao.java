package ua.nure.ponomarev.dao;

import ua.nure.ponomarev.criteria.UserCriteria;
import ua.nure.ponomarev.exception.DBException;
import ua.nure.ponomarev.entity.User;

import java.util.List;

/**
 * @author Bogdan_Ponamarev.
 */


public interface UserDao {
    int put(User user) throws DBException;

    void set(UserCriteria userCriteria,int oldUserID) throws DBException;

     User get(UserCriteria userCriteria) throws DBException;

    List<User> getAll() throws DBException;

    List<User> getAll(UserCriteria userCriteria) throws DBException;
}
