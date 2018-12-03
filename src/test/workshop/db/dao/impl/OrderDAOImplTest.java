package workshop.db.dao.impl;

import workshop.db.ConnectionHolder;
import workshop.db.entity.impl.Order;
import workshop.db.entity.impl.OrderProduct;
import workshop.db.entity.impl.User;
import workshop.exception.DBException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OrderDAOImplTest {

    private static final int ONE = 1;

    @Mock
    private ResultSet resultSet;
    @Mock
    private Statement statement;
    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private Connection connection;
    @Mock
    private ConnectionHolder connectionHolder;
    @Mock
    private Order order;
    @Mock
    private User user;
    @Mock
    private OrderProduct orderProduct;

    @InjectMocks
    private OrderDAOImpl orderDAO;

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
        when(order.getOrderDate()).thenReturn(LocalDateTime.now());
        when(order.getUser()).thenReturn(user);
        when(resultSet.next()).thenReturn(true);
    }

    @Test
    public void shouldReturnSpecifiedOrderWhenInvokeAddOrder() throws DBException, SQLException {
        when(order.getOrderDate()).thenReturn(LocalDateTime.now());
        when(order.getUser()).thenReturn(user);
        assertEquals(orderDAO.addOrder(order), order);
        verify(connectionHolder).getConnection();
        verify(connection).prepareStatement(anyString(), anyInt());
        verify(preparedStatement).executeUpdate();
        verify(resultSet).next();
    }

    @Test
    public void shouldInvokeSpecifiedMethodsWhenTryAddOrderItems() throws DBException, SQLException {
        List<OrderProduct> list = new ArrayList<>();
        list.add(orderProduct);
        when(order.getOrderProducts()).thenReturn(list);
        assertEquals(orderDAO.addOrder(order), order);
        verify(connectionHolder).getConnection();
        verify(connection).prepareStatement(anyString(), anyInt());
        verify(preparedStatement).executeUpdate();
        verify(preparedStatement).getGeneratedKeys();
        verify(resultSet).next();
    }

    @Test
    public void shouldReturnSpecifiedOrderWhenTryGetAllShippingTypes() throws DBException, SQLException {
        when(order.getUser()).thenReturn(user);
        when(resultSet.next()).thenReturn(false);
        assertEquals(orderDAO.getAllShippingTypes(), new ArrayList<>());
        verify(connectionHolder).getConnection();
        verify(connection).createStatement();
        verify(statement).executeQuery(anyString());
        verify(resultSet).next();
    }

    @Test
    public void shouldReturnSpecifiedOrderWhenTryGetAllPaymentTypes() throws DBException, SQLException {
        when(order.getUser()).thenReturn(user);
        when(resultSet.next()).thenReturn(false);
        assertEquals(orderDAO.getAllPaymentTypes(), new ArrayList<>());
        verify(connectionHolder).getConnection();
        verify(connection).createStatement();
        verify(statement).executeQuery(anyString());
        verify(resultSet).next();
    }

    @Test(expected = DBException.class)
    public void shouldThrowDBExceptionIfSQLExceptionWasOccurredWhenTryAddOrder() throws SQLException, DBException {
        when(connection.prepareStatement(anyString(), anyInt())).thenThrow(SQLException.class);
        orderDAO.addOrder(order);
    }

    @Test(expected = DBException.class)
    public void shouldThrowDBExceptionIfSQLExceptionWasOccurredWhenTryAddOrderItems() throws SQLException, DBException {
        List<OrderProduct> list = new ArrayList<>();
        list.add(orderProduct);
        when(order.getOrderProducts()).thenReturn(list);
        when(connection.prepareStatement(anyString(), anyInt())).thenThrow(SQLException.class);
        orderDAO.addOrderItems(order);

    }

    @Test(expected = DBException.class)
    public void shouldThrowDBExceptionIfSQLExceptionWasOccurredWhenTryGetShippingTypes() throws SQLException, DBException {
        when(connection.createStatement()).thenThrow(SQLException.class);
        orderDAO.getAllShippingTypes();
    }

    @Test(expected = DBException.class)
    public void shouldThrowDBExceptionIfSQLExceptionWasOccurredWhenTryGetPaymentTypes() throws SQLException, DBException {
        when(connection.createStatement()).thenThrow(SQLException.class);
        orderDAO.getAllPaymentTypes();
    }

}