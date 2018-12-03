package workshop.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import workshop.db.entity.impl.Order;
import workshop.exception.ServiceException;
import workshop.service.OrderService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static workshop.constants.Constants.AppContextConstants.ERROR_MESSAGE_DIV;
import static workshop.constants.Constants.AppContextConstants.ORDER_CONFIRM_SERVLET;
import static workshop.constants.Constants.AppContextConstants.SHOPPING_CART;

@RunWith(MockitoJUnitRunner.class)
public class OrderConfirmServletTest {

    private static final String ORDER = "order";
    private static final String ERR_CANNOT_ADD_ORDER = "Can not add new order.";

    @Mock
    private OrderService orderService;
    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpSession session;
    @Mock
    private Order order;


    @InjectMocks
    private OrderConfirmServlet orderConfirmServlet;

    @Test
    public void shouldRedirectToOrderConfirmServletWhenDoPostInvoked() throws ServiceException, IOException {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(ORDER)).thenReturn(order);
        when(orderService.addOrder(order)).thenReturn(order);
        orderConfirmServlet.doPost(request, response);
        verify(session).setAttribute(ORDER, order);
        verify(session).removeAttribute(SHOPPING_CART);
        verify(response).sendRedirect(ORDER_CONFIRM_SERVLET);
    }

    @Test(expected = Exception.class)
    public void shouldRedirectToErrorPageIfServletOccurredException() throws IOException, ServiceException {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(ORDER)).thenReturn(order);
        when(orderService.addOrder(order)).thenThrow(new Exception("Err_message"));
        orderConfirmServlet.doPost(request, response);
        verify(request).setAttribute(ERROR_MESSAGE_DIV, ERR_CANNOT_ADD_ORDER);
        verify(response).sendRedirect(ORDER_CONFIRM_SERVLET);
    }

}