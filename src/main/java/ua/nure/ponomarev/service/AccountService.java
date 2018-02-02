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
    public enum SortStrategy{
        ID,
        NAME,
        BALANCE
    }
    List<Account> getAccounts(int userId,SortStrategy sortStrategy) throws DbException;

    List<Account> getRequestedAccounts(int page, SortStrategy sortStrategy)throws DbException;

    Account get(int accountId) throws DbException;

    void replenishAccount(BigDecimal amount,String currency,String accountName) throws DbException, CredentialException;

    boolean isExistCardNumber(String cardNumber) throws DbException;

    void putAccount(Account account, int userId) throws DbException, CredentialException;

    void delete(int accountId, String userPassword) throws DbException, CredentialException;

    void setBanValue(int accountId) throws DbException;

    void setRequestedValue(int accountId)throws DbException,CredentialException;
}
