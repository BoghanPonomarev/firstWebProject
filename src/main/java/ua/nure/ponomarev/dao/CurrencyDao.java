package ua.nure.ponomarev.dao;

import ua.nure.ponomarev.exception.DbException;

import java.math.BigDecimal;
import java.util.List;
/**
 * @author Bogdan_Ponamarev.
 */
public interface CurrencyDao {
    BigDecimal getCoefficient(String from,String to) throws DbException;

    List<String> getCurrency()throws DbException;
}
