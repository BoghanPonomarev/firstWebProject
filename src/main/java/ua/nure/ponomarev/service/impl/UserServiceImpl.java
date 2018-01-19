package ua.nure.ponomarev.service.impl;
import ua.nure.ponomarev.criteria.UserCriteria;
import ua.nure.ponomarev.dao.UserDao;
import ua.nure.ponomarev.exception.DBException;
import ua.nure.ponomarev.entity.User;
import ua.nure.ponomarev.exception.LogicException;
import ua.nure.ponomarev.hash.Hash;
import ua.nure.ponomarev.service.UserService;
import ua.nure.ponomarev.transaction.TransactionManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Bogdan_Ponamarev.
 */
public class UserServiceImpl implements UserService {
    private TransactionManager transaction;
    private UserDao userDao;
    private Hash hash;

    public UserServiceImpl(TransactionManager transaction, UserDao userDao,Hash hash) {
        this.transaction = transaction;
        this.userDao = userDao;
        this.hash=hash;
    }


    /**
     * Method that check existing of particular phone number in data memory
     * without transaction
     * @param phoneNumber certain phone number
     * @return true if number exists in data memory
     * @throws DBException if there are some troubles with connection to data memory
     */
    @Override
    public boolean isExistPhoneNumber(String phoneNumber) throws DBException {
          return  transaction.doWithoutTransaction(()-> {
                User user = new User();
                user.setPhoneNumber(phoneNumber);
                user = userDao.get(new UserCriteria(user));
                return user != null;
            });
    }
    @Override
    public boolean isExistEmail(String email) throws DBException {
            return transaction.doWithoutTransaction(()->{User user = new User();
            user.setEmail(email);
            user = userDao.get(new UserCriteria(user));
            return user != null;});
    }

    @Override
    public int add(User user) throws DBException {
        boolean isExistPhoneNumber = isExistPhoneNumber(user.getPhoneNumber());
        return transaction.doWithoutTransaction(() ->{
                if(isExistPhoneNumber){
            throw new DBException("Phone number is already taken",DBException.ExceptionType.USER_EXCEPTION,new Exception());
        }
            user.setPassword(hash.getHash(user.getPassword()));
                return userDao.put(user);});
    }

    /**
     * @param email email that must be activated
     * @return true if email exist in database regardless was activated
     * this email before or not,return false if there is not this email in database
     * @throws DBException if there is some problems with connection
     */
    @Override
    public boolean putEmail(String email,int id) throws DBException {
        return transaction.doWithTransaction(() -> {
            User user = new User();
            user.setEmail(email);
            userDao.set(new UserCriteria(user),id);
            return true;
        });
    }

    @Override
    public User getUser(String phoneNumber, String password) throws DBException {
        return transaction.doWithTransaction(() -> {
            User user = new User();
            user.setPhoneNumber(phoneNumber);
            user.setPassword(hash.getHash(password));
            return userDao.get(new UserCriteria(user));
        });
    }

    @Override
    public User getUser(int id) throws DBException {
        return transaction.doWithTransaction(()-> {
            User user=new User();
            user.setId(id);
            return userDao.get(new UserCriteria(user));
        });
    }

    @Override
    public void setBanValue(int userId, boolean value) throws DBException {
        transaction.doWithoutTransaction(()->{
            User user = new User();
            user.setBanned(value);
           userDao.set(new UserCriteria(user,true),userId);
           return null;
        });
    }

    public void setUserRole(int userId, User.Role role) throws DBException{
        transaction.doWithoutTransaction(
                ()-> {
                    User user = new User();
                    user.setRole(role);
                    userDao.set(new UserCriteria(user),userId);
                return null;
                });
    }

    @Override
    public List<User> getAll() throws DBException {
        return transaction.doWithTransaction(()-> userDao.getAll());
    }
}
