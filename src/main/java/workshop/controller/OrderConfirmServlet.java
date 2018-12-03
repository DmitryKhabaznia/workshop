package workshop.controller;

import workshop.db.entity.impl.Order;
import workshop.service.OrderService;
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

/**
 * Servlet for confirmation of order.
 */
@WebServlet("/orderConfirm")
public class OrderConfirmServlet extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(OrderConfirmServlet.class);
    private static final String ORDER = "order";
    private static final String ERR_CANNOT_ADD_ORDER = "Can not add new order.";
    private static final String RESULT = "resultMessage";
    private static final String RES_MESSAGE = "Your order was registered. Order id: ";
    private OrderService orderService;

    /**
     * {@inheritDoc}
     */
    @Override
    public void init(ServletConfig config) {
        ServletContext servletContext = config.getServletContext();
        orderService = (OrderService) servletContext.getAttribute(Constants.AppContextConstants.ORDER_SERVICE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.debug("Forward to path: " + Constants.AppContextConstants.ORDER_CONFIRM_PAGE);
        req.getRequestDispatcher(Constants.AppContextConstants.ORDER_CONFIRM_PAGE).forward(req, resp);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        LOG.debug("OrderConfirm servlet starts. Method post.");
        String forward = Constants.AppContextConstants.ERROR_PAGE;
        try {
            HttpSession session = req.getSession();
            Order order = (Order) session.getAttribute(ORDER);
            order = orderService.addOrder(order);
            session.setAttribute(ORDER, order);
            forward = Constants.AppContextConstants.ORDER_CONFIRM_SERVLET;
            session.removeAttribute(Constants.AppContextConstants.SHOPPING_CART);
            session.setAttribute(RESULT, RES_MESSAGE + order.getId());
        } catch (Exception ex) {
            LOG.error(ERR_CANNOT_ADD_ORDER, ex);
            req.setAttribute(Constants.AppContextConstants.ERROR_MESSAGE_DIV, ERR_CANNOT_ADD_ORDER);
        }
        LOG.debug("Redirect to path: " + forward);
        resp.sendRedirect(forward);
    }

}
