package ua.nure.ponomarev.service;
import ua.nure.ponomarev.exception.DbException;

import java.util.List;
import java.util.Locale;

/**
 * @author Bogdan_Ponamarev.
 */
public interface LocaleService {
    List<Locale> getSupportedLocales() throws DbException;
}
