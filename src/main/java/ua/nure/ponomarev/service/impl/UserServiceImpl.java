package ua.nure.ponomarev.service.impl;

import ua.nure.ponomarev.dao.UserDao;
import ua.nure.ponomarev.web.exception.DBException;
import ua.nure.ponomarev.entity.User;
import ua.nure.ponomarev.service.UserService;
import ua.nure.ponomarev.transactions.Transaction;

/**
 * @author Bogdan_Ponamarev.
 */
public class UserServiceImpl implements UserService {
    private Transaction transaction;
    private UserDao userDao;


    public UserServiceImpl(Transaction transaction,UserDao userDao){
        this.transaction = transaction;
        this.userDao = userDao;
    }
    @Override
    public boolean isExistEmail(String email) throws DBException {
        return transaction.doWithSingleTransaction(()->  userDao.isExistEmail(email));
    }

    @Override
    public boolean isExistPhoneNumber(String phoneNumber) throws DBException {
        return transaction.doWithSingleTransaction(() -> userDao.isExistPhone(phoneNumber));
    }

    @Override
    public int addUser(User user) throws DBException {
        return  transaction.doWithSingleTransaction(() -> userDao.create(user));
    }

    @Override
    public boolean activateEmail(String email) throws DBException {
        return  transaction.doWithSingleTransaction(() ->userDao.activateEmail(email));
    }
}
