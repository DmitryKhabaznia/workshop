package workshop.db.dao;

import workshop.bean.FilterFormBean;
import workshop.db.entity.impl.Category;
import workshop.db.entity.impl.Manufacturer;
import workshop.db.entity.impl.Product;
import workshop.exception.DBException;

import java.util.List;

/**
 * Product DAO that intended for managing {@link Product}s.
 */
public interface ProductDAO {

    /**
     * Returns count of products in database that meets requirements in specified {@link FilterFormBean}.
     *
     * @param filterFormBean specified {@link FilterFormBean}
     * @return count of products
     * @throws DBException if {@link java.sql.SQLException} was occurred
     */
    int getProductsCount(FilterFormBean filterFormBean) throws DBException;

    /**
     * Returns {@link List} of {@link Product}s from database that meets requirements in specified {@link FilterFormBean}.
     *
     * @param filterFormBean specified {@link FilterFormBean}
     * @return {@link List} with {@link Product}
     * @throws DBException if {@link java.sql.SQLException} was occurred
     */
    List<Product> getProductList(FilterFormBean filterFormBean) throws DBException;

    /**
     * Returns {@link Product} with specified id.
     *
     * @param id specified id
     * @return {@link Product} with specified id of null otherwise
     * @throws DBException if {@link java.sql.SQLException} was occurred
     */
    Product getProductById(long id) throws DBException;

    /**
     * Finds all {@link Manufacturer} in database.
     *
     * @return {@link List} with all manufacturers
     */
    List<Manufacturer> getAllManufacturers() throws DBException;

    /**
     * Finds all {@link Category} in database.
     *
     * @return {@link List} with all categories from database
     */
    List<Category> getAllCategories() throws DBException;

}
