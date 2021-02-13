package workshop.db;

import workshop.exception.DBException;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

import static workshop.constants.Constants.Messages.CANNOT_CLOSE_CONNECTION;
import static workshop.constants.Constants.Messages.CANNOT_ROLLBACK_CONNECTION;


/**
 * Class that intended for making specified actions as transaction.
 */
public class TransactionManager {

    private static final Logger LOG = Logger.getLogger(TransactionManager.class);
    private static final String CANNOT_DO_TRANSACTION = "Can't do transaction.";

    private ConnectionHolder connectionHolder;

    /**
     * Constructs {@link TransactionManager} with specified {@link ConnectionHolder}
     *
     * @param connectionHolder specified {@link ConnectionHolder}
     */
    public TransactionManager(ConnectionHolder connectionHolder) {
        this.connectionHolder = connectionHolder;
    }

    /**
     * Makes specified actions as transaction and returns result
     *
     * @param action specified actions
     * @param <T>      type of result
     * @return result of actions
     */
    public <T> T doInTransactionWithResult(Action<T> action) throws DBException {
        Connection connection = null;
        try {
            connection = connectionHolder.getConnection();
            connection.setAutoCommit(false);
            T result = action.execute();
            connection.commit();
            return result;
        } catch (SQLException | DBException ex) {
            rollback(connection);
            LOG.error(CANNOT_DO_TRANSACTION, ex);
            throw new DBException(CANNOT_DO_TRANSACTION, ex);
        } finally {
            close(connection);
            connectionHolder.removeConnection();
        }
    }

    private void rollback(Connection con) {
        if (con != null) {
            try {
                con.rollback();
            } catch (SQLException ex) {
                LOG.error(CANNOT_ROLLBACK_CONNECTION, ex);
            }
        }
    }

    private void close(Connection con) {
        if (con != null) {
            try {
                con.close();
                System.out.println("sdfsdfs");
            } catch (SQLException ex) {
                LOG.error(CANNOT_CLOSE_CONNECTION, ex);
            }
        }
    }

}
