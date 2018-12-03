package workshop.db;

import workshop.exception.DBException;

/**
 * Functional interface for actions that should return some result.
 *
 * @param <T> type of result
 */
public interface Action<T> {

    T execute() throws DBException;

}
