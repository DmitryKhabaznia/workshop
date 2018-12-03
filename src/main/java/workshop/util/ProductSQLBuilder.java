package workshop.util;

import workshop.bean.FilterFormBean;
import org.apache.log4j.Logger;
import workshop.db.entity.impl.Product;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static workshop.constants.Constants.FilterFormBeanFields.DEFAULT_SORTING;

/**
 * Builder intended for managing with products.
 */
public class ProductSQLBuilder {

    private static final Logger LOG = Logger.getLogger(ProductSQLBuilder.class);
    private static final String SQL_GET_PRODUCTS_QUERY_START = "SELECT a.id AS id, a.name AS name, a.price AS price, " +
            "a.description AS description, a.image AS image, b.id AS category_id, " +
            "b.name AS category_name, c.id AS manufacturer_id, c.name AS manufacturer_name ";
    private static final String SQL_GET_COUNT_QUERY_START = "SELECT count(*) AS count ";
    private static final String SQL_QUERY_WHERE = "FROM products AS a, categories AS b, manufacturers AS c " +
            "WHERE a.category_id = b.id and a.manufacturer_id = c.id";
    private static final String CATEGORIES_FILTER = " AND category_id IN (";
    private static final String MANUFACTURER_FILTER = " AND manufacturer_id = \"";
    private static final String NAME_FILTER = " AND a.name LIKE ?";
    private static final String PRICE_FILTER = " AND a.price BETWEEN \"";
    private static final String LIMIT = " LIMIT ";
    private static final String OFFSET = " OFFSET ";
    private static final String AND = "\" AND \"";
    private static final String COMMA = ",";
    private static final String END_BRACE = ")";
    private static final String END_QUOTE = "\"";
    private static final String END = ";";

    private Map<String, String> sorting;
    private StringBuilder resultQuery;

    public ProductSQLBuilder() {
        sorting = new HashMap<>();
        sorting.put("name_asc", " ORDER BY a.name ASC");
        sorting.put("name_desc", " ORDER BY a.name DESC");
        sorting.put("price_asc", " ORDER BY a.price ASC");
        sorting.put("price_desc", " ORDER BY a.price DESC");
    }

    /**
     * Returns query for getting count of items with specified filters in bean.
     *
     * @param filterFormBean bean that contains filters
     * @return SQL query for getting count
     */

    public String getCountSQLQuery(FilterFormBean filterFormBean) {
        resultQuery = new StringBuilder();
        resultQuery.append(SQL_GET_COUNT_QUERY_START);
        addFilters(filterFormBean);
        resultQuery.append(END);
        LOG.debug("SQL was constructed: -> " + resultQuery.toString());
        return resultQuery.toString();
    }

    /**
     * Returns query for getting {@link Product}s with specified filters and sorting type in bean.
     *
     * @param filterFormBean bean that contains filters
     * @return SQL query for getting products
     */

    public String getProductsListSQLQuery(FilterFormBean filterFormBean) {
        resultQuery = new StringBuilder();
        resultQuery.append(SQL_GET_PRODUCTS_QUERY_START);
        addFilters(filterFormBean);
        addSorting(filterFormBean);
        addPagination(filterFormBean);
        resultQuery.append(END);
        LOG.debug("SQL was constructed: -> " + resultQuery.toString());
        return resultQuery.toString();
    }

    private void addFilters(FilterFormBean filterFormBean) {
        resultQuery.append(SQL_QUERY_WHERE);
        addCategoriesFilter(filterFormBean);
        addManufacturerFilter(filterFormBean);
        addNameFilter(filterFormBean);
        addPriceFilter(filterFormBean);
    }

    private void addCategoriesFilter(FilterFormBean filterFormBean) {
        int[] categories = filterFormBean.getCategories();
        if (categories != null && categories.length != 0) {
            resultQuery.append(CATEGORIES_FILTER);
            Arrays.stream(categories).forEach(category -> resultQuery.append(category).append(COMMA));
            resultQuery.deleteCharAt(resultQuery.length() - 1);
            resultQuery.append(END_BRACE);
        }
    }

    private void addManufacturerFilter(FilterFormBean filterFormBean) {
        int manufacturer = filterFormBean.getManufacturer();
        if (manufacturer > 0) {
            resultQuery.append(MANUFACTURER_FILTER);
            resultQuery.append(manufacturer);
            resultQuery.append(END_QUOTE);
        }
    }

    private void addNameFilter(FilterFormBean filterFormBean) {
        String name = filterFormBean.getNameInput();
        if (name != null && !name.isEmpty()) {
            resultQuery.append(NAME_FILTER);
        }
    }

    private void addPriceFilter(FilterFormBean filterFormBean) {
        int minPrice = filterFormBean.getMinPrice();
        int maxPrice = filterFormBean.getMaxPrice();
        if ((minPrice < 0 && maxPrice < 0)) {
            return;
        }
        if ((maxPrice > 0 && minPrice > maxPrice)) {
            maxPrice = 0;
        }
        resultQuery.append(PRICE_FILTER);
        resultQuery.append(minPrice < 0 ? 0 : minPrice);
        resultQuery.append(AND);
        resultQuery.append(maxPrice < 0 ? Integer.MAX_VALUE : maxPrice);
        resultQuery.append(END_QUOTE);
    }

    private void addPagination(FilterFormBean filterFormBean) {
        resultQuery.append(LIMIT);
        resultQuery.append(filterFormBean.getProductCountOnPage());
        resultQuery.append(OFFSET);
        resultQuery.append((filterFormBean.getPageNumber() - 1) * filterFormBean.getProductCountOnPage());
    }

    private void addSorting(FilterFormBean filterFormBean) {
        String sortingType = sorting.get(filterFormBean.getSortingType());
        if (sortingType == null) {
            sortingType = sorting.get(DEFAULT_SORTING);
        }
        resultQuery.append(sortingType);
    }

}
