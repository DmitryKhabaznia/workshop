package workshop.db;

import workshop.exception.DBException;
import org.apache.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Class that intended for managing with JNDI connection.
 */
public class ConnectionManager {

    private static final Logger LOG = Logger.getLogger(ConnectionManager.class);
    private static final String CONNECTION_WAS_NOT_INSTALLED_ERROR_MESSAGE = "Connection was not installed.";
    private static final String CANNOT_GET_CONNECTION_ERROR_MESSAGE = "Can't get connection.";
    private static final String DATA_SOURCE_NAME = "java:/comp/env/jdbc/shop";
    private DataSource dataSource;

    public ConnectionManager() throws DBException {
//        try {
//            Context initContext = new InitialContext();
//            dataSource = (DataSource) initContext.lookup(DATA_SOURCE_NAME);
//            LOG.debug("Data source ==> " + dataSource);
//        } catch (NamingException ex) {
//            LOG.error(CONNECTION_WAS_NOT_INSTALLED_ERROR_MESSAGE, ex);
//            throw new DBException(CONNECTION_WAS_NOT_INSTALLED_ERROR_MESSAGE, ex);
//        }
    }

    public Connection getConnection() {
        try {
            URI dbUri = new URI(System.getenv("CLEARDB_DATABASE_URL"));
            String username = dbUri.getUserInfo().split(":")[0];
            String password = dbUri.getUserInfo().split(":")[1];
            String dbUrl = "jdbc:mysql://" + dbUri.getHost() + dbUri.getPath();
            return DriverManager.getConnection(dbUrl, username, password);
        } catch (URISyntaxException | SQLException ex) {
            LOG.error(CANNOT_GET_CONNECTION_ERROR_MESSAGE);
        }
        return null;
    }

    /**
     * Returns {@link Connection} from {@link DataSource}.
     *
     * @return {@link Connection} from {@link DataSource}
     */
//    public Connection getConnection() {
//        Connection connection = null;
//        try {
//            connection = dataSource.getConnection();
//        } catch (SQLException ex) {
//            LOG.error(CANNOT_GET_CONNECTION_ERROR_MESSAGE, ex);
//        }
//        return connection;
//    }

}

