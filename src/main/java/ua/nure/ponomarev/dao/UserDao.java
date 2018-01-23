package ua.nure.ponomarev.dao;

import ua.nure.ponomarev.criteria.UserCriteria;
import ua.nure.ponomarev.entity.User;
import ua.nure.ponomarev.exception.DbException;

import java.util.List;

/**
 * @author Bogdan_Ponamarev.
 */


public interface UserDao {
    int put(User user) throws DbException;

    void set(UserCriteria userCriteria, int oldUserID) throws DbException;

    User get(UserCriteria userCriteria) throws DbException;

    List<User> getAll() throws DbException;

    List<User> getAll(UserCriteria userCriteria) throws DbException;
}
