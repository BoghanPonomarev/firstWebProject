package ua.nure.ponomarev.currency;

import ua.nure.ponomarev.exception.DbException;

import java.math.BigDecimal;

/**
 * @author Bogdan_Ponamarev.
 */
public interface CurrencyManager {
    BigDecimal convertCurrency(BigDecimal value,String fromCurrency,String toCurrency) throws DbException;
}
