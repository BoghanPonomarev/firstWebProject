package ua.nure.ponomarev.service.impl;

import ua.nure.ponomarev.dao.api.UserDao;
import ua.nure.ponomarev.web.exception.DBException;
import ua.nure.ponomarev.entity.User;
import ua.nure.ponomarev.service.api.UserService;
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
        return transaction.doWithSingleTransaction((connection)-> {return userDao.isExistEmail(email,connection);});
    }

    @Override
    public boolean isExistPhoneNumber(String phoneNumber) throws DBException {
        return transaction.doWithSingleTransaction((connection -> {return userDao.isExistPhone(phoneNumber,connection);}));
    }

    @Override
    public boolean addUser(User user) throws DBException {
        return  transaction.doWithSingleTransaction((connection -> {return userDao.create(user,connection);}));
    }

    @Override
    public boolean activateEmail(String email) throws DBException {
        return  transaction.doWithSingleTransaction((connection -> {return userDao.activateEmail(email,connection);}));
    }
}
