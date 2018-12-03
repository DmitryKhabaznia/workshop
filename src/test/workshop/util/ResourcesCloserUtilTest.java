package workshop.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ResourcesCloserUtilTest {

    private  static final int ZERO_NUMBER_OF_INVOCATIONS = 0;
    @Mock
    private Statement statement;
    @Mock
    private ResultSet resultSet;

    @Test
    public void shouldCloseStatementWhenItIsNotNull() throws SQLException {
        ResourcesCloserUtil.close(statement);
        verify(statement).close();
    }

    @Test
    public void shouldCloseResultSetWhenItIsNotNull() throws SQLException {
        ResourcesCloserUtil.close(resultSet);
        verify(resultSet).close();
    }

    @Test
    public void shouldNotCloseResultSetWhenItIsNotNull() throws SQLException {
        ResourcesCloserUtil.close((ResultSet) null);
        verify(resultSet, times(ZERO_NUMBER_OF_INVOCATIONS)).close();
    }

    @Test
    public void shouldNotCloseStatementWhenItIsNotNull() throws SQLException {
        ResourcesCloserUtil.close((Statement) null);
        verify(resultSet, times(ZERO_NUMBER_OF_INVOCATIONS)).close();
    }

}