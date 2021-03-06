package ua.nure.ponomarev.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.nure.ponomarev.exception.DbException;
import ua.nure.ponomarev.holder.SqlConnectionHolder;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Bogdan_Ponamarev.
 */
public class SqlDaoConnectionManager {
    private static Logger logger = LogManager.getLogger(SqlDaoConnectionManager.class);
    private DataSource dataSource;

    public SqlDaoConnectionManager(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Connection getConnection() throws SQLException {

        Connection connection = SqlConnectionHolder.getConnection();
        if (connection == null) {
            connection = dataSource.getConnection();
        }
        return connection;
    }

    public void closeResultSet(ResultSet resultSet) throws DbException {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException e) {
            logger.error("Result set has`nt been closed");
            throw new DbException("Result set has`nt been closed", DbException.ExceptionType.SERVER_EXCEPTION, e);
        }
    }

    public void closePrepareStatement(PreparedStatement preparedStatement) {
        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                logger.error("PreparedStatement was not closed", e);
            }
        }
    }
}
