package workshop.util;

import workshop.bean.FilterFormBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;
import static org.junit.runners.Parameterized.*;

@RunWith(Parameterized.class)
public class ProductSQLBuilderTest {

    private static final String SQL_GET_PRODUCTS_QUERY_START = "SELECT a.id AS id, a.name AS name, a.price AS price, " +
            "a.description AS description, a.image AS image, b.id AS category_id, " +
            "b.name AS category_name, c.id AS manufacturer_id, c.name AS manufacturer_name ";
    private static final String SQL_GET_COUNT_QUERY_START = "SELECT count(*) AS count ";
    private static final String SQL_QUERY_WHERE = "FROM products AS a, categories AS b, manufacturers AS c " +
            "WHERE a.category_id = b.id and a.manufacturer_id = c.id";
    private static final String END = ";";
    private static final String NAME_ASC = " ORDER BY a.name ASC";
    private static final String NAME_DESC = " ORDER BY a.name DESC";
    private static final int DEFAULT_VALUE = -1;
    private static final int DEFAULT_PAGE_NUMBER = 1;
    private static final int DEFAULT_COUNT_OF_PRODUCTS = 6;
    private static final int VALUE = 15;
    private static final int ONE = 1;
    private static final String NAME_STRING = "string";

    private ProductSQLBuilder productSQLBuilder;
    private FilterFormBean filterFormBean;
    private String resultListOfProductsQuery;
    private String resultCountOfProductsQuery;

    public ProductSQLBuilderTest(int manufacturer,
                                 int[] categories,
                                 int minPrice,
                                 int maxPrice,
                                 String nameInput,
                                 String sortingType,
                                 int pageNumber,
                                 int productCountOnPage,
                                 String resultCountOfProductsQuery,
                                 String resultListOfProductsQuery) {
        filterFormBean = new FilterFormBean();
        filterFormBean.setManufacturer(manufacturer);
        filterFormBean.setCategories(categories);
        filterFormBean.setMinPrice(minPrice);
        filterFormBean.setMaxPrice(maxPrice);
        filterFormBean.setNameInput(nameInput);
        filterFormBean.setSortingType(sortingType);
        filterFormBean.setPageNumber(pageNumber);
        filterFormBean.setProductCountOnPage(productCountOnPage);
        this.resultCountOfProductsQuery = resultCountOfProductsQuery;
        this.resultListOfProductsQuery = resultListOfProductsQuery;
        productSQLBuilder = new ProductSQLBuilder();
    }

