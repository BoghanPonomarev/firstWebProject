package ua.nure.ponomarev.service.impl;

import lombok.AllArgsConstructor;
import ua.nure.ponomarev.dao.CurrencyDao;
import ua.nure.ponomarev.dao.LocaleDao;
import ua.nure.ponomarev.exception.DbException;
import ua.nure.ponomarev.service.LocaleService;
import ua.nure.ponomarev.transaction.TransactionManager;

import java.util.List;
import java.util.Locale;

/**
 * @author Bogdan_Ponamarev.
 */
@AllArgsConstructor
public class LocaleServiceImpl implements LocaleService {
    private TransactionManager transactionManager;
    private LocaleDao localeDao;
    @Override
    public List<Locale> getSupportedLocales() throws DbException {
        return transactionManager.doWithTransaction(()->localeDao.getLocales());
    }
}
