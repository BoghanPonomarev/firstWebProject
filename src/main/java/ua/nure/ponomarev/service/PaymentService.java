package ua.nure.ponomarev.service;

import ua.nure.ponomarev.document.DocumentType;
import ua.nure.ponomarev.entity.Payment;
import ua.nure.ponomarev.exception.CredentialException;
import ua.nure.ponomarev.exception.DbException;
import java.util.List;
import java.math.BigDecimal;

/**
 * @author Bogdan_Ponamarev.
 */
public interface PaymentService {
    public enum Strategy{
        ID,
        FIRST_OLD,
        FIRST_NEW
    }

    void executePayment(int id,int userId) throws DbException, CredentialException;

    int preparePayment(int senderAccountId, String recipientIdentity, BigDecimal amount, String currency,int userId) throws DbException, CredentialException;

    List<Payment> getPayments(int userId,int page,Strategy strategy) throws DbException;

    void deletePayment(int paymentId,int userId) throws DbException, CredentialException;

    byte[] generateRecord(int paymentId, DocumentType documentType) throws  DbException;
}
