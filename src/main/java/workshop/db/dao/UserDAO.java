package workshop.db.dao;

import workshop.db.entity.impl.User;
import workshop.exception.DBException;

/**
 * User DAO that intended for managing users.
 */
public interface UserDAO {

    /**
     * Adds specified user to database.
     *
     * @param user specified {@link User}
     * @return added {@link User} with updated id
     */
    User addUser(User user) throws DBException;

    /**
     * Checks if specified {@link User} is in database.
     *
     * @param user specified {@link User}
     * @return <tt>true</tt> if user is in database, <tt>false</tt> otherwise
     */
    boolean containsUser(User user) throws DBException;

    /**
     * Finds {@link User} in database with specified login.
     *
     * @param login specified login
     * @return {@link User} with specified login
     */
    User findUserByLogin(String login) throws DBException;

}

