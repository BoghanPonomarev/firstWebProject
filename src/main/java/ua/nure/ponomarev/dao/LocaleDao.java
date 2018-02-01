package ua.nure.ponomarev.dao;

import ua.nure.ponomarev.exception.DbException;

import java.util.Locale;
import java.util.List;
/**
 * @author Bogdan_Ponamarev.
 */
public interface LocaleDao {
    List<Locale> getLocales()throws DbException;
}
