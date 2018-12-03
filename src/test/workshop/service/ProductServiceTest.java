package workshop.service;

import workshop.bean.FilterFormBean;
import workshop.db.Action;
import workshop.db.TransactionManager;
import workshop.db.dto.ProductsDTO;
import workshop.db.entity.impl.Product;
import workshop.exception.DBException;
import workshop.exception.ServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {

    private static final int COUNT = 1;
    private static final long ID = 1;
    public static final int ZERO = 0;

    @Mock
    private TransactionManager transactionManager;
    @Mock
    private FilterFormBean filterFormBean;
    @Mock
    private Product product;
    @Mock
    private List list;
    @Mock
    private ProductsDTO productsDTO;

    @InjectMocks
    private ProductService productService;

    @Test
    public void shouldReturnSpecifiedCount() throws DBException, ServiceException {
        when(transactionManager.doInTransactionWithResult(any(Action.class))).thenReturn(COUNT);
        assertEquals(productService.getCountOfProducts(filterFormBean), COUNT);
    }

    @Test
    public void shouldReturnProduct() throws ServiceException, DBException {
        when(transactionManager.doInTransactionWithResult(any(Action.class))).thenReturn(product);
        assertEquals(productService.getProductById(ID), product);
    }

    @Test
    public void shouldReturnMockedListCategories() throws ServiceException, DBException {
        when(transactionManager.doInTransactionWithResult(any(Action.class))).thenReturn(list);
        assertEquals(productService.getAllCategories(), list);
    }

    @Test
    public void shouldReturnMockedListManufacturers() throws ServiceException, DBException{
        when(transactionManager.doInTransactionWithResult(any(Action.class))).thenReturn(list);
        assertEquals(productService.getAllManufacturers(), list);
    }

    @Test
    public void shouldReturnMockedListProducts() throws ServiceException, DBException{
        when(transactionManager.doInTransactionWithResult(any(Action.class))).thenReturn(list);
        assertEquals(productService.getProductsList(filterFormBean), list);
    }

    @Test (expected = ServiceException.class)
    public void shouldTrowServiceExceptionIfDBExceptionWasOccurredWhenGetCountOfProductWasInvoked() throws DBException, ServiceException {
        when(transactionManager.doInTransactionWithResult(any(Action.class))).thenThrow(DBException.class);
        productService.getCountOfProducts(filterFormBean);
    }

    @Test (expected = ServiceException.class)
    public void shouldTrowServiceExceptionIfDBExceptionWasOccurredWhenGetProductByIdWasInvoked() throws DBException, ServiceException {
        when(transactionManager.doInTransactionWithResult(any(Action.class))).thenThrow(DBException.class);
        productService.getProductById(ID);
    }

    @Test (expected = ServiceException.class)
    public void shouldTrowServiceExceptionIfDBExceptionWasOccurredWhenGetProductsListWasInvoked() throws DBException, ServiceException {
        when(transactionManager.doInTransactionWithResult(any(Action.class))).thenThrow(DBException.class);
        productService.getProductsList(filterFormBean);
    }

    @Test (expected = ServiceException.class)
    public void shouldTrowServiceExceptionIfDBExceptionWasOccurredWhenGetAllCategoriesWasInvoked() throws DBException, ServiceException {
        when(transactionManager.doInTransactionWithResult(any(Action.class))).thenThrow(DBException.class);
        productService.getAllCategories();
    }

    @Test (expected = ServiceException.class)
    public void shouldTrowServiceExceptionIfDBExceptionWasOccurredWhenGetAllManufacturersWasInvoked() throws DBException, ServiceException {
        when(transactionManager.doInTransactionWithResult(any(Action.class))).thenThrow(DBException.class);
        productService.getAllManufacturers();
    }

    @Test
    public void shouldReturnProductDTOMock() throws ServiceException, DBException {
        when(transactionManager.doInTransactionWithResult(any(Action.class))).thenReturn(ZERO);
        when(filterFormBean.getProductCountOnPage()).thenReturn(COUNT);
        when(filterFormBean.getPageNumber()).thenReturn(ZERO);
        assert (productService.getProductsDTO(filterFormBean).getLastPageNumber() == ZERO);
        assert (productService.getProductsDTO(filterFormBean).getProductList().equals(Collections.emptyList()));
    }

}