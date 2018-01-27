package ua.nure.ponomarev.transaction;

import ua.nure.ponomarev.exception.CredentialException;
import ua.nure.ponomarev.exception.DbException;

/**
 * @author Bogdan_Ponamarev.
 */
public interface TransactionOperation<T> {
    T execute() throws DbException;
}
