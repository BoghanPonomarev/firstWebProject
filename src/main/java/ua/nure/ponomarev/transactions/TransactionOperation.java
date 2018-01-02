package ua.nure.ponomarev.transactions;

import ua.nure.ponomarev.web.exception.DBException;

import java.sql.Connection;

/**
 * @author Bogdan_Ponamarev.
 */
public interface TransactionOperation<T> {
    T doWitTransaction() throws DBException;
}
