package ua.nure.ponomarev.service.impl;

import ua.nure.ponomarev.dao.PaymentDao;
import ua.nure.ponomarev.entity.Payment;
import ua.nure.ponomarev.transaction.TransactionManager;

/**
 * @author Bogdan_Ponamarev.
 */
public class PaymentServiceImpl {
    private TransactionManager transactionManager;
    private PaymentDao paymentDao;

    public PaymentServiceImpl(PaymentDao paymentDao, TransactionManager transactionManager) {
        this.paymentDao = paymentDao;
        this.transactionManager = transactionManager;
    }

    public void put(Payment payment){
       // transactionManager.doWithoutTransaction()//ADD work with currency and to db also
    }
}
