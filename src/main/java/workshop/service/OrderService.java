package workshop.service;

import workshop.db.TransactionManager;
import workshop.db.dao.OrderDAO;
import workshop.db.entity.impl.Order;
import workshop.db.entity.impl.PaymentType;
import workshop.db.entity.impl.ShippingType;
import workshop.exception.DBException;
import workshop.exception.ServiceException;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Service that intended for working with orders.
 */
public class OrderService {

    private static final Logger LOG = Logger.getLogger(OrderService.class);
    private static final String ERR_CANNOT_ADD_NEW_ORDER = "Cannot add new order.";
    private static final String ERR_CANNOT_GET_ALL_TYPES = "Cannot get all types.";
    private TransactionManager transactionManager;
    private OrderDAO orderDAO;

    public OrderService(TransactionManager transactionManager, OrderDAO orderDAO) {
        this.transactionManager = transactionManager;
        this.orderDAO = orderDAO;
    }

    /**
     * Adds specified {@link Order} using {@link OrderDAO}.
     *
     * @param order specified {@link Order} to be added
     * @return {@link Order} with updated id
     */
    public Order addOrder(Order order) throws ServiceException {
        try {
            return transactionManager.doInTransactionWithResult(() -> {
                Order orderWithId = (orderDAO.addOrder(order));
                orderDAO.addOrderItems(orderWithId);
                return orderWithId;
            });
        } catch (DBException ex) {
            LOG.error("Can not add order: -> " + order);
            throw new ServiceException(ERR_CANNOT_ADD_NEW_ORDER, ex);
        }
    }

    /**
     * Returns {@link List} of all types of shipping.
     *
     * @return {@link List} of all types of shipping
     * @throws ServiceException
     */
    public List<ShippingType> getAllShippingTypes() throws ServiceException {
        List<ShippingType> shippingTypes;
        try {
            shippingTypes = transactionManager.doInTransactionWithResult(() -> orderDAO.getAllShippingTypes());
        } catch (DBException ex) {
            LOG.error(ERR_CANNOT_GET_ALL_TYPES, ex);
            throw new ServiceException(ERR_CANNOT_GET_ALL_TYPES, ex);
        }
        return shippingTypes;
    }

    /**
     * Returns {@link List} of all types of payment.
     *
     * @return {@link List} of all types of payment
     * @throws ServiceException
     */
    public List<PaymentType> getAllPaymentTypes() throws ServiceException {
        List<PaymentType> paymentTypes;
        try {
            paymentTypes = transactionManager.doInTransactionWithResult(() -> orderDAO.getAllPaymentTypes());
        } catch (DBException ex) {
            LOG.error(ERR_CANNOT_GET_ALL_TYPES, ex);
            throw new ServiceException(ERR_CANNOT_GET_ALL_TYPES, ex);
        }
        return paymentTypes;
    }

}
