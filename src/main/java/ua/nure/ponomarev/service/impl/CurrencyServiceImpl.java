package ua.nure.ponomarev.service.impl;

import lombok.AllArgsConstructor;
import ua.nure.ponomarev.dao.CurrencyDao;
import ua.nure.ponomarev.exception.DbException;
import ua.nure.ponomarev.service.CurrencyService;
import ua.nure.ponomarev.transaction.TransactionManager;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Bogdan_Ponamarev.
 */
@AllArgsConstructor
public class CurrencyServiceImpl implements CurrencyService {
    private TransactionManager transactionManager;
    private CurrencyDao currencyDao;
    @Override
    public BigDecimal getCoefficient(String from, String to) throws DbException {
        return transactionManager.doWithTransaction(()->currencyDao.getCoefficient(from,to));
    }

    @Override
    public List<String> getCurrency() throws DbException {
        return transactionManager.doWithTransaction(()->currencyDao.getCurrency());
    }
}
