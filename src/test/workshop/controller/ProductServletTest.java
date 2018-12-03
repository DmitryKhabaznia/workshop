package workshop.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import workshop.bean.FilterFormBean;
import workshop.controller.ProductServlet;
import workshop.db.dto.ProductsDTO;
import workshop.exception.ServiceException;
import workshop.service.ProductService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static workshop.constants.Constants.AppContextConstants.PRODUCTS_PAGE;
import static workshop.constants.Constants.FilterFormBeanFields.LAST_PAGE_NUMBER;

@RunWith(MockitoJUnitRunner.class)
public class ProductServletTest {

    private static final String MANUFACTURERS = "manufacturers";
    private static final String CATEGORIES = "categories";
    private static final String PRODUCTS = "products";
    private static final String[] STUB_CATEGORIES = {"1"};
    private static final String STUB_VALUE = "1";
    private static final int ZERO = 0;
    private static final int ONE = 1;

    @Mock
    private ProductService productService;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private ProductsDTO productsDTO;
    @Mock
    private List list;

    @InjectMocks
    private ProductServlet productServlet;

    @Test
    public void shouldSetEmptyListOfProductsWhenZeroCount() throws ServiceException, ServletException, IOException {
        when(productService.getAllCategories()).thenReturn(list);
        when(productService.getAllManufacturers()).thenReturn(list);
        when(productService.getProductsDTO(any(FilterFormBean.class))).thenReturn(productsDTO);
        when(productService.getCountOfProducts(any(FilterFormBean.class))).thenReturn(ZERO);
        when(request.getRequestDispatcher(PRODUCTS_PAGE)).thenReturn(requestDispatcher);
        productServlet.doGet(request, response);
        verify(request).setAttribute(MANUFACTURERS, list);
        verify(request).setAttribute(PRODUCTS, Collections.EMPTY_LIST);
        verify(request).setAttribute(CATEGORIES, list);
        verify(request).setAttribute(LAST_PAGE_NUMBER, ZERO);
        verify(request).getRequestDispatcher(PRODUCTS_PAGE);
    }

    @Test
    public void shouldSetNotEmptyListOfProductsWhenCountIsNotZero() throws ServiceException, ServletException, IOException {
        when(productService.getProductsDTO(any(FilterFormBean.class))).thenReturn(productsDTO);
        when(productsDTO.getProductList()).thenReturn(list);
        when(productsDTO.getLastPageNumber()).thenReturn(ONE);
        when(request.getParameter(anyString())).thenReturn(STUB_VALUE);
        when(request.getParameterValues(anyString())).thenReturn(STUB_CATEGORIES);
        when(productService.getAllCategories()).thenReturn(list);
        when(productService.getAllManufacturers()).thenReturn(list);
        when(productService.getCountOfProducts(any(FilterFormBean.class))).thenReturn(ONE);
        when(productService.getProductsList(any(FilterFormBean.class))).thenReturn(list);
        when(request.getRequestDispatcher(PRODUCTS_PAGE)).thenReturn(requestDispatcher);
        productServlet.doGet(request, response);
        verify(request).setAttribute(MANUFACTURERS, list);
        verify(request).setAttribute(PRODUCTS, list);
        verify(request).setAttribute(CATEGORIES, list);
        verify(request).setAttribute(LAST_PAGE_NUMBER, ONE);
        verify(request).getRequestDispatcher(PRODUCTS_PAGE);
    }

}