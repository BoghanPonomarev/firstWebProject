package ua.nure.ponomarev.service.impl;
import ua.nure.ponomarev.criteria.UserCriteria;
import ua.nure.ponomarev.dao.UserDao;
import ua.nure.ponomarev.exception.DBException;
import ua.nure.ponomarev.entity.User;
import ua.nure.ponomarev.exception.LogicException;
import ua.nure.ponomarev.service.UserService;
import ua.nure.ponomarev.transaction.TransactionManager;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Bogdan_Ponamarev.
 */
public class UserServiceImpl implements UserService {
    private TransactionManager transaction;
    private UserDao userDao;


    public UserServiceImpl(TransactionManager transaction, UserDao userDao) {
        this.transaction = transaction;
        this.userDao = userDao;
    }

    public boolean isCanEntry(String phoneNumber, String password) throws DBException {
        return transaction.doWithTransaction(() ->
                getUser(phoneNumber, password) != null);
    }

    /**
     * Method that check existing of particular phone number in data memory
     * without transaction
     * @param phoneNumber certain phone number
     * @return true if number exists in data memory
     * @throws DBException if there are some troubles with connection to data memory
     */
    private boolean isExistPhoneNumber(String phoneNumber) throws DBException {
            User user = new User();
            user.setPhoneNumber(phoneNumber);
            user = userDao.get(new UserCriteria(user));
            return user != null;
    }
    private boolean isExistEmail(String email) throws DBException {
            User user = new User();
            user.setEmail(email);
            user = userDao.get(new UserCriteria(user));
            return user != null;
    }

    @Override
    public int addUser(User user) throws DBException {
        return transaction.doWithoutTransaction(() ->{
                if(isExistPhoneNumber(user.getPhoneNumber())){
            throw new DBException("Phone number is already taken",DBException.ExceptionType.USER_EXCEPTION,new Exception());
        }
            if(isExistEmail(user.getEmail())){
                throw new DBException("Email is already taken",DBException.ExceptionType.USER_EXCEPTION,new Exception());
            }
                return userDao.put(user);});
    }

    /**
     * @param email email that must be activated
     * @return true if email exist in database regardless was activated
     * this email before or not,return false if there is not this email in database
     * @throws DBException if there is some problems with connection
     */
    @Override
    public boolean activateEmail(String email) throws DBException {
        return transaction.doWithTransaction(() -> userDao.activateEmail(email));
    }

    @Override
    public User getUser(String phoneNumber, String password) throws DBException {
        return transaction.doWithTransaction(() -> {
            User user = new User();
            user.setPhoneNumber(phoneNumber);
            user.setPassword(password);
            user.setPassword(password);
            return userDao.get(new UserCriteria(user));
        });
    }
}
