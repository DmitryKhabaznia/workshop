package workshop.service;

import workshop.db.Action;
import workshop.db.TransactionManager;
import workshop.db.dao.OrderDAO;
import workshop.db.entity.impl.Order;
import workshop.exception.DBException;
import workshop.exception.ServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceTest {

    @Mock
    private TransactionManager transactionManager;
    @Mock
    private OrderDAO orderDAO;
    @Mock
    private Order order;
    @Mock
    private List list;

    @InjectMocks
    private OrderService orderService;

    @Test
    public void shouldReturnMockedList() throws DBException, ServiceException {
        when(transactionManager.doInTransactionWithResult(any(Action.class))).thenReturn(list);
        orderService.getAllShippingTypes();
        orderService.getAllPaymentTypes();
    }

    @Test
    public void shouldReturnOrder() throws DBException, ServiceException {
        when(transactionManager.doInTransactionWithResult(any(Action.class))).thenReturn(order);
        orderService.addOrder(order);
    }

    @Test (expected = ServiceException.class)
    public void shouldTrowServiceExceptionIfDBExceptionWasOccurredWhenTryToAddOrderWasInvoked() throws DBException, ServiceException {
        when(transactionManager.doInTransactionWithResult(any(Action.class))).thenThrow(DBException.class);
        orderService.addOrder(order);
    }

    @Test (expected = ServiceException.class)
    public void shouldTrowServiceExceptionIfDBExceptionWasOccurredWhenGetShippingTypesListWasInvoked() throws DBException, ServiceException {
        when(transactionManager.doInTransactionWithResult(any(Action.class))).thenThrow(DBException.class);
        orderService.getAllShippingTypes();
    }

    @Test (expected = ServiceException.class)
    public void shouldTrowServiceExceptionIfDBExceptionWasOccurredWhenGetPaymentTypesListWasInvoked() throws DBException, ServiceException {
        when(transactionManager.doInTransactionWithResult(any(Action.class))).thenThrow(DBException.class);
        orderService.getAllPaymentTypes();
    }

}