    @Parameters
    public static Collection<Object[]> parameters() {
        return Arrays.asList(new Object[][]{
                {DEFAULT_VALUE, null, DEFAULT_VALUE, DEFAULT_VALUE, null, null, DEFAULT_PAGE_NUMBER, DEFAULT_COUNT_OF_PRODUCTS,
                        SQL_GET_COUNT_QUERY_START + SQL_QUERY_WHERE + END,
                        SQL_GET_PRODUCTS_QUERY_START + SQL_QUERY_WHERE + NAME_ASC + " LIMIT 6 OFFSET 0" + END},
                {VALUE, null, DEFAULT_VALUE, DEFAULT_VALUE, null, null, DEFAULT_PAGE_NUMBER, DEFAULT_COUNT_OF_PRODUCTS,
                        SQL_GET_COUNT_QUERY_START + SQL_QUERY_WHERE + " AND manufacturer_id = \"" + VALUE + "\"" + END,
                        SQL_GET_PRODUCTS_QUERY_START + SQL_QUERY_WHERE + " AND manufacturer_id = \"" + VALUE + "\"" + NAME_ASC + " LIMIT 6 OFFSET 0" + END},
                {DEFAULT_VALUE, new int[]{1}, DEFAULT_VALUE, DEFAULT_VALUE, null, null, DEFAULT_PAGE_NUMBER, DEFAULT_COUNT_OF_PRODUCTS,
                        SQL_GET_COUNT_QUERY_START + SQL_QUERY_WHERE + " AND category_id IN (1)" + END,
                        SQL_GET_PRODUCTS_QUERY_START + SQL_QUERY_WHERE + " AND category_id IN (1)" + NAME_ASC + " LIMIT 6 OFFSET 0" + END},
                {DEFAULT_VALUE, null, VALUE, DEFAULT_VALUE, null, null, DEFAULT_PAGE_NUMBER, DEFAULT_COUNT_OF_PRODUCTS,
                        SQL_GET_COUNT_QUERY_START + SQL_QUERY_WHERE + " AND a.price BETWEEN \"" + VALUE + "\" AND \"" + Integer.MAX_VALUE + "\"" + END,
                        SQL_GET_PRODUCTS_QUERY_START + SQL_QUERY_WHERE + " AND a.price BETWEEN \"" + VALUE + "\" AND \"" + Integer.MAX_VALUE + "\"" + NAME_ASC + " LIMIT 6 OFFSET 0" + END},
                {DEFAULT_VALUE, null, DEFAULT_VALUE, VALUE, null, null, DEFAULT_PAGE_NUMBER, DEFAULT_COUNT_OF_PRODUCTS,
                        SQL_GET_COUNT_QUERY_START + SQL_QUERY_WHERE + " AND a.price BETWEEN \"" + 0 + "\" AND \"" + VALUE + "\"" + END,
                        SQL_GET_PRODUCTS_QUERY_START + SQL_QUERY_WHERE + " AND a.price BETWEEN \"" + 0 + "\" AND \"" + VALUE + "\"" + NAME_ASC + " LIMIT 6 OFFSET 0" + END},
                {DEFAULT_VALUE, null, VALUE, ONE, null, null, DEFAULT_PAGE_NUMBER, DEFAULT_COUNT_OF_PRODUCTS,
                        SQL_GET_COUNT_QUERY_START + SQL_QUERY_WHERE + " AND a.price BETWEEN \"" + VALUE + "\" AND \"" + 0 + "\"" + END,
                        SQL_GET_PRODUCTS_QUERY_START + SQL_QUERY_WHERE + " AND a.price BETWEEN \"" + VALUE + "\" AND \"" + 0 + "\"" + NAME_ASC + " LIMIT 6 OFFSET 0" + END},
                {DEFAULT_VALUE, null, DEFAULT_VALUE, DEFAULT_VALUE, NAME_STRING, null, DEFAULT_PAGE_NUMBER, DEFAULT_COUNT_OF_PRODUCTS,
                        SQL_GET_COUNT_QUERY_START + SQL_QUERY_WHERE + " AND a.name LIKE ?" + END,
                        SQL_GET_PRODUCTS_QUERY_START + SQL_QUERY_WHERE + " AND a.name LIKE ?" + NAME_ASC + " LIMIT 6 OFFSET 0" + END},
                {DEFAULT_VALUE, null, DEFAULT_VALUE, DEFAULT_VALUE, null, "name_desc", DEFAULT_PAGE_NUMBER, DEFAULT_COUNT_OF_PRODUCTS,
                        SQL_GET_COUNT_QUERY_START + SQL_QUERY_WHERE + END,
                        SQL_GET_PRODUCTS_QUERY_START + SQL_QUERY_WHERE + NAME_DESC + " LIMIT 6 OFFSET 0" + END},
                {DEFAULT_VALUE, null, DEFAULT_VALUE, DEFAULT_VALUE, null, null, VALUE, VALUE,
                        SQL_GET_COUNT_QUERY_START + SQL_QUERY_WHERE + END,
                        SQL_GET_PRODUCTS_QUERY_START + SQL_QUERY_WHERE + NAME_ASC + " LIMIT " + VALUE + " OFFSET " + (VALUE - 1) * VALUE + END}
        });
    }

    @Test
    public void getCountSQLQuery() {
        assertEquals(resultCountOfProductsQuery, productSQLBuilder.getCountSQLQuery(filterFormBean));
    }

    @Test
    public void getProductsListSQLQuery() {
        assertEquals(resultListOfProductsQuery, productSQLBuilder.getProductsListSQLQuery(filterFormBean));
    }

}