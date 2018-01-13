package ua.nure.ponomarev.transaction;

import ua.nure.ponomarev.exception.DBException;

/**
 * @author Bogdan_Ponamarev.
 */
public interface TransactionOperation<T> {
    T execute() throws DBException;
}
