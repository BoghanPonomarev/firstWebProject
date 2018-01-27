package ua.nure.ponomarev.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.nure.ponomarev.dao.CurrencyDao;
import ua.nure.ponomarev.dao.SqlDaoConnectionManager;
import ua.nure.ponomarev.exception.DbException;

import java.sql.Connection;
import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Bogdan_Ponamarev.
 */
public class CurrencyDaoImpl implements CurrencyDao {
    public static final String SQL_SELECT_VALUE_QUERY = "SELECT exchange_rate FROM webproject.exchange_rate WHERE currency_from in" +
            "(SELECT id FROM webproject.currency WHERE name=?) AND currency_to in(SELECT id FROM webproject.currency WHERE name=?)";
    public static final String SQL_GET_ALL_CURRENCY_QUERY = "SELECT name FROM webproject.currency";
    private static Logger logger = LogManager.getLogger(SqlUserDao.class);
    private SqlDaoConnectionManager connectionManager;

    public CurrencyDaoImpl(DataSource dataSource) {
        connectionManager = new SqlDaoConnectionManager(dataSource);
    }

    @Override
    public BigDecimal getCoefficient(String from, String to) throws DbException {
        PreparedStatement preparedStatement= null;
        ResultSet resultSet = null;
        try {
            Connection connection =connectionManager.getConnection();
            preparedStatement = connection.prepareStatement(SQL_SELECT_VALUE_QUERY);
            preparedStatement.setString(1,from);
            preparedStatement.setString(2,to);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getBigDecimal(1);
        } catch (SQLException e) {
           logger.error("Could not retrieve currency value "+e);
           throw new DbException("Could not retrieve currency value"+e);
        }finally {
            connectionManager.closeResultSet(resultSet);
            connectionManager.closePrepareStatement(preparedStatement);
        }
    }

    @Override
    public List<String> getCurrency() throws DbException {
        PreparedStatement preparedStatement= null;
        ResultSet resultSet = null;
        List<String> currency = new ArrayList<>();
        try {
            Connection connection =connectionManager.getConnection();
            preparedStatement = connection.prepareStatement(SQL_GET_ALL_CURRENCY_QUERY);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                currency.add(resultSet.getString(1));
            }
        } catch (SQLException e) {
            logger.error("Could not obtain currency "+e);
            throw new DbException("Could not obtain currency"+e);
        }finally {
            connectionManager.closeResultSet(resultSet);
            connectionManager.closePrepareStatement(preparedStatement);
        }
        return currency;
    }

}
