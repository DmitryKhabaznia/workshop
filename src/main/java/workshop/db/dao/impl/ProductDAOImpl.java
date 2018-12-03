package workshop.db.dao.impl;

import workshop.bean.FilterFormBean;
import workshop.db.ConnectionHolder;
import workshop.db.dao.ProductDAO;
import workshop.db.entity.impl.Category;
import workshop.db.entity.impl.Manufacturer;
import workshop.db.entity.impl.Product;
import workshop.exception.DBException;
import workshop.util.ProductSQLBuilder;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;
import workshop.util.ResourcesCloserUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static workshop.util.ResourcesCloserUtil.close;

/**
 * Implementation of {@link ProductDAO}.
 */
public class ProductDAOImpl implements ProductDAO {

    private static final Logger LOG = Logger.getLogger(ProductDAOImpl.class);
    private static final String ERR_CANNOT_OBTAIN_COUNT_OF_PRODUCTS = "Can not obtain count of products.";
    private static final String ERR_CANNOT_OBTAIN_LIST_OF_PRODUCTS = "Can not obtain count of products with specified filters.";
    private static final String ERR_CANNOT_EXTRACT_PRODUCT = "Can not extract product.";
    private static final String COUNT_FIELD = "count";
    private static final String ENTITY_ID = "id";
    private static final String NAME_FIELD = "name";
    private static final String PRICE_FIELD = "price";
    private static final String DESCRIPTION_FIELD = "description";
    private static final String CATEGORY_ID_FIELD = "category_id";
    private static final String CATEGORY_NAME_FIELD = "category_name";
    private static final String MANUFACTURER_ID_FIELD = "manufacturer_id";
    private static final String MANUFACTURER_NAME_FIELD = "manufacturer_name";
    private static final String IMAGE_NAME_FIELD = "image";
    private static final String PERCENTAGE_SIGN = "%";
    private static final String MANUFACTURER_NAME = "name";
    private static final String CATEGORY_NAME = "name";
    private static final String ERR_CANNOT_EXTRACT_MANUFACTURER = "Can not extract manufacturer from result set.";
    private static final String ERR_CANNOT_OBTAIN_ALL_MANUFACTURERS = "Can not obtain all manufacturers";
    private static final String ERR_CANNOT_EXTRACT_CATEGORY = "Can not extract category from result set.";
    private static final String ERR_CANNOT_OBTAIN_ALL_CATEGORIES = "Can not obtain all categories.";
    private static final String SQL_FIND_ALL_MANUFACTURERS = "SELECT * FROM manufacturers;";
    private static final String SQL_FIND_ALL_CATEGORIES = "SELECT * FROM categories;";
    private static final String SQL_FIND_PRODUCT_BY_ID = "SELECT a.id AS id, a.name AS name, a.price AS price, " +
            "a.description AS description, a.image AS image, b.id AS category_id, " +
            "b.name AS category_name, c.id AS manufacturer_id, c.name AS manufacturer_name " +
            "FROM products AS a, categories AS b, manufacturers AS c " +
            "WHERE a.category_id = b.id AND a.manufacturer_id = c.id AND a.id = ?;";
    private static final String ERR_CANNOT_OBTAIN_PRODUCT_WITH_ID = "Can not obtain product with specified id:";
    private ConnectionHolder connectionHolder;
    private ProductSQLBuilder productSQLBuilder;

