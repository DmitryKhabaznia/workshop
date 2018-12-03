package workshop.db.dao.impl;

import workshop.db.ConnectionHolder;
import workshop.db.dao.UserDAO;
import workshop.db.entity.impl.User;
import workshop.exception.DBException;
import org.apache.log4j.Logger;
import workshop.util.ResourcesCloserUtil;

import java.sql.*;

import static workshop.util.ResourcesCloserUtil.close;


/**
 * Implementation of DAO that intended for managing users.
 */
public class UserDAOImpl implements UserDAO {

    private static final Logger LOG = Logger.getLogger(UserDAOImpl.class);
    private static final String ERR_CANNOT_ADD_USER = "Can't add user. ";
    private static final String ERR_CANNOT_OBTAIN_USER_BY_LOGIN = "Can't obtain user by login. ";
    private static final String ERR_CANNOT_EXTRACT_USER = "Can't extract user from result set. ";

    private static final String ENTITY_ID = "id";
    private static final String USER_LOGIN = "login";
    private static final String USER_PASS = "password";
    private static final String USER_FIRST_NAME = "first_name";
    private static final String USER_LAST_NAME = "last_name";
    private static final String USER_GENDER = "gender";
    private static final String USER_EMAIL = "email";
    private static final String USER_ROLE = "role";

    private static final String SQL_ADD_USER = "INSERT INTO users VALUES(DEFAULT, ?, ?, ?, ?, ?, ?, " +
            "(SELECT id from Roles where name = ?));";
    private static final String SQL_FIND_USER_BY_LOGIN =
            "SELECT a.id, a.first_name, a.last_name, a.login, a.email, a.password, a.gender, b.name AS role " +
                    "FROM users AS a, roles AS b WHERE a.role_id = b.id AND a.login = ?;";
    private ConnectionHolder connectionHolder;

    public UserDAOImpl(ConnectionHolder connectionHolder) {
        this.connectionHolder = connectionHolder;
    }

    /**
     * Adds specified user to database.
     *
     * @param user specified {@link User}
     * @return added {@link User} with updated id
     */
    @Override
    public User addUser(User user) throws DBException {
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        try {
            Connection con = connectionHolder.getConnection();
            pstmt = con.prepareStatement(SQL_ADD_USER, Statement.RETURN_GENERATED_KEYS);
            int k = 1;
            pstmt.setString(k++, user.getFirstName());
            pstmt.setString(k++, user.getLastName());
            pstmt.setString(k++, user.getLogin());
            pstmt.setString(k++, user.getEmail());
            pstmt.setString(k++, user.getPassword());
            pstmt.setBoolean(k++, user.getMale());
            pstmt.setString(k, user.getRole());
            if (pstmt.executeUpdate() > 0) {
                rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    user.setId(rs.getLong(1));
                }
            }
        } catch (SQLException ex) {
            LOG.error("Can not add user: -> " + user);
            throw new DBException(ERR_CANNOT_ADD_USER, ex);
        } finally {
            ResourcesCloserUtil.close(pstmt, rs);
        }
        return user;
    }

    /**
     * Checks if specified {@link User} is in database.
     *
     * @param user specified {@link User}
     * @return <tt>true</tt> if user is in database, <tt>false</tt> otherwise
     */
    @Override
    public boolean containsUser(User user) throws DBException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            Connection con = connectionHolder.getConnection();
            pstmt = con.prepareStatement(SQL_FIND_USER_BY_LOGIN);
            pstmt.setString(1, user.getLogin());
            rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException ex) {
            LOG.error("Can not obtain user: -> " + user);
            throw new DBException(ERR_CANNOT_OBTAIN_USER_BY_LOGIN, ex);
        } finally {
            ResourcesCloserUtil.close(pstmt, rs);
        }
    }

    /**
     * Finds {@link User} in database with specified login.
     *
     * @param login specified login
     * @return {@link User} with specified login
     */
    @Override
    public User findUserByLogin(String login) throws DBException {
        User user = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            Connection con = connectionHolder.getConnection();
            pstmt = con.prepareStatement(SQL_FIND_USER_BY_LOGIN);
            pstmt.setString(1, login);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                user = mapUser(rs);
            }
            return user;
        } catch (SQLException ex) {
            LOG.error("Can not obtain user by login: -> " + login);
            throw new DBException(ERR_CANNOT_OBTAIN_USER_BY_LOGIN, ex);
        } finally {
            ResourcesCloserUtil.close(pstmt, rs);
        }
    }

    private User mapUser(ResultSet rs) throws DBException {
        User user = new User();
        try {
            user.setId(rs.getLong(ENTITY_ID));
            user.setLogin(rs.getString(USER_LOGIN));
            user.setPassword(rs.getString(USER_PASS));
            user.setFirstName(rs.getString(USER_FIRST_NAME));
            user.setLastName(rs.getString(USER_LAST_NAME));
            user.setEmail(rs.getString(USER_EMAIL));
            user.setMale(rs.getInt(USER_GENDER) == 1);
            user.setRole(rs.getString(USER_ROLE));
            return user;
        } catch (SQLException ex) {
            LOG.error("Can not extract user from result set.");
            throw new DBException(ERR_CANNOT_EXTRACT_USER, ex);
        }
    }

}

