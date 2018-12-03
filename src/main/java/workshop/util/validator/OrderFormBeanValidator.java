package workshop.util.validator;

import workshop.bean.OrderFormBean;
import org.apache.commons.lang3.math.NumberUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Validates {@link OrderFormBean}.
 */
public class OrderFormBeanValidator {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final Pattern CARD_DATE_PATTERN = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$");
    private static final Pattern CARD_NUMBER_PATTERN = Pattern.compile("^\\d{16}$");
    private static final Pattern CARD_CVV_PATTERN = Pattern.compile("^\\d{3}$");
    private static final String CARD_PAYMENT_TYPE_ID = "card";
    private static final String DELIVERY_SHIPPING_TYPE_ID = "delivery";

    /**
     * Checks if specified {@link OrderFormBean} has valid values.
     *
     * @param orderFormBean specified {@link OrderFormBean} to be checked
     * @return <tt>true</tt> if {@link OrderFormBean} is valid, <tt>false</tt> otherwise
     */
    public boolean isValid(OrderFormBean orderFormBean) {
        return validatePayment(orderFormBean) && validateShipping(orderFormBean);
    }

    private boolean validatePayment(OrderFormBean orderFormBean) {
        final String paymentType = orderFormBean.getPaymentType();
        return Objects.nonNull(paymentType) &&
                (!paymentType.equalsIgnoreCase(CARD_PAYMENT_TYPE_ID)
                        || validateCardNumber(orderFormBean.getPaymentCardNumber())
                        && validateCardCVV(orderFormBean.getPaymentCardCVV())
                        && validateCardDate(orderFormBean.getPaymentCardDate()));
    }

    private boolean validateShipping(OrderFormBean orderFormBean) {
        final String shippingType = orderFormBean.getShippingType();
        return Objects.nonNull(shippingType) &&
                (!shippingType.equalsIgnoreCase(DELIVERY_SHIPPING_TYPE_ID)
                        || validateAddress(orderFormBean.getDeliveryAddress()));
    }

    private boolean validateAddress(String value) {
        return Objects.nonNull(value) && !value.isEmpty();
    }

    private boolean validateCardDate(String value) {
        return CARD_DATE_PATTERN.matcher(value).matches()
                && LocalDate.parse(value, DATE_FORMATTER).compareTo(LocalDate.now()) > 0;
    }

    private boolean validateCardNumber(String value) {
        return NumberUtils.isDigits(value) && CARD_NUMBER_PATTERN.matcher(value).matches();
    }

    private boolean validateCardCVV(String value) {
        return NumberUtils.isDigits(value) && CARD_CVV_PATTERN.matcher(value).matches();
    }

}
