package workshop.db.dao.impl;

import workshop.bean.FilterFormBean;
import workshop.db.ConnectionHolder;
import workshop.exception.DBException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.sql.*;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductDAOImplTest {

    private static final int ONE = 1;
    private static final String COUNT = "10";
    private static final long ID = 1;

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
    FilterFormBean filterFormBean;

    @InjectMocks
    private ProductDAOImpl productDAO;

    @Before
    public void setUp() throws SQLException {
        when(connectionHolder.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(connection.prepareStatement(anyString(), anyInt())).thenReturn(preparedStatement);
        when(connection.createStatement()).thenReturn(statement);
        when(statement.executeQuery(anyString())).thenReturn(resultSet);
        when(preparedStatement.executeUpdate()).thenReturn(ONE);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
    }

    @Test
    public void shouldReturnSpecifiedCount() throws SQLException, DBException {
        when(resultSet.getString(anyString())).thenReturn(COUNT);
        assertEquals(productDAO.getProductsCount(filterFormBean), Integer.parseInt(COUNT));
        verify(connectionHolder).getConnection();
        verify(connection).prepareStatement(anyString());
        verify(preparedStatement).executeQuery();
        verify(resultSet).next();
    }

    @Test
    public void shouldInvokeSpecifiedMethodsWhenTryGetProductById() throws SQLException, DBException {
        when(resultSet.next()).thenReturn(false);
        productDAO.getProductById(ID);
        verify(connectionHolder).getConnection();
        verify(connection).prepareStatement(anyString());
        verify(preparedStatement).executeQuery();
        verify(resultSet).next();
    }

    @Test
    public void shouldInvokeSpecifiedMethodWhenTryGetProductList() throws DBException, SQLException {
        when(resultSet.next()).thenReturn(false);
        productDAO.getProductList(filterFormBean);
        verify(connectionHolder).getConnection();
        verify(connection).prepareStatement(anyString());
        verify(preparedStatement).executeQuery();
        verify(resultSet).next();
    }

    @Test
    public void shouldInvokeSpecifiedMethodWhenTryGetCategoriesList() throws DBException, SQLException {
        when(resultSet.next()).thenReturn(false);
        productDAO.getAllCategories();
        verify(connectionHolder).getConnection();
        verify(connection).createStatement();
        verify(statement).executeQuery(anyString());
        verify(resultSet).next();
    }

    @Test
    public void shouldInvokeSpecifiedMethodWhenTryGetManufacturersList() throws DBException, SQLException {
        when(resultSet.next()).thenReturn(false);
        productDAO.getAllManufacturers();
        verify(connectionHolder).getConnection();
        verify(connection).createStatement();
        verify(statement).executeQuery(anyString());
        verify(resultSet).next();
    }

    @Test(expected = DBException.class)
    public void shouldThrowDBExceptionIfSQLExceptionWasOccurredWhenTryFindAllCategories() throws SQLException, DBException {
        when(connection.createStatement()).thenThrow(SQLException.class);
        productDAO.getAllCategories();
    }

    @Test(expected = DBException.class)
    public void shouldThrowDBExceptionIfSQLExceptionWasOccurredWhenTryFindAllManufacturers() throws SQLException, DBException {
        when(connection.createStatement()).thenThrow(SQLException.class);
        productDAO.getAllManufacturers();
    }

    @Test(expected = DBException.class)
    public void shouldThrowDBExceptionIfSQLExceptionWasOccurredWhenTryFindProductById() throws SQLException, DBException {
        when(connection.prepareStatement(anyString())).thenThrow(SQLException.class);
        productDAO.getProductById(ID);
    }

    @Test(expected = DBException.class)
    public void shouldThrowDBExceptionIfSQLExceptionWasOccurredWhenTryGetProductList() throws SQLException, DBException {
        when(connection.prepareStatement(anyString())).thenThrow(SQLException.class);
        productDAO.getProductList(filterFormBean);
    }

    @Test(expected = DBException.class)
    public void shouldThrowDBExceptionIfSQLExceptionWasOccurredWhenTryFindProductsCount() throws SQLException, DBException {
        when(connection.prepareStatement(anyString())).thenThrow(SQLException.class);
        productDAO.getProductsCount(filterFormBean);
    }

}