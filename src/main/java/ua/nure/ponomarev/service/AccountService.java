package ua.nure.ponomarev.service;

import ua.nure.ponomarev.entity.Account;
import ua.nure.ponomarev.exception.CredentialException;
import ua.nure.ponomarev.exception.DbException;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Bogdan_Ponamarev.
 */
public interface AccountService {
    List<Account> getAccounts(int userId) throws DbException;

    boolean isExistCardNumber(String cardNumber) throws DbException;

    void putAccount(Account account, int userId) throws DbException, CredentialException;

    void delete(int accountId, String userPassword) throws DbException, CredentialException;

    void setBanValue(int accountId, boolean value) throws DbException;

    void changeAmount(int AccountId, BigDecimal sum) throws DbException;
}
