package workshop.db;

import org.apache.log4j.Logger;

import java.sql.Connection;

/**
 * Class for holding {@link Connection}.
 */
public class ConnectionHolder {

    private static final Logger LOG = Logger.getLogger(ConnectionHolder.class);
    private ThreadLocal<Connection> threadLocal;

    public ConnectionHolder(ConnectionManager connectionManager) {
        threadLocal = ThreadLocal.withInitial(connectionManager::getConnection);
    }

    /**
     * @return {@link Connection} from {@link ThreadLocal}
     */
    public Connection getConnection() {
        return threadLocal.get();
    }

    /**
     * Removes {@link Connection} from {@link ThreadLocal}
     */
    public void removeConnection() {
        threadLocal.remove();
        LOG.debug("Connection was removed.");
    }

}
