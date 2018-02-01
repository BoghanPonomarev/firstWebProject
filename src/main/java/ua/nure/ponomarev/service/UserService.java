package ua.nure.ponomarev.service;

import ua.nure.ponomarev.entity.User;
import ua.nure.ponomarev.exception.CredentialException;
import ua.nure.ponomarev.exception.DbException;

import java.util.List;
import java.util.Locale;

/**
 * @author Bogdan_Ponamarev.
 */
public interface UserService {

    int add(User user) throws DbException, CredentialException;

    void putEmail(String email, int id) throws DbException, CredentialException;

    User getFullUser(User user) throws DbException, CredentialException;

    void setBanValue(int userId, boolean value) throws DbException;

    void setUserRole(int userId, User.Role role) throws DbException;

    List<User> getAll(User.Role requesterRole,int page,boolean isOnlyBanned) throws DbException;

    List<User> getAll(User.Role requesterRole,int page) throws DbException;

    void setLanguage(int userId,Locale locale) throws DbException;

    Locale getLanguage(int userId) throws DbException;
}
