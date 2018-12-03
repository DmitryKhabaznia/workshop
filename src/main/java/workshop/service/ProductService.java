package workshop.service;

import workshop.bean.FilterFormBean;
import workshop.db.TransactionManager;
import workshop.db.dao.ProductDAO;
import workshop.db.dto.ProductsDTO;
import workshop.db.entity.impl.Category;
import workshop.db.entity.impl.Manufacturer;
import workshop.db.entity.impl.Product;
import workshop.exception.DBException;
import workshop.exception.ServiceException;
import org.apache.log4j.Logger;

import java.util.Collections;
import java.util.List;

/**
 * Service that works with {@link TransactionManager} and {@link ProductDAO}.
 */
public class ProductService {

    private static final Logger LOG = Logger.getLogger(ProductService.class);
    private static final String ERR_CANNOT_GET_COUNT_OF_PRODUCTS = "Cannot get count of products.";
    private static final String ERR_CANNOT_GET_LIST_OF_PRODUCTS = "Cannot get list of products.";
    private static final String CAN_NOT_GET_ALL_CATEGORIES = "Can not get all categories.";
    private static final String CANNOT_GET_ALL_MANUFACTURERS = "Can not get all manufacturers";
    private static final String ERR_CANNOT_GET_PRODUCT_BY_ID = "Can not get product by id";

    private TransactionManager transactionManager;
    private ProductDAO productDAO;

    public ProductService(TransactionManager transactionManager, ProductDAO productDAO) {
        this.transactionManager = transactionManager;
        this.productDAO = productDAO;
    }

    /**
     * Returns count of products in database that meets requirements in specified {@link FilterFormBean}.
     *
     * @param filterFormBean specified {@link FilterFormBean}
     * @return count of products
     * @throws ServiceException if {@link DBException} was occurred
     */
    public int getCountOfProducts(FilterFormBean filterFormBean) throws ServiceException {
        int count;
        try {
            count = transactionManager.doInTransactionWithResult(() -> productDAO.getProductsCount(filterFormBean));
            LOG.debug("Count of products: -> " + count);
        } catch (DBException ex) {
            LOG.error(ERR_CANNOT_GET_COUNT_OF_PRODUCTS, ex);
            throw new ServiceException(ERR_CANNOT_GET_COUNT_OF_PRODUCTS, ex);
        }
        return count;
    }

    /**
     * Returns {@link List} of {@link Product}s from database that meets requirements in specified {@link FilterFormBean}.
     *
     * @param filterFormBean specified {@link FilterFormBean}
     * @return {@link List} with {@link Product}
     * @throws ServiceException if {@link DBException} was occurred
     */
    public List<Product> getProductsList(FilterFormBean filterFormBean) throws ServiceException {
        List<Product> products;
        try {
            products = transactionManager.doInTransactionWithResult(() -> productDAO.getProductList(filterFormBean));
        } catch (DBException ex) {
            LOG.error(ERR_CANNOT_GET_LIST_OF_PRODUCTS, ex);
            throw new ServiceException(ERR_CANNOT_GET_LIST_OF_PRODUCTS, ex);
        }
        return products;
    }

    public Product getProductById(long id) throws ServiceException {
        Product product;
        try {
            product = transactionManager.doInTransactionWithResult(() -> productDAO.getProductById(id));
        } catch (DBException ex) {
            LOG.error(ERR_CANNOT_GET_PRODUCT_BY_ID + id, ex);
            throw new ServiceException(ERR_CANNOT_GET_PRODUCT_BY_ID, ex);
        }
        return product;
    }

    /**
     * Finds all {@link Category}s in database.
     *
     * @return {@link List} with all {@link Category}s
     * @throws ServiceException if {@link DBException} was occurred
     */
    public List<Category> getAllCategories() throws ServiceException {
        List<Category> categories;
        try {
            categories = transactionManager.doInTransactionWithResult(() -> productDAO.getAllCategories());
        } catch (DBException ex) {
            LOG.error(CAN_NOT_GET_ALL_CATEGORIES, ex);
            throw new ServiceException(CAN_NOT_GET_ALL_CATEGORIES, ex);
        }
        return categories;
    }

    /**
     * Finds all {@link Manufacturer}s in database.
     *
     * @return {@link List} with all {@link Manufacturer}s
     * @throws ServiceException if {@link DBException} was occurred
     */
    public List<Manufacturer> getAllManufacturers() throws ServiceException {
        List<Manufacturer> manufacturers;
        try {
            manufacturers = transactionManager.doInTransactionWithResult(() -> productDAO.getAllManufacturers());
        } catch (DBException ex) {
            LOG.error(CANNOT_GET_ALL_MANUFACTURERS, ex);
            throw new ServiceException(CANNOT_GET_ALL_MANUFACTURERS, ex);
        }
        return manufacturers;
    }

    /**
     * Returns DTO with products, count of this products and last number of page for pagination.
     *
     * @param filterFormBean specified {@link FilterFormBean}
     * @return {@link ProductsDTO}
     */
    public ProductsDTO getProductsDTO(FilterFormBean filterFormBean) throws ServiceException {
        ProductsDTO productsDTO = new ProductsDTO();
        int countOfProducts = getCountOfProducts(filterFormBean);
        int lastPageNumber = (int) Math.ceil((double) countOfProducts / filterFormBean.getProductCountOnPage());
        productsDTO.setLastPageNumber(lastPageNumber);
        productsDTO.setProductList(countOfProducts == 0 || lastPageNumber < filterFormBean.getPageNumber()
                ? Collections.emptyList()
                : getProductsList(filterFormBean));
        return productsDTO;
    }

}
