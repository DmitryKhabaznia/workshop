package workshop.controller;

import workshop.bean.FilterFormBean;
import workshop.db.dto.ProductsDTO;
import workshop.db.entity.impl.Category;
import workshop.db.entity.impl.Manufacturer;
import workshop.exception.AppException;
import workshop.exception.ServiceException;
import workshop.service.ProductService;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;
import workshop.constants.Constants;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static java.lang.Integer.parseInt;

/**
 * Servlet for sorting and filtration of products.
 */
@WebServlet("/products")
public class ProductServlet extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(ProductServlet.class);
    private static final int DEFAULT_COUNT_OF_PRODUCTS_ON_PAGE = 6;
    private static final int DEFAULT_PAGE_NUMBER = 1;
    private static final int MAX_COUNT_OF_PRODUCTS_ON_PAGE = 9;
    private static final int[] EMPTY_ARRAY = new int[0];
    private static final String MANUFACTURERS = "manufacturers";
    private static final String CATEGORIES = "categories";
    private static final String PRODUCTS = "products";
    private static final String FILTER_FORM_BEAN = "formBean";
    private static final String ERR_MESSAGE = "Some problem with our application was occurred.";

    private ProductService productService;

    /**
     * {@inheritDoc}
     */
    @Override
    public void init(ServletConfig config) {
        ServletContext servletContext = config.getServletContext();
        productService = (ProductService) servletContext.getAttribute(Constants.AppContextConstants.PRODUCT_SERVICE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.debug("Product servlet starts. Method GET.");
        String forward = Constants.AppContextConstants.ERROR_PAGE;
        try {
            FilterFormBean filterFormBean = getFormBean(req);
            setProducts(req, filterFormBean);
            setManufacturers(req);
            setCategories(req);
            setFilterFormBean(req, filterFormBean);
            forward = Constants.AppContextConstants.PRODUCTS_PAGE;
        } catch (AppException ex) {
            LOG.error("Can not process doGet method", ex);
            req.setAttribute(Constants.AppContextConstants.ERROR_MESSAGE_DIV, ERR_MESSAGE);
        }
        LOG.debug("Forward to path: " + forward);
        req.getRequestDispatcher(forward).forward(req, resp);
    }

    private void setFilterFormBean(HttpServletRequest req, FilterFormBean filterFormBean) {
        LOG.debug("Filter form bean obtained: -> " + filterFormBean);
        req.setAttribute(FILTER_FORM_BEAN, filterFormBean);
    }

    private void setProducts(HttpServletRequest req, FilterFormBean filterFormBean) throws ServiceException {
        ProductsDTO productsDTO = productService.getProductsDTO(filterFormBean);
        req.setAttribute(PRODUCTS, productsDTO.getProductList());
        req.setAttribute(Constants.FilterFormBeanFields.LAST_PAGE_NUMBER, productsDTO.getLastPageNumber());
    }

    private void setManufacturers(HttpServletRequest req) throws ServiceException {
        List<Manufacturer> manufacturers = productService.getAllManufacturers();
        req.setAttribute(MANUFACTURERS, manufacturers);
    }

    private void setCategories(HttpServletRequest req) throws ServiceException {
        List<Category> categories = productService.getAllCategories();
        req.setAttribute(CATEGORIES, categories);
    }

    private FilterFormBean getFormBean(HttpServletRequest request) {
        FilterFormBean filterFormBean = new FilterFormBean();
        filterFormBean.setManufacturer(convertToInt(request.getParameter(Constants.FilterFormBeanFields.MANUFACTURER_FIELD)));
        filterFormBean.setCategories(getCategories(request));
        filterFormBean.setMinPrice(convertToInt(request.getParameter(Constants.FilterFormBeanFields.MIN_PRICE_FIELD)));
        filterFormBean.setMaxPrice(convertToInt(request.getParameter(Constants.FilterFormBeanFields.MAX_PRICE_FIELD)));
        filterFormBean.setNameInput(request.getParameter(Constants.FilterFormBeanFields.NAME_FIELD));
        filterFormBean.setSortingType(getSortingType(request.getParameter(Constants.FilterFormBeanFields.SORTING_TYPE_FIELD)));
        filterFormBean.setPageNumber(getPageNumber(request.getParameter(Constants.FilterFormBeanFields.PAGE_NUMBER_FIELD)));
        filterFormBean.setProductCountOnPage(getProductCountOnPage(request.getParameter(Constants.FilterFormBeanFields.PRODUCT_COUNT_ON_PAGE_FIELD)));
        return filterFormBean;
    }

    private int[] getCategories(HttpServletRequest req) {
        String[] categories = req.getParameterValues(Constants.FilterFormBeanFields.CATEGORY_FIELD);
        return categories == null ? EMPTY_ARRAY : Arrays
                .stream(req.getParameterValues(Constants.FilterFormBeanFields.CATEGORY_FIELD))
                .mapToInt(this::convertToInt)
                .filter(category -> category > 0)
                .toArray();
    }

    private int convertToInt(String value) {
        return NumberUtils.isDigits(value)
                ? parseInt(value)
                : -1;
    }

    private int getProductCountOnPage(String value) {
        int count = NumberUtils.isDigits(value)
                ? parseInt(value)
                : DEFAULT_COUNT_OF_PRODUCTS_ON_PAGE;
        return count > MAX_COUNT_OF_PRODUCTS_ON_PAGE
                ? MAX_COUNT_OF_PRODUCTS_ON_PAGE
                : count;
    }

    private int getPageNumber(String value) {
        return NumberUtils.isDigits(value)
                ? parseInt(value)
                : DEFAULT_PAGE_NUMBER;
    }

    private String getSortingType(String value) {
        return value != null && !value.isEmpty()
                ? value
                : Constants.FilterFormBeanFields.DEFAULT_SORTING;
    }

}
