package workshop.service;

import workshop.db.TransactionManager;
import workshop.db.dao.UserDAO;
import workshop.db.entity.impl.User;
import workshop.exception.DBException;
import workshop.exception.ServiceException;
import org.apache.log4j.Logger;

/**
 * Service that works with {@link TransactionManager}, {@link UserDAO}.
 */
public class UserService {

    private static final Logger LOG = Logger.getLogger(UserService.class);
    private static final String ADDING_OF_USER_FAILED_MESSAGE = "Adding user was failed.";

    private TransactionManager transactionManager;
    private UserDAO userDAO;

    /**
     * Creates user service with specified {@link }.
     *
     * @param userDAO            specified {@link UserDAO}
     * @param transactionManager specified {@link TransactionManager}
     */
    public UserService(TransactionManager transactionManager, UserDAO userDAO) {
        LOG.debug("User service was initialized.");
        this.transactionManager = transactionManager;
        this.userDAO = userDAO;
    }

    /**
     * Adds specified {@link User} to {@link UserDAO}.
     *
     * @param user specified {@link User}
     * @return user that was added, null otherwise
     */
    public User addUser(User user) throws ServiceException {
        try {
            LOG.debug("Adding new user.");
            return transactionManager.doInTransactionWithResult(() -> userDAO.containsUser(user) ? null : userDAO.addUser(user));
        } catch (DBException ex) {
            LOG.error(ADDING_OF_USER_FAILED_MESSAGE, ex);
            throw new ServiceException(ADDING_OF_USER_FAILED_MESSAGE, ex);
        }
    }

    /**
     * Finds User with specified login.
     *
     * @param login specified id of {@link User}
     * @return {@link User} with specified login
     */
    public User findUserByLogin(String login) throws ServiceException {
        User user;
        try {
            user = transactionManager.doInTransactionWithResult(() -> userDAO.findUserByLogin(login));
            LOG.debug("Finding user with login: -> " + login);
        } catch (DBException ex) {
            LOG.error(ADDING_OF_USER_FAILED_MESSAGE, ex);
            throw new ServiceException(ADDING_OF_USER_FAILED_MESSAGE, ex);
        }
        return user;
    }
}

