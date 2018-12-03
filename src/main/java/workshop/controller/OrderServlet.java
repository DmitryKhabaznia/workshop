package workshop.controller;

import workshop.bean.Cart;
import workshop.bean.OrderFormBean;
import workshop.db.entity.impl.Order;
import workshop.db.entity.impl.OrderProduct;
import workshop.db.entity.impl.User;
import workshop.exception.ServiceException;
import workshop.service.OrderService;
import workshop.util.converter.impl.OrderConverter;
import workshop.util.validator.OrderFormBeanValidator;
import org.apache.log4j.Logger;
import workshop.constants.Constants;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Servlet intended for processing information of order.
 */
@WebServlet("/order")
public class OrderServlet extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(OrderServlet.class);
    private static final String CARD_NUMBER = "card";
    private static final String CARD_CVV_FIELD = "cvv";
    private static final String CARD_DATE_FIELD = "date";
    private static final String PAYMENT_TYPE_FIELD = "paymentType";
    private static final String DELIVERY_ADDRESS_FIELD = "deliveryAddress";
    private static final String SHIPPING_TYPE_FIELD = "deliveryType";
    private static final String ORDER = "order";
    private static final String ERR_CANNOT_ADD_ORDER = "Can not add new order.";
    private static final String ERR_MESSAGE = "Date is wrong.";
    private static final String ERROR = "error";
    private static final String PAYMENT_TYPES = "paymentTypes";
    private static final String SHIPPING_TYPES = "shippingTypes";
    private OrderFormBeanValidator orderFormBeanValidator;
    private OrderConverter orderConverter;
    private OrderService orderService;

    /**
     * {@inheritDoc}
     */
    @Override
    public void init(ServletConfig config) {
        ServletContext servletContext = config.getServletContext();
        orderService = (OrderService) servletContext.getAttribute(Constants.AppContextConstants.ORDER_SERVICE);
        orderFormBeanValidator = new OrderFormBeanValidator();
        orderConverter = new OrderConverter();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.debug("OrderFormBean servlet starts.");
        String forward = Constants.AppContextConstants.ORDER_PAGE;
        if (req.getSession().getAttribute(Constants.AppContextConstants.USER_ATTRIBUTE) == null) {
            forward = Constants.AppContextConstants.REGISTRATION_SERVLET;
            req.getSession().setAttribute(Constants.AppContextConstants.PREVIOUS_PAGE, Constants.AppContextConstants.ORDER_SERVLET);
        } else {
            try {
                req.setAttribute(PAYMENT_TYPES, orderService.getAllPaymentTypes());
                req.setAttribute(SHIPPING_TYPES, orderService.getAllShippingTypes());
            } catch (ServiceException e) {
                LOG.debug("Cannot obtain types.");
            }
        }
        LOG.debug("Forward to path: " + forward);
        req.getRequestDispatcher(forward).forward(req, resp);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        LOG.debug("OrderConfirm servlet starts. Method post.");
        String redirectPath;
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute(Constants.AppContextConstants.USER_ATTRIBUTE);
        OrderFormBean orderFormBean = createOrderBean(req);
        if (user == null || !orderFormBeanValidator.isValid(orderFormBean)) {
            session.setAttribute(ERROR, ERR_MESSAGE);
            redirectPath = Constants.AppContextConstants.ORDER_SERVLET;
        } else {
            Order order = getOrder(req, user, orderFormBean);
            req.getSession().setAttribute(ORDER, order);
            redirectPath = Constants.AppContextConstants.ORDER_CONFIRM_SERVLET;
        }
        LOG.debug("Redirect to path: " + redirectPath);
        resp.sendRedirect(redirectPath);
    }

    private OrderFormBean createOrderBean(HttpServletRequest request) {
        OrderFormBean orderFormBean = new OrderFormBean();
        orderFormBean.setDeliveryAddress(request.getParameter(DELIVERY_ADDRESS_FIELD));
        orderFormBean.setShippingType(request.getParameter(SHIPPING_TYPE_FIELD));
        orderFormBean.setPaymentType(request.getParameter(PAYMENT_TYPE_FIELD));
        orderFormBean.setPaymentCardNumber(request.getParameter(CARD_NUMBER));
        orderFormBean.setPaymentCardCVV(request.getParameter(CARD_CVV_FIELD));
        orderFormBean.setPaymentCardDate(request.getParameter(CARD_DATE_FIELD));
        return orderFormBean;
    }

    private Order getOrder(HttpServletRequest request, User user, OrderFormBean orderFormBean) {
        Order order = orderConverter.convert(orderFormBean);
        order.setOrderProducts(getOrderItems(request));
        order.setUser(user);
        return order;
    }

    private List<OrderProduct> getOrderItems(HttpServletRequest request) {
        Cart cart = (Cart) request.getSession().getAttribute(Constants.AppContextConstants.SHOPPING_CART);
        return cart == null ? null : cart
                .getAllProducts()
                .entrySet()
                .stream()
                .map(entry -> new OrderProduct(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

}
