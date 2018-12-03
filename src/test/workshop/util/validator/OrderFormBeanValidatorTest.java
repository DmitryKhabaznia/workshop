package workshop.util.validator;

import workshop.bean.OrderFormBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class OrderFormBeanValidatorTest {

    private static final String CARD_PAYMENT_TYPE_ID = "card";
    private static final String DELIVERY_SHIPPING_TYPE_ID = "delivery";
    private static final String VALID_TYPE = "type";
    private static final String VALID_DELIVERY_ADRESS = "adress";
    private static final String INVALID_DELIVERY_ADRESS = null;
    private static final String VALID_CARD_DATE = "2234-12-02";
    private static final String INVALID_CARD_DATE = "noValid";
    private static final String VALID_CARD_NUMBER = "1234123412341234";
    private static final String INVALID_CARD_NUMBER = "invalidNumber";
    private static final String VALID_CARD_CVV = "234";
    private static final String INVALID_CARD_CVV = "invalidCVV";

    private OrderFormBeanValidator orderFormBeanValidator;
    private OrderFormBean orderFormBean;
    private boolean result;

    public OrderFormBeanValidatorTest(String shippingType,
                                      String paymentType,
                                      String deliveryAddress,
                                      String cardDate,
                                      String cardNumber,
                                      String cardCvv,
                                      boolean result) {
        orderFormBean = new OrderFormBean();
        orderFormBean.setPaymentType(paymentType);
        orderFormBean.setShippingType(shippingType);
        orderFormBean.setDeliveryAddress(deliveryAddress);
        orderFormBean.setPaymentCardDate(cardDate);
        orderFormBean.setPaymentCardNumber(cardNumber);
        orderFormBean.setPaymentCardCVV(cardCvv);
        this.result = result;
        orderFormBeanValidator = new OrderFormBeanValidator();
    }

    @Parameters
    public static Collection<Object[]> parameters() {
        return Arrays.asList(new Object[][]{
                {VALID_TYPE, VALID_TYPE, VALID_DELIVERY_ADRESS, VALID_CARD_DATE, VALID_CARD_NUMBER, VALID_CARD_CVV, true},
                {VALID_TYPE, VALID_TYPE, null, null, null, null, true},
                {DELIVERY_SHIPPING_TYPE_ID, VALID_TYPE, VALID_DELIVERY_ADRESS, null, null, null, true},
                {DELIVERY_SHIPPING_TYPE_ID, VALID_TYPE, INVALID_DELIVERY_ADRESS, null, null, null, false},
                {VALID_TYPE, CARD_PAYMENT_TYPE_ID, null, VALID_CARD_DATE, VALID_CARD_NUMBER, VALID_CARD_CVV, true},
                {VALID_TYPE, CARD_PAYMENT_TYPE_ID, null, INVALID_CARD_DATE, VALID_CARD_NUMBER, VALID_CARD_CVV, false},
                {VALID_TYPE, CARD_PAYMENT_TYPE_ID, null, VALID_CARD_DATE, INVALID_CARD_NUMBER, VALID_CARD_CVV, false},
                {VALID_TYPE, CARD_PAYMENT_TYPE_ID, null, VALID_CARD_DATE, VALID_CARD_NUMBER, INVALID_CARD_CVV, false}
        });
    }

    @Test
    public void isValid() {
        assertEquals(result, orderFormBeanValidator.isValid(orderFormBean));
    }
}