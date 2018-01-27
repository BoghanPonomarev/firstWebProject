package ua.nure.ponomarev.currency;

import lombok.AllArgsConstructor;
import ua.nure.ponomarev.dao.CurrencyDao;
import ua.nure.ponomarev.exception.DbException;

import java.math.BigDecimal;

/**
 * @author Bogdan_Ponamarev.
 */
@AllArgsConstructor
public class CurrencyManagerImpl implements CurrencyManager {
    private CurrencyDao currencyDao;
    @Override
    public BigDecimal convertCurrency(BigDecimal value, String fromCurrency, String toCurrency) throws DbException {
        return value.multiply(currencyDao.getCoefficient(fromCurrency,toCurrency));
    }
}
