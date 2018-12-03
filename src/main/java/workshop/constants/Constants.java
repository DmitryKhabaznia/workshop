package workshop.constants;

public final class Constants {

    public final class AppContextConstants {

        public static final String USER_SERVICE = "USER_SERVICE";
        public static final String PRODUCT_SERVICE = "PRODUCT_SERVICE";
        public static final String AVATAR_SERVICE = "AVATAR_SERVICE";
        public static final String CAPTCHA_SERVICE = "CAPTCHA_SERVICE";
        public static final String ORDER_SERVICE = "ORDER_SERVICE";
        public static final String REGISTER_FORM_BEAN_VALIDATOR = "REGISTER_FORM_BEAN_VALIDATOR";
        public static final String USER_CONVERTER = "USER_CONVERTER";
        public static final String SHOPPING_CART = "SHOPPING_CART";
        public static final String USER_ATTRIBUTE = "user";
        public static final String ERROR_MESSAGE_DIV = "error_message";
        public static final String SUCCESS_MESSAGE_DIV = "success_message";

        public static final String SIGN_UP_PAGE = "WEB-INF/jsp/sign-up.jsp";
        public static final String PRODUCTS_PAGE = "WEB-INF/jsp/products.jsp";
        public static final String SHOPPING_CART_PAGE = "WEB-INF/jsp/shopping_cart.jsp";
        public static final String ORDER_PAGE = "WEB-INF/jsp/order.jsp";
        public static final String ORDER_CONFIRM_PAGE = "WEB-INF/jsp/order_confirm.jsp";
        public static final String INDEX_PAGE = "index.jsp";
        public static final String REGISTRATION_SERVLET = "registration";
        public static final String ORDER_SERVLET = "order";
        public static final String ORDER_CONFIRM_SERVLET = "orderConfirm";
        public static final String ERROR_PAGE = "error_page.jsp";
        public static final String PREVIOUS_PAGE = "previous_page";

    }

    public final class FilterFormBeanFields {

        public static final String MANUFACTURER_FIELD = "manufacturer";
        public static final String CATEGORY_FIELD = "category";
        public static final String MIN_PRICE_FIELD = "minPrice";
        public static final String MAX_PRICE_FIELD = "maxPrice";
        public static final String NAME_FIELD = "name";
        public static final String SORTING_TYPE_FIELD = "sortingType";
        public static final String PAGE_NUMBER_FIELD = "pageNumber";
        public static final String PRODUCT_COUNT_ON_PAGE_FIELD = "numberOfProducts";
        public static final String LAST_PAGE_NUMBER = "lastPage";
        public static final String DEFAULT_SORTING = "name_asc";

    }

    public final class Messages {
        public static final String EMPTY_FIELD_MESSAGE = "Field can't be empty.";
        public static final String FIRST_NAME_INVALID_MESSAGE = "First name is invalid.";
        public static final String SECOND_NAME_INVALID_MESSAGE = "Second name is invalid.";
        public static final String LOGIN_INVALID_MESSAGE = "Login is invalid.";
        public static final String EMAIL_INVALID_MESSAGE = "Email is invalid.";
        public static final String PASS_INVALID_MESSAGE = "Password is invalid.";
        public static final String PASS_NOT_EQUALS_MESSAGE = "Passwords should be equal.";
        public static final String CAPTCHA_VALUE_INVALID_MESSAGE = "Value is not equal.";
        public static final String USER_EXISTS_ERROR_MESSAGE = "User with this login is already exists.";
        public static final String TIME_OUT_ERROR_MESSAGE = "Time of registration out.";
        public static final String SESSION_IS_NULL_MESSAGE = "Session is null.";
        public static final String NO_COOKIES_MESSAGE = "Cookies are empty";

        public static final String CANNOT_CLOSE_CONNECTION = "Can't close connection";
        public static final String CANNOT_ROLLBACK_CONNECTION = "Can't rollback connection";
        public static final String CANNOT_CLOSE_STATEMENT = "Can't close statement";
        public static final String CANNOT_CLOSE_RESULT_SET = "Can't close result set";

    }

}
