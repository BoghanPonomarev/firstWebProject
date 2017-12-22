package ua.nure.ponomarev.dao.impl;

import ua.nure.ponomarev.dao.api.UserDao;
import ua.nure.ponomarev.db.dataSource.DataSource;

/**
 * @author Bogdan_Ponamarev.
 */
public class UserDaoFactory {
    private DataSource dataSource;

    public UserDaoFactory() {

    }

    public UserDaoFactory(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public UserDao getUserDao(String type) {
        if (type.equals("DB") && this.dataSource != null) {
            return new DbUserDao(dataSource);
        }
        //need changing after jndi
        return null;
    }
}
