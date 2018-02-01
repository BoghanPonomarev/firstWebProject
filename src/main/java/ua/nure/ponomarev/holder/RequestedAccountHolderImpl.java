package ua.nure.ponomarev.holder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.nure.ponomarev.criteria.AccountCriteria;
import ua.nure.ponomarev.dao.AccountDao;
import ua.nure.ponomarev.entity.Account;
import ua.nure.ponomarev.exception.DbException;
import ua.nure.ponomarev.transaction.TransactionManager;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author Bogdan_Ponamarev.
 */
public class RequestedAccountHolderImpl implements RequestedAccountHolder {
    private static Logger logger = LogManager.getLogger(RequestedAccountHolderImpl.class);
    private Map<Integer, LocalDateTime> requested;
    private AccountDao accountDao;
    private TransactionManager transactionManager;
    private static final int DEFAULT_DELAY = 5;
    private static final int INIT_DELAY = 2;
    private static final int HOUR_BAN_DELAY = 24;
    private ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor((Runnable r) ->
    {
        Thread th = new Thread(r);
        th.setDaemon(true);
        return th;
    });

    public RequestedAccountHolderImpl(AccountDao accountDao,TransactionManager transactionManager) {
        this.accountDao = accountDao;
        this.transactionManager = transactionManager;
        requested = new ConcurrentHashMap<>();
        scheduler.scheduleAtFixedRate(() -> {
            LocalDateTime now = LocalDateTime.now();
            for (Integer key : requested.keySet()) {
                if (requested.get(key).isBefore(now)) {
                    try {
                        this.transactionManager.doWithTransaction(()->{
                            Account account= new Account(key,new Account.Card());
                            account =this.accountDao.getAccount(
                                    new AccountCriteria(account,false,false));
                            account.setRequestedForUnban(false);
                            this.accountDao.setAccount(new AccountCriteria(account,false,true),account.getId());
                            return null;
                                }
                        );
                    } catch (DbException e) {
                        logger.error("Could not set requirement status on account "+e);
                    }
                    requested.remove(key);
                }
            }

        }, INIT_DELAY, DEFAULT_DELAY, TimeUnit.MINUTES);
    }

    @Override
    public void addToRequested(int accountId) {
        requested.put(accountId, LocalDateTime.now().plus(HOUR_BAN_DELAY, ChronoUnit.HOURS));
    }

    @Override
    public boolean isAlreadyRequested(int accountId) {
        return requested.containsKey(accountId);
    }

    @Override
    public void remove(int accountId) {
        requested.remove(accountId);
    }
}
