package ua.nure.ponomarev.service;

import ua.nure.ponomarev.exception.DBException;
import ua.nure.ponomarev.entity.User;
import java.util.List;
/**
 * @author Bogdan_Ponamarev.
 */
public interface UserService {

    int add(User user) throws DBException;

    boolean putEmail(String email,int id) throws DBException;

    User getUser(String phoneNumber,String password)throws DBException;

    User getUser(int id) throws DBException;

    void setBanValue(int userId,boolean value) throws DBException;

    void setUserRole(int userId, User.Role role) throws DBException;

    List<User> getAll() throws DBException;

    boolean isExistPhoneNumber(String phoneNumber) throws DBException;

    boolean isExistEmail(String email) throws DBException;
}
