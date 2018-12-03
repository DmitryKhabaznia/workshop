package workshop.controller;

import workshop.bean.Cart;
import workshop.bean.CartResponseBean;
import workshop.db.entity.impl.Product;
import workshop.exception.ServiceException;
import workshop.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import static workshop.constants.Constants.AppContextConstants.SHOPPING_CART;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ShoppingCartServletTest {

    private static final String PRODUCT_COUNT = "productCount";
    private static final String PRODUCT_ID = "productId";
    private static final int COUNT = 10;
    private static final double TOTAL_SUM = 10;
    private static final double SUBTOTAL = 5;
    private static final long ID = 1;
    private static final long WRONG_ID = -1;

    @Mock
    private ProductService productService;
    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpSession session;
    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private Cart cart;
    @Mock
    private Map<Product, Integer> products;
    @Mock
    private Product product;

    @InjectMocks
    private ShoppingCartServlet shoppingCartServlet;

    @Before
    public void setUp() throws ServiceException {
        when(cart.getCount()).thenReturn(COUNT);
        when(cart.getTotalSum()).thenReturn(TOTAL_SUM);
        when(cart.getAllProducts()).thenReturn(products);
        when(cart.getSubtotal(anyInt())).thenReturn(SUBTOTAL);
        when(cart.removeProduct(anyInt())).thenReturn(true);
        when(product.getId()).thenReturn(ID);
        when(productService.getProductById(ID)).thenReturn(product);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(SHOPPING_CART)).thenReturn(cart);
        when(request.getParameter(PRODUCT_COUNT)).thenReturn(String.valueOf(COUNT));
    }

    @Test
    public void shouldAddProductToCartAndSetAttributesToSession() throws IOException {
        when(request.getParameter(PRODUCT_ID)).thenReturn(String.valueOf(ID));
        shoppingCartServlet.doPut(request, response);
        verify(session).getAttribute(SHOPPING_CART);
        verify(request).getParameter(PRODUCT_ID);
        verify(request).getParameter(PRODUCT_COUNT);
        verify(cart).addProduct(product, COUNT);
        verify(cart).getCount();
        verify(cart).getTotalSum();
        verify(objectMapper).writeValue(any(Writer.class), any(CartResponseBean.class));
    }

    @Test
    public void shouldChangeCountOfProductAndSetAttributesToSessionIfIdIsValid() throws IOException {
        when(request.getParameter(PRODUCT_ID)).thenReturn(String.valueOf(ID));
        shoppingCartServlet.doPost(request, response);
        verify(session).getAttribute(SHOPPING_CART);
        verify(request).getParameter(PRODUCT_ID);
        verify(request).getParameter(PRODUCT_COUNT);
        verify(cart).changeCount(ID, COUNT);
        verify(cart).getCount();
        verify(cart).getTotalSum();
        verify(cart).getSubtotal(ID);
        verify(objectMapper).writeValue(any(Writer.class), any(CartResponseBean.class));
    }

    @Test
    public void shouldNotChangeCountOfProductAndSetAttributesToSessionIfIdIsInValid() throws IOException {
        when(request.getParameter(PRODUCT_ID)).thenReturn(String.valueOf(WRONG_ID));
        shoppingCartServlet.doPost(request, response);
        verify(session).getAttribute(SHOPPING_CART);
        verify(request).getParameter(PRODUCT_ID);
        verify(cart).getCount();
        verify(cart).getTotalSum();
        verify(objectMapper).writeValue(any(Writer.class), any(CartResponseBean.class));
    }

    @Test
    public void shouldRemoveProductByIdIfIdIsValidAndSetAttributesToSession() throws IOException {
        when(request.getParameter(PRODUCT_ID)).thenReturn(String.valueOf(ID));
        shoppingCartServlet.doDelete(request, response);
        verify(session).getAttribute(SHOPPING_CART);
        verify(request).getParameter(PRODUCT_ID);
        verify(cart).removeProduct(ID);
        verify(cart).getCount();
        verify(cart).getTotalSum();
        verify(objectMapper).writeValue(any(Writer.class), any(CartResponseBean.class));
    }

    @Test
    public void shouldRemoveProductByIdIfIdIsInValidAndSetAttributesToSession() throws IOException {
        when(request.getParameter(PRODUCT_ID)).thenReturn(String.valueOf(WRONG_ID));
        shoppingCartServlet.doDelete(request, response);
        verify(session).getAttribute(SHOPPING_CART);
        verify(request).getParameter(PRODUCT_ID);
        verify(cart).removeAll();
        verify(cart).getCount();
        verify(cart).getTotalSum();
        verify(objectMapper).writeValue(any(Writer.class), any(CartResponseBean.class));
    }

}