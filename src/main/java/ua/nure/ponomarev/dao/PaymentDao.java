package ua.nure.ponomarev.dao;

import ua.nure.ponomarev.entity.Payment;
import ua.nure.ponomarev.exception.DbException;

import java.util.List;

/**
 * @author Bogdan_Ponamarev.
 */
public interface PaymentDao {
    void put(Payment payment) throws DbException;

    List<Payment> getAll(int userId) throws DbException;
}
