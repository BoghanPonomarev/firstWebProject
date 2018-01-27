package ua.nure.ponomarev.service;

import ua.nure.ponomarev.entity.Payment;
import ua.nure.ponomarev.exception.CredentialException;
import ua.nure.ponomarev.exception.DbException;
import java.util.List;
import java.math.BigDecimal;

/**
 * @author Bogdan_Ponamarev.
 */
public interface PaymentService {
    void executePayment(int id,int userId) throws DbException, CredentialException;

    int preparePayment(int senderAccountId, String recipientIdentity, BigDecimal amount, String currency,int userId) throws DbException, CredentialException;

    List<Payment> getPayments(int userId) throws DbException;

    void deletePayment(int paymentId) throws DbException;
}
