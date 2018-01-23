package ua.nure.ponomarev.service.impl;

import ua.nure.ponomarev.criteria.AccountCriteria;
import ua.nure.ponomarev.criteria.UserCriteria;
import ua.nure.ponomarev.dao.AccountDao;
import ua.nure.ponomarev.entity.Account;
import ua.nure.ponomarev.entity.User;
import ua.nure.ponomarev.exception.CredentialException;
import ua.nure.ponomarev.exception.DbException;
import ua.nure.ponomarev.hash.HashGenerator;
import ua.nure.ponomarev.service.AccountService;
import ua.nure.ponomarev.transaction.TransactionManager;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Bogdan_Ponamarev.
 */
public class AccountServiceImpl implements AccountService {
    private TransactionManager transactionManager;
    private AccountDao accountDao;
    private HashGenerator hashGenerator;

    public AccountServiceImpl(AccountDao accountDao, TransactionManager transactionManager
            , HashGenerator hashGenerator) {
        this.accountDao = accountDao;
        this.transactionManager = transactionManager;
        this.hashGenerator = hashGenerator;
    }

    @Override
    public List<Account> getAccounts(int userId) throws DbException {
        return transactionManager.doWithTransaction(() -> {
            User user = new User();
            user.setId(userId);
            return accountDao.getAll(new UserCriteria(user));
        });
    }

    @Override
    public boolean isExistCardNumber(String cardNumber) throws DbException {
        return transactionManager.doWithTransaction(() -> {
            Account account = new Account(0, new Account.Card().builder()
                    .cardNumber(cardNumber).build());
            return accountDao.getAccount(new AccountCriteria(account, false,false)) != null;
        });

    }

    @Override
    public void putAccount(Account account, int userId) throws DbException, CredentialException {
        List<String> errors = new ArrayList<>();
        if (isExistAccount(account.getId(), account.getCard().getCardNumber())) {
            errors.add("Card with this card number is already exist");
        }
        if (!errors.isEmpty()) {
            throw new CredentialException(errors);
        }
        transactionManager.doWithoutTransaction(() -> {
            accountDao.put(account, userId);
            return null;
        });
    }

    private boolean isExistAccount(int accountId, String cardNumber) throws DbException {
        return transactionManager.doWithoutTransaction(() -> {
            Account account = new Account(accountId, new Account.Card().builder()
                    .cardNumber(cardNumber).build());
            return accountDao.getAccount(new AccountCriteria(account, false,false)) != null;
        });
    }

    @Override
    public void delete(int accountId, String password) throws DbException, CredentialException {
        List<String> errors = new ArrayList<>();
        transactionManager.doWithoutTransaction(() -> {
            if (!accountDao.getUser(accountId).getPassword().equals(hashGenerator.generateHash(password))) {
                errors.add("Passwords not equals");
            } else {
                accountDao.delete(accountId);
            }
            return null;
        });
        if (!errors.isEmpty()) {
            throw new CredentialException(errors);
        }
    }

    @Override
    public void setBanValue(int accountId, boolean value) throws DbException {
        transactionManager.doWithoutTransaction(() -> {
            accountDao.setBanOfAccount(value, accountId);
            return null;
        });
    }
}
