package workshop.db.dao;

import workshop.db.entity.impl.Order;
import workshop.db.entity.impl.PaymentType;
import workshop.db.entity.impl.ShippingType;
import workshop.exception.DBException;
import workshop.db.entity.impl.OrderProduct;

import java.util.List;

/**
 * DAO that intended for working with orders and order field types.
 */
public interface OrderDAO {

    /**
     * Adds specified {@link Order} into database and returns this {@link Order} with updated id.
     *
     * @param order specified {@link Order}
     * @return added {@link Order} with updated id
     */
    Order addOrder(Order order) throws DBException;

    /**
     * Adds products of order into database.
     *
     * @param order specified {@link Order} which {@link OrderProduct}s is added
     */
    void addOrderItems(Order order) throws DBException;

    /**
     * Returns {@link List} of all types of shipping.
     *
     * @return {@link List} of all types of shipping
     * @throws DBException
     */
    List<ShippingType> getAllShippingTypes() throws DBException;

    /**
     * Returns {@link List} of all types of payment.
     *
     * @return {@link List} of all types of payment
     * @throws DBException
     */
    List<PaymentType> getAllPaymentTypes() throws DBException;

}
