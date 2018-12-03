package workshop.util.converter.impl;

import workshop.bean.OrderFormBean;
import workshop.db.entity.impl.Order;
import workshop.util.converter.Converter;
import org.apache.log4j.Logger;

import java.time.LocalDateTime;

/**
 * {@inheritDoc}
 */
public class OrderConverter implements Converter<OrderFormBean, Order> {

    private static final Logger LOG = Logger.getLogger(OrderConverter.class);
    private static final String REGISTERED_STATUS = "registered";
    private static final String DEFAULT_STATUS_DESCRIPTION = "Order was registered from site.";

    /**
     * {@inheritDoc}
     */
    @Override
    public Order convert(OrderFormBean formBean) {
        Order order = new Order();
        order.setStatus(REGISTERED_STATUS);
        order.setStatusDescription(DEFAULT_STATUS_DESCRIPTION);
        order.setOrderDate(LocalDateTime.now());
        order.setPaymentType(formBean.getPaymentType());
        order.setShippingType(formBean.getShippingType());
        order.setDeliveryAddress(formBean.getDeliveryAddress());
        LOG.debug("OrderFormBean was converted to Order: -> " + order);
        return order;
    }
}
