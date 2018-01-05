package ua.nure.ponomarev.service.impl;

import ua.nure.ponomarev.criteria.UserCriteria;
import ua.nure.ponomarev.dao.UserDao;
import ua.nure.ponomarev.exception.DBException;
import ua.nure.ponomarev.entity.User;
import ua.nure.ponomarev.service.UserService;
import ua.nure.ponomarev.transaction.TransactionManager;

import javax.sql.DataSource;

/**
 * @author Bogdan_Ponamarev.
 */
public class UserServiceImpl implements UserService {
    private TransactionManager transaction;
    private UserDao userDao;


    public UserServiceImpl(TransactionManager transaction, UserDao userDao){
        this.transaction = transaction;
        this.userDao = userDao;
    }
    @Override
    public boolean isExistEmail(String email) throws DBException {
        return transaction.doWithTransaction(()->  {
            User user = new User();
            user.setEmail(email);
            user = userDao.get(new UserCriteria(user));
            return user!=null;
        });
    }

    @Override
    public boolean isExistPhoneNumber(String phoneNumber) throws DBException {
        return transaction.doWithTransaction(()->  {
            User user = new User();
            user.setPhoneNumber(phoneNumber);
            user = userDao.get(new UserCriteria(user));
            return user!=null;
        });
    }
    public boolean isCanEntry(String phoneNumber,String password) throws DBException{
        return transaction.doWithTransaction(()->  {
            User user = new User();
            user.setPhoneNumber(phoneNumber);
            user.setPassword(password);
            user = userDao.get(new UserCriteria(user));
            return user!=null;
        });
    }
    @Override
    public int addUser(User user) throws DBException {
        return  userDao.put(user);
    }

    /**
     *
     * @param email email that must be activated
     * @return true if email exist in database regardless was activated
     * this email before or not,return false if there is not this email in database
     * @throws DBException if there is some problems with connection
     */
    @Override
    public boolean activateEmail(String email) throws DBException {
        return  transaction.doWithTransaction(() ->userDao.activateEmail(email));
    }
}
