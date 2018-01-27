package ua.nure.ponomarev.service.impl;

import lombok.AllArgsConstructor;
import ua.nure.ponomarev.criteria.AccountCriteria;
import ua.nure.ponomarev.criteria.UserCriteria;
import ua.nure.ponomarev.currency.CurrencyManager;
import ua.nure.ponomarev.dao.AccountDao;
import ua.nure.ponomarev.dao.PaymentDao;
import ua.nure.ponomarev.entity.Account;
import ua.nure.ponomarev.entity.Payment;
import ua.nure.ponomarev.entity.User;
import ua.nure.ponomarev.exception.CredentialException;
import ua.nure.ponomarev.exception.DbException;
import ua.nure.ponomarev.service.PaymentService;
import ua.nure.ponomarev.transaction.TransactionManager;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Bogdan_Ponamarev.
 */
@AllArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private static final int MAX_SUM_OF_ACCOUNT = 1000000;
    private static final int MIN_SUM_OF_ACCOUNT = 0;
    private TransactionManager transactionManager;
    private PaymentDao paymentDao;
    private AccountDao accountDao;
    private CurrencyManager currencyManager;

    @Override
    public void executePayment(int id, int userId) throws DbException, CredentialException {
        List<String> errors = new ArrayList<>();
        try {
            transactionManager.doWithTransaction(() ->
                    {
                        Payment payment = paymentDao.get(id);
                        if (payment == null) {
                            errors.add("Payment was deleted");
                            throw new DbException("Validation exception");
                        }
                        Account sender = accountDao.getAccount(new AccountCriteria(
                                new Account(payment.getSenderId(), new Account.Card()), false, false));
                        Account recipient = accountDao.getAccount(new AccountCriteria(
                                new Account(payment.getRecipientId(), new Account.Card()), false, false));
                        errors.addAll(executeValidation(sender, recipient, payment.getAmount(), payment.getCurrency()));
                        errors.addAll(accessValidation(userId, sender.getId()));
                        if (!errors.isEmpty()) {
                            throw new DbException("Validation exception");
                        }
                        BigDecimal amount = payment.getAmount();
                        if (!payment.getCurrency().equals(sender.getCurrency())) {
                            amount = currencyManager.convertCurrency(payment.getAmount(), payment.getCurrency(), sender.getCurrency());
                        }
                        sender.setBalance(sender.getBalance().add(amount.negate()));
                        accountDao.setAccount(new AccountCriteria(
                                sender, false, false), sender.getId());
                        if (!payment.getCurrency().equals(recipient.getCurrency())) {
                            amount = currencyManager.convertCurrency(payment.getAmount(), payment.getCurrency(), recipient.getCurrency());
                        }
                        recipient.setBalance(recipient.getBalance().add(amount));
                        accountDao.setAccount(new AccountCriteria(
                                        recipient, false, false),
                                recipient.getId());
                        putPayment(payment);
                        return null;
                    }
            );
        } catch (DbException e) {
            if (!errors.isEmpty()) {
                throw new CredentialException(errors);
            } else {
                throw e;
            }
        }
    }

    private void putPayment(Payment payment) throws DbException {
        payment.setStatus(Payment.Status.SENT);
        payment.setDate(LocalDateTime.now());
        paymentDao.deletePayment(payment.getId());
        paymentDao.put(payment);
        int tmpId = payment.getRecipientId();
        payment.setRecipientId(payment.getSenderId());
        payment.setSenderId(tmpId);
        payment.setType(Payment.Type.INCOMING);
        paymentDao.put(payment);
    }

    private List<String> accessValidation(int userId, int accountId) throws DbException {
        User user = new User();
        List<String> error = new ArrayList<>();
        user.setId(userId);
        for (Account account : accountDao.getAll(new UserCriteria(user))) {
            if (accountId == account.getId()) {
                return error;
            }
        }
        error.add("Access denied!");
        return error;
    }

    private List<String> preparingValidation(Account senderAccount, Account recipientAccount
            , BigDecimal amount, String currency, int userId) throws DbException {
        List<String> errors = new ArrayList<>();
        if (senderAccount == null) {
            errors.add("There is no such sender account");
        }
        if (recipientAccount == null) {
            errors.add("There is no such recipient account");
        }
        if (!errors.isEmpty()) {
            return errors;
        }
        if (senderAccount.isBanned()) {
            errors.add("Sender banned");
        }
        if (recipientAccount.isBanned()) {
            errors.add("Recipient banned");
        }
        if (senderAccount.getId() == recipientAccount.getId()) {
            errors.add("You can not be paid for the same account");
        }
        errors.addAll(accessValidation(userId, senderAccount.getId()));
        return errors;
    }

    private List<String> executeValidation(Account senderAccount, Account recipientAccount
            , BigDecimal amount, String currency) throws DbException {
        List<String> errors = new ArrayList<>();
        if (senderAccount == null) {
            errors.add("There is no such sender account");
        }
        if (recipientAccount == null) {
            errors.add("There is no such recipient account");
        }
        if (!errors.isEmpty()) {
            return errors;
        }

        if (senderAccount.isBanned()) {
            errors.add("Sender banned");
        }
        if (recipientAccount.isBanned()) {
            errors.add("Recipient banned");
        }
        if (!currency.equals(senderAccount.getCurrency())) {
            amount = currencyManager.convertCurrency(amount, currency, senderAccount.getCurrency());
        }
        if (senderAccount.getBalance().add(amount.negate()).doubleValue() < MIN_SUM_OF_ACCOUNT) {
            errors.add("Not enough money on your account");
        }
        if (!currency.equals(recipientAccount.getCurrency())) {
            amount = currencyManager.convertCurrency(amount, currency, recipientAccount.getCurrency());
        }
        if (senderAccount.getBalance().add(amount).doubleValue() > MAX_SUM_OF_ACCOUNT) {
            errors.add("The amount of money exceeds the allowable");
        }
        return errors;
    }

    @Override
    public int preparePayment(int senderAccountId, String recipientIdentity, BigDecimal amount, String currency, int userId) throws DbException, CredentialException {
        List<String> errors = new ArrayList<>();
        Account recipient = transactionManager.doWithoutTransaction(() -> {
            Account account = new Account(0, new Account.Card(recipientIdentity, null, null));
            Account resAccount = accountDao.getAccount(new AccountCriteria(account, false, false));
            if (resAccount == null) {
                account.setCard(new Account.Card());
                account.setName(recipientIdentity);
                resAccount = accountDao.getAccount(new AccountCriteria(account, false, false));
            }
            return resAccount;
        });
        Account sender = transactionManager.doWithoutTransaction(() ->
                accountDao.getAccount(new AccountCriteria(
                        new Account(senderAccountId,
                                new Account.Card()), false, false)));
        transactionManager.doWithTransaction(() -> {
            errors.addAll(preparingValidation(sender, recipient, amount, currency, userId));
            return null;
        });
        if (!errors.isEmpty()) {
            throw new CredentialException(errors);
        }
        Payment payment = new Payment().builder()
                .date(LocalDateTime.now()).amount(amount)
                .status(Payment.Status.PREPARED)
                .currency(currency).senderId(senderAccountId).type(Payment.Type.OUTGOING)
                .recipientId(recipient.getId()).build();
        return transactionManager.doWithoutTransaction(() ->
        {
            return paymentDao.put(payment);
        });
    }


    @Override
    public List<Payment> getPayments(int userId) throws DbException {
        return transactionManager.doWithoutTransaction(() -> paymentDao.getAll(userId));
    }

    @Override
    public void deletePayment(int paymentId) throws DbException {
        transactionManager.doWithTransaction(() ->
        {
            paymentDao.deletePayment(paymentId);
            return null;
        });
    }

}
