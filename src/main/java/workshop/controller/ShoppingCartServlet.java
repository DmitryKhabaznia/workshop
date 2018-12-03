package workshop.controller;

import workshop.bean.CartResponseBean;
import workshop.bean.Cart;
import workshop.db.entity.impl.Product;
import workshop.exception.AppException;
import workshop.service.ProductService;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static workshop.constants.Constants.AppContextConstants.*;
import static workshop.constants.Constants.AppContextConstants.ERROR_MESSAGE_DIV;
import static workshop.constants.Constants.AppContextConstants.SHOPPING_CART;

/**
 * Servlet that intended for managing of products in the shopping cart.
 */
@WebServlet("/shoppingCart")
public class ShoppingCartServlet extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(ShoppingCartServlet.class);
    private static final String PRODUCT_COUNT = "productCount";
    private static final String PRODUCT_ID = "productId";
    private ProductService productService;
    private ObjectMapper mapper;

    /**
     * {@inheritDoc}
     */
    @Override
    public void init(ServletConfig config) {
        ServletContext servletContext = config.getServletContext();
        productService = (ProductService) servletContext.getAttribute(PRODUCT_SERVICE);
        mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(SHOPPING_CART_PAGE).forward(req, resp);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Cart cart = getShoppingCart(req);
        long id = getId(req);
        CartResponseBean cartResponseBean;
        if (id < 0) {
            cartResponseBean = new CartResponseBean(cart.getCount(), cart.getTotalSum());
        } else {
            cart.changeCount(id, getCount(req));
            cartResponseBean = new CartResponseBean(cart.getCount(), cart.getTotalSum(), cart.getSubtotal(id));
        }
        setResponse(req.getSession(), resp, cart, cartResponseBean);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            Cart cart = getShoppingCart(req);
            long id = getId(req);
            if (id >= 0) {
                Product product = productService.getProductById(id);
                cart.addProduct(product, getCount(req));
            }
            CartResponseBean cartResponseBean = new CartResponseBean(cart.getCount(), cart.getTotalSum());
            setResponse(req.getSession(), resp, cart, cartResponseBean);
        } catch (AppException ex) {
            LOG.error("Can't add product to cart.", ex);
            req.setAttribute(ERROR_MESSAGE_DIV, ex.getMessage());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Cart cart = getShoppingCart(req);
        long id = getId(req);
        if (id < 0) {
            cart.removeAll();
        } else {
            cart.removeProduct(id);
        }
        CartResponseBean cartResponseBean = new CartResponseBean(cart.getCount(), cart.getTotalSum());
        setResponse(req.getSession(), resp, cart, cartResponseBean);
    }

    private long getId(HttpServletRequest req) {
        String id = req.getParameter(PRODUCT_ID);
        return NumberUtils.isDigits(id) ? Long.parseLong(id) : -1;
    }

    private int getCount(HttpServletRequest req) {
        String count = req.getParameter(PRODUCT_COUNT);
        int parsedValue = NumberUtils.isDigits(count) ? Integer.parseInt(count) : -1;
        return parsedValue < 0 ? 0 : parsedValue;
    }

    private Cart getShoppingCart(HttpServletRequest request) {
        Cart cart = (Cart) request.getSession().getAttribute(SHOPPING_CART);
        return cart == null ? new Cart() : cart;
    }

    private void setResponse(HttpSession session, HttpServletResponse resp, Cart cart, CartResponseBean cartBean) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        mapper.writeValue(resp.getWriter(), cartBean);
        session.setAttribute(SHOPPING_CART, cart);
    }

}
