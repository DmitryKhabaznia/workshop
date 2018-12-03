package workshop.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import workshop.bean.Cart;
import workshop.bean.OrderFormBean;
import workshop.db.entity.impl.*;
import workshop.exception.ServiceException;
import workshop.service.OrderService;
import workshop.util.converter.impl.OrderConverter;
import workshop.util.validator.OrderFormBeanValidator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static workshop.constants.Constants.AppContextConstants.*;

@RunWith(MockitoJUnitRunner.class)
public class OrderServletTest {

    private static final List<ShippingType> SHIPPING_TYPES = new ArrayList<>();
    private static final List<PaymentType> PAYMENT_TYPES = new ArrayList<>();
    private static final String PAYMENT_TYPES_ATTRIBUTE = "paymentTypes";
    private static final String SHIPPING_TYPES_ATTRIBUTE = "shippingTypes";
    private static final String ERR_MESSAGE = "Date is wrong.";
    private static final String ERROR = "error";
    private static final String ORDER = "order";
    private Map<Product, Integer> productMap;

    @Mock
    private OrderFormBeanValidator orderFormBeanValidator;
    @Mock
    private OrderConverter orderConverter;
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
    @Mock
    private User user;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private Cart cart;

    @InjectMocks
    private OrderServlet orderServlet;

    @Before
    public void setUp() throws ServiceException {
        productMap = new HashMap<>();
        when(orderService.getAllPaymentTypes()).thenReturn(PAYMENT_TYPES);
        when(orderService.getAllShippingTypes()).thenReturn(SHIPPING_TYPES);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(session.getAttribute(USER_ATTRIBUTE)).thenReturn(user);
        when(session.getAttribute(SHOPPING_CART)).thenReturn(cart);
        when(request.getSession()).thenReturn(session);
        when(cart.getAllProducts()).thenReturn(productMap);
    }

    @Test
    public void shouldSetTypesIntoSessionAndForwardToOrderPageIdUserIsLoginedWhenDoGetInvoked() throws ServletException, IOException {
        when(session.getAttribute(USER_ATTRIBUTE)).thenReturn(user);
        orderServlet.doGet(request, response);
        verify(request).setAttribute(PAYMENT_TYPES_ATTRIBUTE, PAYMENT_TYPES);
        verify(request).setAttribute(SHIPPING_TYPES_ATTRIBUTE, SHIPPING_TYPES);
        verify(request).getRequestDispatcher(ORDER_PAGE);
    }

    @Test
    public void shouldSetPreviousPageIntoSessionAndForwardToOrderPageIdUserIsNotLoginedWhenDoGetInvoked() throws ServletException, IOException {
        when(session.getAttribute(USER_ATTRIBUTE)).thenReturn(null);
        orderServlet.doGet(request, response);
        verify(session).setAttribute(PREVIOUS_PAGE, ORDER_SERVLET);
        verify(request).getRequestDispatcher(REGISTRATION_SERVLET);
    }

    @Test
    public void shouldRedirectToOrderServletAndSetErrorMessageIfInputIsInvalidWhenDoPostInvoked() throws IOException {
        when(orderFormBeanValidator.isValid(any(OrderFormBean.class))).thenReturn(false);
        orderServlet.doPost(request, response);
        verify(session).getAttribute(USER_ATTRIBUTE);
        verify(session).setAttribute(ERROR, ERR_MESSAGE);
        verify(response).sendRedirect(ORDER_SERVLET);
    }

    @Test
    public void shouldRedirectToOrderServletAndSetErrorMessageIfInputIsUserIsNullWhenDoPostInvoked() throws IOException {
        when(session.getAttribute(USER_ATTRIBUTE)).thenReturn(null);
        when(orderFormBeanValidator.isValid(any(OrderFormBean.class))).thenReturn(true);
        orderServlet.doPost(request, response);
        verify(session).getAttribute(USER_ATTRIBUTE);
        verify(session).setAttribute(ERROR, ERR_MESSAGE);
        verify(response).sendRedirect(ORDER_SERVLET);
    }

    @Test
    public void shouldRedirectToOrderConfirmServletAndCreatesOrderIfInputIsInputIsValidAndUserIsNotNulllWhenDoPostInvoked() throws IOException {
        when(orderFormBeanValidator.isValid(any(OrderFormBean.class))).thenReturn(true);
        when(orderConverter.convert(any(OrderFormBean.class))).thenReturn(order);
        orderServlet.doPost(request, response);
        verify(session).getAttribute(USER_ATTRIBUTE);
        verify(session).setAttribute(ORDER, order);
            verify(response).sendRedirect(ORDER_CONFIRM_SERVLET);
    }

}