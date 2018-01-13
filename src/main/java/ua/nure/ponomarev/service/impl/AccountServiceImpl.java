package ua.nure.ponomarev.service.impl;

import ua.nure.ponomarev.criteria.UserCriteria;
import ua.nure.ponomarev.dao.AccountDao;
import ua.nure.ponomarev.entity.Account;
import ua.nure.ponomarev.entity.User;
import ua.nure.ponomarev.exception.DBException;
import ua.nure.ponomarev.service.AccountService;
import ua.nure.ponomarev.transaction.TransactionManager;

import java.util.List;

/**
 * @author Bogdan_Ponamarev.
 */
public class AccountServiceImpl implements AccountService {
    private TransactionManager transactionManager;
    private AccountDao accountDao;

    public AccountServiceImpl(AccountDao accountDao, TransactionManager transactionManager) {
        this.accountDao = accountDao;
        this.transactionManager = transactionManager;
    }

    @Override
    public List<Account> getAccounts(int id) throws DBException {
        return transactionManager.doWithTransaction(() -> {
            User user = new User();
            user.setId(id);
            return accountDao.getAccounts(new UserCriteria(user));
        });
    }

    @Override
    public List<Account> getAccounts(String PhoneNumber) throws DBException {
        return transactionManager.doWithTransaction(() -> {
            User user = new User();
            user.setPhoneNumber(PhoneNumber);
            return accountDao.getAccounts(new UserCriteria(user));
        });
    }

    @Override
    public void putAccount(Account account, int userId) throws DBException {
        transactionManager.doWithoutTransaction(() -> {

            accountDao.putAccount(account, userId);
            return true;
        });
    }

    @Override
    public boolean isValidAccount(int accountId,String cardNumber) throws DBException {
        return transactionManager.doWithoutTransaction(() -> !accountDao.isExist(accountId,cardNumber));
    }

    @Override
    public void delete(int accountId) throws DBException {
        transactionManager.doWithoutTransaction(() -> {
            accountDao.delete(accountId);
            return true;
        });
    }
}
