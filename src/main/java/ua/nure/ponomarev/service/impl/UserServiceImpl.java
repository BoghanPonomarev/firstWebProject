package ua.nure.ponomarev.service.impl;

import ua.nure.ponomarev.criteria.UserCriteria;
import ua.nure.ponomarev.dao.UserDao;
import ua.nure.ponomarev.entity.User;
import ua.nure.ponomarev.exception.CredentialException;
import ua.nure.ponomarev.exception.DbException;
import ua.nure.ponomarev.hash.HashGenerator;
import ua.nure.ponomarev.service.UserService;
import ua.nure.ponomarev.transaction.TransactionManager;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Bogdan_Ponamarev.
 */
public class UserServiceImpl implements UserService {
    private TransactionManager transaction;
    private UserDao userDao;
    private HashGenerator hash;

    public UserServiceImpl(TransactionManager transaction, UserDao userDao, HashGenerator hash) {
        this.transaction = transaction;
        this.userDao = userDao;
        this.hash = hash;
    }


    /**
     * Method that check existing of particular phone number in data memory
     * without transaction
     *
     * @param phoneNumber certain phone number
     * @return true if number exists in data memory
     * @throws DbException if there are some troubles with connection to data memory
     */
    private boolean isExistPhoneNumber(String phoneNumber) throws DbException {
        return transaction.doWithoutTransaction(() -> {
            User user = new User();
            user.setPhoneNumber(phoneNumber);
            user = userDao.get(new UserCriteria(user));
            return user != null;
        });
    }

    private boolean isExistEmail(String email) throws DbException {
        return transaction.doWithoutTransaction(() -> {
            User user = new User();
            user.setEmail(email);
            user = userDao.get(new UserCriteria(user));
            return user != null;
        });
    }

    private String hashUserPassword(String password) {
        if (password != null) {
            return hash.generateHash(password);
        }
        return null;
    }

    @Override
    public int add(User user) throws DbException, CredentialException {
        List<String> errors = new ArrayList<>();
        if (isExistPhoneNumber(user.getPhoneNumber())) {
            errors.add("Phone number is already exist");
        }
        if (!errors.isEmpty()) {
            throw new CredentialException(errors);
        }
        return transaction.doWithoutTransaction(() -> {
            user.setPassword(hashUserPassword(user.getPassword()));
            return userDao.put(user);
        });
    }

    /**
     * @param email email that must be activated
     * @return true if email exist in database regardless was activated
     * this email before or not,return false if there is not this email in database
     * @throws DbException if there is some problems with connection
     */
    @Override
    public void putEmail(String email, int id) throws DbException, CredentialException {
        if (isExistEmail(email)) {
            List<String> errors = new ArrayList<>();
            errors.add("Email is already exist");
            throw new CredentialException(errors);
        }
        transaction.doWithTransaction(() -> {
            User user = new User();
            user.setEmail(email);
            userDao.set(new UserCriteria(user), id);
            return true;
        });
    }

    @Override
    public User getFullUser(User user) throws DbException, CredentialException {
        User resUser = transaction.doWithTransaction(() -> {
            user.setPassword(hashUserPassword(user.getPassword()));
            return userDao.get(new UserCriteria(user));
        });
        if (resUser == null) {
            List<String> errors = new ArrayList<>();
            errors.add("There is no such user");
            throw new CredentialException(errors);
        }
        return resUser;
    }


    @Override
    public void setBanValue(int userId, boolean value) throws DbException {
        transaction.doWithoutTransaction(() -> {
            User user = new User();
            user.setBanned(value);
            userDao.set(new UserCriteria(user, true), userId);
            return null;
        });
    }

    public void setUserRole(int userId, User.Role role) throws DbException {
        transaction.doWithoutTransaction(
                () -> {
                    User user = new User();
                    user.setRole(role);
                    userDao.set(new UserCriteria(user), userId);
                    return null;
                });
    }

    @Override
    public List<User> getAll(User.Role requesterRole) throws DbException {
        User user = new User();
        return transaction.doWithTransaction(() ->
        {
            List<User> superAdminList = new ArrayList<>();
            user.setRole(User.Role.USER);
            superAdminList.addAll(userDao.getAll(new UserCriteria(user)));
            if (requesterRole.equals(User.Role.SUPER_ADMIN)) {
                user.setRole(User.Role.ADMIN);
                superAdminList.addAll(userDao.getAll(new UserCriteria(user)));
            }
            return superAdminList;
        });
    }
}
