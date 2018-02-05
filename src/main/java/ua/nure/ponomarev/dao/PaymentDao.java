package ua.nure.ponomarev.dao;

import ua.nure.ponomarev.entity.Payment;
import ua.nure.ponomarev.exception.DbException;

import java.util.List;

/**
 * @author Bogdan_Ponamarev.
 */
public interface PaymentDao {
    Payment get(int id) throws DbException;

    int put(Payment payment) throws DbException;

    void deletePayment(int paymentId)throws DbException;

    List<Payment> getAll(int userId,int startCount,int quantity,String sortedColumn) throws DbException;

    List<Payment> getAll(int userId,int startCount,int quantity,String sortedColumn,boolean onlyReadyPayments) throws DbException;


}
