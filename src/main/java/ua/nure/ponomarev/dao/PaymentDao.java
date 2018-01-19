package ua.nure.ponomarev.dao;

import ua.nure.ponomarev.entity.Payment;
import ua.nure.ponomarev.exception.DBException;

import java.util.List;

/**
 * @author Bogdan_Ponamarev.
 */
public interface PaymentDao {
    void put(Payment payment) throws DBException;

    List<Payment> getAll(int userId) throws DBException;
}