    public ProductDAOImpl(ConnectionHolder connectionHolder) {
        this.connectionHolder = connectionHolder;
        productSQLBuilder = new ProductSQLBuilder();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getProductsCount(FilterFormBean filterFormBean) throws DBException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            Connection connection = connectionHolder.getConnection();
            pstmt = connection.prepareStatement(productSQLBuilder.getCountSQLQuery(filterFormBean));
            injectName(pstmt, filterFormBean.getNameInput());
            rs = pstmt.executeQuery();
            String count = null;
            if (rs.next()) {
                count = rs.getString(COUNT_FIELD);
            }
            return NumberUtils.isDigits(count) ? Integer.parseInt(count) : -1;
        } catch (SQLException ex) {
            LOG.error(ERR_CANNOT_OBTAIN_COUNT_OF_PRODUCTS + filterFormBean.toString(), ex);
            throw new DBException(ERR_CANNOT_OBTAIN_COUNT_OF_PRODUCTS, ex);
        } finally {
            ResourcesCloserUtil.close(pstmt, rs);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Product> getProductList(FilterFormBean filterFormBean) throws DBException {
        List<Product> products = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            Connection connection = connectionHolder.getConnection();
            pstmt = connection.prepareStatement(productSQLBuilder.getProductsListSQLQuery(filterFormBean));
            injectName(pstmt, filterFormBean.getNameInput());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                products.add(mapProduct(rs));
            }
            return products;
        } catch (SQLException ex) {
            LOG.error(ERR_CANNOT_OBTAIN_LIST_OF_PRODUCTS, ex);
            throw new DBException(ERR_CANNOT_OBTAIN_LIST_OF_PRODUCTS, ex);
        } finally {
            ResourcesCloserUtil.close(pstmt, rs);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Product getProductById(long id) throws DBException {
        Product result = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            Connection connection = connectionHolder.getConnection();
            pstmt = connection.prepareStatement(SQL_FIND_PRODUCT_BY_ID);
            pstmt.setString(1, String.valueOf(id));
            rs = pstmt.executeQuery();
            while (rs.next()) {
                result = (mapProduct(rs));
            }
            return result;
        } catch (SQLException ex) {
            LOG.error(ERR_CANNOT_OBTAIN_PRODUCT_WITH_ID + id, ex);
            throw new DBException(ERR_CANNOT_OBTAIN_PRODUCT_WITH_ID, ex);
        } finally {
            ResourcesCloserUtil.close(pstmt, rs);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Manufacturer> getAllManufacturers() throws DBException {
        List<Manufacturer> manufacturers = new ArrayList<>();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            Connection connection = connectionHolder.getConnection();
            stmt = connection.createStatement();
            rs = stmt.executeQuery(SQL_FIND_ALL_MANUFACTURERS);
            while (rs.next()) {
                manufacturers.add(mapManufacturer(rs));
            }
            return manufacturers;
        } catch (SQLException ex) {
            LOG.error(ERR_CANNOT_OBTAIN_ALL_MANUFACTURERS, ex);
            throw new DBException(ERR_CANNOT_OBTAIN_ALL_MANUFACTURERS, ex);
        } finally {
            ResourcesCloserUtil.close(stmt, rs);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Category> getAllCategories() throws DBException {
        List<Category> categories = new ArrayList<>();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            Connection connection = connectionHolder.getConnection();
            stmt = connection.createStatement();
            rs = stmt.executeQuery(SQL_FIND_ALL_CATEGORIES);
            while (rs.next()) {
                categories.add(mapCategory(rs));
            }
            return categories;
        } catch (SQLException ex) {
            LOG.error(ERR_CANNOT_OBTAIN_ALL_CATEGORIES, ex);
            throw new DBException(ERR_CANNOT_OBTAIN_ALL_CATEGORIES, ex);
        } finally {
            ResourcesCloserUtil.close(stmt, rs);
        }
    }

    private void injectName(PreparedStatement pstmt, String specifiedName) throws SQLException {
        if (specifiedName != null && !specifiedName.isEmpty()) {
            specifiedName = PERCENTAGE_SIGN + specifiedName + PERCENTAGE_SIGN;
            pstmt.setString(1, specifiedName);
        }
    }


    private Product mapProduct(ResultSet rs) throws DBException {
        Product product = new Product();
        try {
            product.setId(rs.getLong(ENTITY_ID));
            product.setName(rs.getString(NAME_FIELD));
            product.setDescription(rs.getString(DESCRIPTION_FIELD));
            product.setPrice(rs.getBigDecimal(PRICE_FIELD));
            product.setCategory(mapProductCategory(rs));
            product.setManufacturer(mapProductManufacturer(rs));
            product.setImageName(rs.getString(IMAGE_NAME_FIELD));
        } catch (SQLException ex) {
            LOG.error(ERR_CANNOT_EXTRACT_PRODUCT, ex);
            throw new DBException(ERR_CANNOT_EXTRACT_PRODUCT, ex);
        }
        return product;
    }

    private Category mapProductCategory(ResultSet rs) throws SQLException {
        Category category = new Category();
        category.setId(rs.getLong(CATEGORY_ID_FIELD));
        category.setName(rs.getString(CATEGORY_NAME_FIELD));
        return category;
    }

    private Manufacturer mapProductManufacturer(ResultSet rs) throws SQLException {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(rs.getLong(MANUFACTURER_ID_FIELD));
        manufacturer.setName(rs.getString(MANUFACTURER_NAME_FIELD));
        return manufacturer;
    }

    private Manufacturer mapManufacturer(ResultSet rs) throws DBException {
        Manufacturer manufacturer = new Manufacturer();
        try {
            manufacturer.setId(rs.getLong(ENTITY_ID));
            manufacturer.setName(rs.getString(MANUFACTURER_NAME));
        } catch (SQLException ex) {
            LOG.error(ERR_CANNOT_EXTRACT_MANUFACTURER, ex);
            throw new DBException(ERR_CANNOT_EXTRACT_MANUFACTURER, ex);
        }
        return manufacturer;
    }


    private Category mapCategory(ResultSet rs) throws DBException {
        Category category = new Category();
        try {
            category.setId(rs.getLong(ENTITY_ID));
            category.setName(rs.getString(CATEGORY_NAME));
        } catch (SQLException ex) {
            LOG.error(ERR_CANNOT_EXTRACT_CATEGORY, ex);
            throw new DBException(ERR_CANNOT_EXTRACT_CATEGORY, ex);
        }
        return category;
    }

}
