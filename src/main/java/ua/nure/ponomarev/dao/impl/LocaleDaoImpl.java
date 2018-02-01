package ua.nure.ponomarev.dao.impl;

import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.nure.ponomarev.dao.LocaleDao;
import ua.nure.ponomarev.dao.SqlDaoConnectionManager;
import ua.nure.ponomarev.exception.DbException;
import java.sql.*;
import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author Bogdan_Ponamarev.
 */
@AllArgsConstructor
public class LocaleDaoImpl implements LocaleDao {
    private static final String SQL_GET_ALL_LANGUAGES_QUERY = "SELECT name FROM webproject.languages";
    private static Logger logger = LogManager.getLogger(LocaleDaoImpl.class);
    private SqlDaoConnectionManager connectionManager;

    public LocaleDaoImpl(DataSource dataSource) {
        connectionManager = new SqlDaoConnectionManager(dataSource);
    }
    @Override
    public List<Locale> getLocales() throws DbException {
        PreparedStatement preparedStatement=null;
        ResultSet resultSet = null;
        try{
            Connection connection = connectionManager.getConnection();
            preparedStatement = connection.prepareStatement(SQL_GET_ALL_LANGUAGES_QUERY);
            resultSet = preparedStatement.executeQuery();
            List<Locale> locales = new ArrayList<>();
            while(resultSet.next()){
                locales.add(new Locale(resultSet.getString(1)));
            }
            return locales;
        } catch (SQLException e) {
            logger.fatal("Could not get all languages "+e);
            throw new DbException("Could not get all languages");
        }finally {
            connectionManager.closeResultSet(resultSet);
            connectionManager.closePrepareStatement(preparedStatement);
        }
    }
}
