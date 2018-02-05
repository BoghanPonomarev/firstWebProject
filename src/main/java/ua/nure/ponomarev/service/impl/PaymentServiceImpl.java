package ua.nure.ponomarev.service.impl;

import lombok.AllArgsConstructor;
import ua.nure.ponomarev.criteria.AccountCriteria;
import ua.nure.ponomarev.criteria.UserCriteria;
import ua.nure.ponomarev.currency.CurrencyManager;
import ua.nure.ponomarev.dao.AccountDao;
import ua.nure.ponomarev.dao.PaymentDao;
import ua.nure.ponomarev.document.DocumentType;
import ua.nure.ponomarev.document.RenderPaymentDto;
import ua.nure.ponomarev.document.ReportGenerator;
import ua.nure.ponomarev.entity.Account;
import ua.nure.ponomarev.entity.Payment;
import ua.nure.ponomarev.entity.User;
import ua.nure.ponomarev.exception.CredentialException;
import ua.nure.ponomarev.exception.DbException;
import ua.nure.ponomarev.service.PaymentService;
import ua.nure.ponomarev.transaction.TransactionManager;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Bogdan_Ponamarev.
 */
@AllArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private static final int MAX_SUM_OF_ACCOUNT = 1000000;
    private static final int MIN_SUM_OF_ACCOUNT = 0;
    private static final int PAGINATE_QUANTITY = 2;
    private TransactionManager transactionManager;
    private PaymentDao paymentDao;
    private AccountDao accountDao;
    private CurrencyManager currencyManager;
    private ReportGenerator reportGenerator;

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
        for (Account account : accountDao.getAll(new UserCriteria(user), "id")) {
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
    public List<Payment> getPayments(int userId, int page, Strategy strategy) throws DbException {
        String sortedColumn = "id";
        if (strategy != null) {
            if (strategy == Strategy.FIRST_OLD) {
                sortedColumn = "date";
            }
            if (strategy == Strategy.FIRST_NEW) {
                sortedColumn = " date DESC";
            }
        }
        String finalColumn = sortedColumn;
        return transactionManager.doWithoutTransaction(() -> paymentDao.getAll(userId, (page - 1) * PAGINATE_QUANTITY, PAGINATE_QUANTITY, finalColumn));
    }

    private List<Payment> getPayments(int userId, int page) throws DbException {
        return transactionManager.doWithoutTransaction(() -> getPayments(userId, page, Strategy.ID));
    }

    @Override
    public void deletePayment(int paymentId, int userId) throws DbException, CredentialException {
        List<String> errors = new ArrayList<>();
        transactionManager.doWithTransaction(() ->
        {
            if (accountDao.getUser(paymentDao.get(paymentId).getSenderId()).getId() == userId) {
                paymentDao.deletePayment(paymentId);
            } else {
                errors.add("Access denied!");
            }
            return null;
        });
        if (!errors.isEmpty()) {
            throw new CredentialException(errors);
        }
    }

    @Override
    public byte[] generateRecord(int paymentId, DocumentType documentType) throws DbException {
        return transactionManager.doWithTransaction(() -> {
            Payment payment = paymentDao.get(paymentId);
            Account senderAccount = new Account(payment.getSenderId(),new Account.Card());
            Account recipientAccount = new Account(payment.getRecipientId(),new Account.Card());
            senderAccount = accountDao.getAccount(new AccountCriteria(senderAccount,false,false));
            recipientAccount = accountDao.getAccount(new AccountCriteria(recipientAccount,false,false));
            User sender = accountDao.getUser(senderAccount.getId());
            User recipient = accountDao.getUser(recipientAccount.getId());
            return reportGenerator.generateReport(documentType,"payment"
                    , Arrays.asList(RenderPaymentDto.builder().amount(payment.getAmount().toString()+payment.getCurrency())
                    .amountCursive("amountCoursive")
                    .date(payment.getDate().toString())
                    .documentNumber(String.valueOf(payment.getId()))
                    .payer(sender.getPhoneNumber())
                    .receiver(recipient.getPhoneNumber())
                    .payerAccount(senderAccount.getName())
                    .receiverAccount(recipientAccount.getName()).build()));
        });
    }

}
