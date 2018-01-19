package ua.nure.ponomarev.service;

import ua.nure.ponomarev.entity.Account;
import ua.nure.ponomarev.exception.DBException;

import java.util.List;

/**
 * @author Bogdan_Ponamarev.
 */
public interface AccountService {
    List<Account> getAccounts(int id) throws DBException;

    List<Account> getAccounts(String PhoneNumber) throws DBException;

    void putAccount(Account account, int userId) throws DBException;

    boolean isExistAccount(int id,String cardNumber) throws DBException;

    void delete(int accountId) throws DBException;

    void setBanValue(int accountId,boolean value) throws DBException;
}
