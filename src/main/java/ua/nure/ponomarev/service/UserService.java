package ua.nure.ponomarev.service;

import ua.nure.ponomarev.web.exception.DBException;
import ua.nure.ponomarev.entity.User;

/**
 * @author Bogdan_Ponamarev.
 */
public interface UserService {
    boolean isExistEmail(String email) throws DBException;

    boolean isExistPhoneNumber(String phoneNumber) throws DBException;

    int addUser(User user) throws DBException;

    boolean activateEmail(String email) throws DBException;
}
