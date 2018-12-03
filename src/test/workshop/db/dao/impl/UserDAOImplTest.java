package workshop.db.dao.impl;

import workshop.db.ConnectionHolder;
import workshop.db.entity.impl.User;
import workshop.exception.DBException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.sql.*;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserDAOImplTest {

    private static final int ONE = 1;
    private static final String LOGIN = "login";

    @Mock
    ResultSet resultSet;
    @Mock
    Statement statement;
    @Mock
    PreparedStatement preparedStatement;
    @Mock
    Connection connection;
    @Mock
    ConnectionHolder connectionHolder;
    @Mock
    User user;

    @InjectMocks
    private UserDAOImpl userDAO;

    @Before
    public void setUp() throws SQLException {
        when(connectionHolder.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(connection.prepareStatement(anyString(), anyInt())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(ONE);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
    }

    @Test
    public void shouldInvokeSpecifiedMethodWhenTryAddUser() throws DBException, SQLException {
        assertEquals(userDAO.addUser(user), user);
        verify(connectionHolder).getConnection();
        verify(connection).prepareStatement(anyString(), anyInt());
        verify(preparedStatement, times(6)).setString(anyInt(), anyString());
        verify(preparedStatement, times(1)).setBoolean(anyInt(), anyBoolean());
        verify(preparedStatement).executeUpdate();
        verify(resultSet).next();
    }

    @Test
    public void shouldInvokeSpecifiedMethodWhenTryFindSpecifiedUser() throws DBException {
        assertEquals(userDAO.containsUser(user), true);
    }

    @Test
    public void shouldInvokeSpecifiedMethodWhenTryFindSpecifiedUserByLogin() throws DBException, SQLException {
        userDAO.findUserByLogin(LOGIN);
        verify(connectionHolder).getConnection();
        verify(connection).prepareStatement(anyString());
        verify(preparedStatement).setString(anyInt(), anyString());
        verify(preparedStatement).executeQuery();
        verify(resultSet).next();
    }

    @Test(expected = DBException.class)
    public void shouldThrowDBExceptionIfSQLExceptionWasOccurredWhenTryAddUser() throws SQLException, DBException {
        when(connection.prepareStatement(anyString(), anyInt())).thenThrow(SQLException.class);
        when(connection.prepareStatement(anyString())).thenThrow(SQLException.class);
        userDAO.addUser(user);
    }

    @Test(expected = DBException.class)
    public void shouldThrowDBExceptionIfSQLExceptionWasOccurredWhenTryFindUserByLogin() throws SQLException, DBException {
        when(connection.prepareStatement(anyString())).thenThrow(SQLException.class);
        userDAO.findUserByLogin(LOGIN);
    }

    @Test(expected = DBException.class)
    public void shouldThrowDBExceptionIfSQLExceptionWasOccurredWhenTryFindUser() throws SQLException, DBException {
        when(connection.prepareStatement(anyString())).thenThrow(SQLException.class);
        userDAO.containsUser(user);
    }

}