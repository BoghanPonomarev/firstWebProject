package ua.nure.ponomarev.service.impl;

import ua.nure.ponomarev.criteria.AccountCriteria;
import ua.nure.ponomarev.criteria.UserCriteria;
import ua.nure.ponomarev.currency.CurrencyManager;
import ua.nure.ponomarev.dao.AccountDao;
import ua.nure.ponomarev.entity.Account;
import ua.nure.ponomarev.entity.User;
import ua.nure.ponomarev.exception.CredentialException;
import ua.nure.ponomarev.exception.DbException;
import ua.nure.ponomarev.hash.HashGenerator;
import ua.nure.ponomarev.holder.RequestedAccountHolder;
import ua.nure.ponomarev.service.AccountService;
import ua.nure.ponomarev.transaction.TransactionManager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Bogdan_Ponamarev.
 */
public class AccountServiceImpl implements AccountService {
    private TransactionManager transactionManager;
    private AccountDao accountDao;
    private HashGenerator hashGenerator;
    private RequestedAccountHolder requestedAccountHolder;
    private static final int MAX_SUM_OF_ACCOUNT = 1000000;
    private static final int QUANTITY_OF_ONE_USER_PAGE = 5;
    private CurrencyManager currencyManager;

    public AccountServiceImpl(AccountDao accountDao, TransactionManager transactionManager
            , HashGenerator hashGenerator, CurrencyManager currencyManager
            , RequestedAccountHolder requestedAccountHolder) {
        this.accountDao = accountDao;
        this.transactionManager = transactionManager;
        this.hashGenerator = hashGenerator;
        this.currencyManager = currencyManager;
        this.requestedAccountHolder = requestedAccountHolder;
    }

    @Override
    public List<Account> getAccounts(int userId, SortStrategy sortStrategy) throws DbException {
        return transactionManager.doWithTransaction(() -> {
            User user = new User();
            user.setId(userId);
            return accountDao.getAll(new UserCriteria(user), getSortedColumn(sortStrategy));
        });
    }

    private String getSortedColumn(SortStrategy sortStrategy) {
        String sortedColumn = "id";
        if (sortStrategy != null) {
            if (sortStrategy == SortStrategy.BALANCE) {
                sortedColumn = "balance";
            }
            if (sortStrategy == SortStrategy.NAME) {
                sortedColumn = "name";
            }
        }
        return sortedColumn;
    }

    @Override
    public List<Account> getRequestedAccounts(int page, SortStrategy sortStrategy) throws DbException {
        return transactionManager.doWithTransaction(() -> {
            Account account = new Account();
            account.setRequestedForUnban(true);
            return accountDao.getAll(new AccountCriteria(account, false, true)
                    , getSortedColumn(sortStrategy)
                    , (page - 1) * QUANTITY_OF_ONE_USER_PAGE, QUANTITY_OF_ONE_USER_PAGE);
        });
    }

    @Override
    public Account get(int accountId) throws DbException {
        return transactionManager.doWithTransaction(() -> {
            Account account = new Account(accountId, null);
            return accountDao.getAccount(new AccountCriteria(account, false, false));
        });
    }

    @Override
    public void replenishAccount(BigDecimal amount, String currency, String accountName) throws DbException, CredentialException {
        List<String> errors = new ArrayList<>();
        try {
            transactionManager.doWithTransaction(() -> {
                Account account = new Account(0, new Account.Card());
                account.setName(accountName);
                account = accountDao.getAccount(new AccountCriteria(account, false, false));
                if (account == null) {
                    errors.add("Account dose`nt exist");
                    throw new DbException("Thrown exception");
                }
                BigDecimal sum = amount;
                if (!account.getCurrency().equals(currency)) {
                    sum = currencyManager.convertCurrency(amount, currency, account.getCurrency());
                }
                if (account.getBalance().add(sum).doubleValue() > (double) MAX_SUM_OF_ACCOUNT) {
                    errors.add("Your account can not be processed because you reached the maximum amount of money on your account");
                    throw new DbException("Thrown exception");
                }
                account.setBalance(account.getBalance().add(sum));
                accountDao.setAccount(new AccountCriteria(account, false, false), account.getId());
                return null;
            });
        } catch (DbException e) {
            if (e.getMessage().equals("Thrown exception")) {
                throw new CredentialException(errors);
            } else {
                throw e;
            }
        }

    }

    @Override
    public boolean isExistCardNumber(String cardNumber) throws DbException {
        return transactionManager.doWithTransaction(() -> {
            Account.Card card = new Account.Card();
            card.setCardNumber(cardNumber);
            Account account = new Account(0, card);
            return accountDao.getAccount(new AccountCriteria(account, false, false)) != null;
        });

    }

    @Override
    public void putAccount(Account account, int userId) throws DbException, CredentialException {
        List<String> errors = new ArrayList<>();
        if (isExistAccount(account.getId(), account.getCard().getCardNumber())) {
            errors.add("Card with this card number is already exist");
        }
        Account accountName = transactionManager.doWithTransaction(() -> {
            Account accountNameCheck = new Account();
            accountNameCheck.setName(account.getName());
            return accountDao.getAccount(new AccountCriteria(accountNameCheck, false, false));
        });
        for (Account ac : getAccounts(userId, SortStrategy.ID)) {
            if (accountName.getName().equals(ac.getName())) {
                errors.add("You almost have account with this name");
            }
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
            Account.Card card = new Account.Card();
            card.setCardNumber(cardNumber);
            Account account = new Account(accountId, card);
            return accountDao.getAccount(new AccountCriteria(account, false, false)) != null;
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
    public void setBanValue(int accountId) throws DbException {
        transactionManager.doWithoutTransaction(() -> {
            requestedAccountHolder.remove(accountId);
            Account account = new Account(accountId, new Account.Card());
            account = accountDao.getAccount(new AccountCriteria(account, false, false));
            account.setBanned(!account.isBanned());
            account.setRequestedForUnban(false);
            accountDao.setAccount(new AccountCriteria(account, true, true), accountId);
            return null;
        });
    }

    @Override
    public void setRequestedValue(int accountId) throws DbException, CredentialException {
        if (requestedAccountHolder.isAlreadyRequested(accountId)) {
            List<String> errors = new ArrayList<>();
            errors.add("Account already requested,or denied,try request tomorrow");
            throw new CredentialException(errors);
        }
        transactionManager.doWithTransaction(() ->
        {
            Account account = new Account(accountId, new Account.Card());
            account = accountDao.getAccount(new AccountCriteria(account, false, false));
            account.setRequestedForUnban(true);
            accountDao.setAccount(new AccountCriteria(account, false, true), accountId);
            return null;
        });
    }

}
