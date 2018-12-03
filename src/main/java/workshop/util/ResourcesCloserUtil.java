package workshop.util;

import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static workshop.constants.Constants.Messages.CANNOT_CLOSE_RESULT_SET;
import static workshop.constants.Constants.Messages.CANNOT_CLOSE_STATEMENT;

/**
 * Util for closing {@link Statement} and {@link ResultSet}.
 */
public class ResourcesCloserUtil {

    private static final Logger LOG = Logger.getLogger(ResourcesCloserUtil.class);

    /**
     * Closes {@link Statement} and {@link ResultSet}.
     *
     * @param stmt specified {@link Statement}
     * @param rs   specified {@link ResultSet}
     */
    public static void close(Statement stmt, ResultSet rs) {
        close(rs);
        close(stmt);
    }

    public static void close(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException ex) {
                LOG.error(CANNOT_CLOSE_STATEMENT, ex);
            }
        }
    }

    public static void close(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException ex) {
                LOG.error(CANNOT_CLOSE_RESULT_SET, ex);
            }
        }
    }
}
