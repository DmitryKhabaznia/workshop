package workshop.util.converter.impl;

import workshop.bean.OrderFormBean;
import workshop.db.entity.impl.Order;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OrderConverterTest {

    private static final String REGISTERED_STATUS = "registered";
    private static final String DEFAULT_STATUS_DESCRIPTION = "Order was registered from site.";
    private static final String PAYMENT_TYPE = "card";
    private static final String SHIPPING_TYPE = "pick up";
    private static final String DELIVERY_ADDRESS = "some street";
    private static final LocalDateTime localDateTime = LocalDateTime.now();

    @Mock
    private OrderFormBean orderFormBean;
    @InjectMocks
    private OrderConverter orderConverter;

    private Order order;

    @Before
    public void setUp() {
        order = new Order();
        order.setDeliveryAddress(DELIVERY_ADDRESS);
        order.setOrderDate(localDateTime);
        order.setPaymentType(PAYMENT_TYPE);
        order.setShippingType(SHIPPING_TYPE);
        order.setStatus(REGISTERED_STATUS);
        order.setStatusDescription(DEFAULT_STATUS_DESCRIPTION);
    }

    @Test
    public void shouldReturnObjectThatEqualOfSettedUpOrder() {
        when(orderFormBean.getDeliveryAddress()).thenReturn(DELIVERY_ADDRESS);
        when(orderFormBean.getPaymentType()).thenReturn(PAYMENT_TYPE);
        when(orderFormBean.getShippingType()).thenReturn(SHIPPING_TYPE);
        Order convertedOrder = orderConverter.convert(orderFormBean);
        assertEquals(convertedOrder.getDeliveryAddress(), order.getDeliveryAddress());
        assertEquals(convertedOrder.getPaymentType(), order.getPaymentType());
        assertEquals(convertedOrder.getShippingType(), order.getShippingType());
        assertEquals(convertedOrder.getOrderProducts(), order.getOrderProducts());
        assertEquals(convertedOrder.getStatus(), order.getStatus());
        assertEquals(convertedOrder.getStatusDescription(), order.getStatusDescription());
        assertEquals(convertedOrder.getUser(), order.getUser());
        assert (convertedOrder.getOrderDate() != null);
    }

}