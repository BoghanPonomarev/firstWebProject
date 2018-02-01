package ua.nure.ponomarev.dao;

import ua.nure.ponomarev.criteria.AccountCriteria;
import ua.nure.ponomarev.criteria.UserCriteria;
import ua.nure.ponomarev.entity.Account;
import ua.nure.ponomarev.entity.User;
import ua.nure.ponomarev.exception.DbException;

import java.util.List;

/**
 * @author Bogdan_Ponamarev.
 */
public interface AccountDao {
    List<Account> getAll(UserCriteria userCriteria,String sortedColumn) throws DbException;

    List<Account> getAll(AccountCriteria accountCriteria,String sortedColumn) throws DbException;

    User getUser(int accountId) throws DbException;

    Account getAccount(AccountCriteria accountCriteria) throws DbException;

    void put(Account account, int userId) throws DbException;

    void setAccount(AccountCriteria accountCriteria, int oldAccountId)throws DbException;

    void delete(int id) throws DbException;

}
