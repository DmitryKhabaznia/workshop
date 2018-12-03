package workshop.db.dao.impl;

import workshop.db.ConnectionHolder;
import workshop.db.dao.OrderDAO;
import workshop.db.entity.impl.Order;
import workshop.db.entity.impl.OrderProduct;
import workshop.db.entity.impl.PaymentType;
import workshop.db.entity.impl.ShippingType;
import workshop.exception.DBException;
import org.apache.log4j.Logger;
import workshop.util.ResourcesCloserUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static workshop.util.ResourcesCloserUtil.close;

/**
 * Implementation of {@link OrderDAO}.
 */
public class OrderDAOImpl implements OrderDAO {

    private static final Logger LOG = Logger.getLogger(OrderDAOImpl.class);
    private static final String SQL_ADD_ORDER = "INSERT INTO orders VALUES (DEFAULT, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_GET_PAYMENT_TYPE_ID_BY_NAME = "SELECT id FROM payment_types WHERE name = ?";
    private static final String SQL_GET_SHIPPING_TYPE_ID_BY_NAME = "SELECT id FROM shipping_types WHERE name = ?";
    private static final String SQL_GET_STATUS_ID_BY_NAME = "SELECT id FROM statuses WHERE name = ?";
    private static final String SQL_ADD_ORDER_ITEM = "INSERT INTO order_products VALUES (DEFAULT, ?, ?, ?, ?)";
    private static final String ERR_CANNOT_ADD_NEW_ORDER = "Cannot add new order.";
    private static final String SQL_FIND_ALL_SHIPPING_TYPES = "SELECT * FROM shipping_types;";
    private static final String SQL_FIND_ALL_PAYMENT_TYPES = "SELECT * FROM payment_types;";
    private static final String ERR_CANNOT_OBTAIN_TYPES = "Cannot obtain all types.";
    private static final String ERR_CANNOT_EXTRACT_TYPE = "Cannot extract type.";
    private static final String ERR_CANNOT_GET_ID = "Cannot get id.";
    private static final String ENTITY_ID = "id";
    private static final String ENTITY_NAME = "name";
    private static final String BY_NAME = " by name ";
    private ConnectionHolder connectionHolder;

    public OrderDAOImpl(ConnectionHolder connectionHolder) {
        this.connectionHolder = connectionHolder;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Order addOrder(Order order) throws DBException {
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        try {
            Connection connection = connectionHolder.getConnection();
            pstmt = connection.prepareStatement(SQL_ADD_ORDER, Statement.RETURN_GENERATED_KEYS);
            int k = 1;
            pstmt.setString(k++, Timestamp.valueOf(order.getOrderDate()).toString());
            pstmt.setLong(k++, getStatusId(order.getStatus()));
            pstmt.setString(k++, order.getStatusDescription());
            pstmt.setLong(k++, order.getUser().getId());
            pstmt.setLong(k++, getShippingTypeId(order.getShippingType()));
            pstmt.setString(k++, order.getDeliveryAddress());
            pstmt.setLong(k++, getPaymentTypeId(order.getPaymentType()));
            if (pstmt.executeUpdate() > 0) {
                rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    order.setId(rs.getLong(1));
                }
            }
        } catch (SQLException ex) {
            LOG.debug(ERR_CANNOT_ADD_NEW_ORDER + order, ex);
            throw new DBException(ERR_CANNOT_ADD_NEW_ORDER, ex);
        } finally {
            ResourcesCloserUtil.close(pstmt, rs);
        }
        return order;
    }

    private long getPaymentTypeId(String productType) throws DBException {
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        try {
            Connection connection = connectionHolder.getConnection();
            pstmt = connection.prepareStatement(SQL_GET_PAYMENT_TYPE_ID_BY_NAME);
            pstmt.setString(1, productType);
            rs = pstmt.executeQuery();
                if (rs.next()) {
                    return rs.getLong(1);
                }
        } catch (SQLException ex) {
            LOG.debug(ERR_CANNOT_GET_ID + BY_NAME + productType, ex);
            throw new DBException(ERR_CANNOT_GET_ID, ex);
        } finally {
            ResourcesCloserUtil.close(pstmt, rs);
        }
        return -1;
    }

    private long getShippingTypeId(String shippingType) throws DBException {
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        try {
            Connection connection = connectionHolder.getConnection();
            pstmt = connection.prepareStatement(SQL_GET_SHIPPING_TYPE_ID_BY_NAME);
            pstmt.setString(1, shippingType);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getLong(1);
            }
        } catch (SQLException ex) {
            LOG.debug(ERR_CANNOT_GET_ID + BY_NAME + shippingType, ex);
            throw new DBException(ERR_CANNOT_GET_ID, ex);
        } finally {
            ResourcesCloserUtil.close(pstmt, rs);
        }
        return -1;
    }

    private long getStatusId(String status) throws DBException {
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        try {
            Connection connection = connectionHolder.getConnection();
            pstmt = connection.prepareStatement(SQL_GET_STATUS_ID_BY_NAME);
            pstmt.setString(1, status);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getLong(1);
            }
        } catch (SQLException ex) {
            LOG.debug(ERR_CANNOT_GET_ID + BY_NAME + status, ex);
            throw new DBException(ERR_CANNOT_GET_ID, ex);
        } finally {
            ResourcesCloserUtil.close(pstmt, rs);
        }
        return -1;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addOrderItems(Order order) throws DBException {
        for (OrderProduct orderProduct : order.getOrderProducts()) {
            addItem(orderProduct, order.getId());
        }
    }

    private void addItem(OrderProduct orderProduct, Long orderId) throws DBException {
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        try {
            Connection connection = connectionHolder.getConnection();
            pstmt = connection.prepareStatement(SQL_ADD_ORDER_ITEM, Statement.RETURN_GENERATED_KEYS);
            int k = 1;
            pstmt.setString(k++, String.valueOf(orderId));
            pstmt.setString(k++, String.valueOf(orderProduct.getProduct().getId()));
            pstmt.setString(k++, String.valueOf(orderProduct.getCount()));
            pstmt.setString(k++, orderProduct.getCurrentPrice().toString());
            if (pstmt.executeUpdate() > 0) {
                rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    orderProduct.setId(rs.getLong(1));
                }
            }
        } catch (SQLException ex) {
            LOG.debug(ERR_CANNOT_ADD_NEW_ORDER + orderProduct, ex);
            throw new DBException(ERR_CANNOT_ADD_NEW_ORDER, ex);
        } finally {
            ResourcesCloserUtil.close(pstmt, rs);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ShippingType> getAllShippingTypes() throws DBException {
        List<ShippingType> shippingTypes = new ArrayList<>();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            Connection connection = connectionHolder.getConnection();
            stmt = connection.createStatement();
            rs = stmt.executeQuery(SQL_FIND_ALL_SHIPPING_TYPES);
            while (rs.next()) {
                shippingTypes.add(mapShippingType(rs));
            }
            return shippingTypes;
        } catch (SQLException ex) {
            LOG.error(ERR_CANNOT_OBTAIN_TYPES, ex);
            throw new DBException(ERR_CANNOT_OBTAIN_TYPES, ex);
        } finally {
            ResourcesCloserUtil.close(stmt, rs);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PaymentType> getAllPaymentTypes() throws DBException {
        List<PaymentType> paymentTypes = new ArrayList<>();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            Connection connection = connectionHolder.getConnection();
            stmt = connection.createStatement();
            rs = stmt.executeQuery(SQL_FIND_ALL_PAYMENT_TYPES);
            while (rs.next()) {
                paymentTypes.add(mapPaymentType(rs));
            }
            return paymentTypes;
        } catch (SQLException ex) {
            LOG.error(ERR_CANNOT_OBTAIN_TYPES, ex);
            throw new DBException(ERR_CANNOT_OBTAIN_TYPES, ex);
        } finally {
            ResourcesCloserUtil.close(stmt, rs);
        }
    }

    private ShippingType mapShippingType(ResultSet rs) throws DBException {
        ShippingType shippingType = new ShippingType();
        try {
            shippingType.setId(rs.getLong(ENTITY_ID));
            shippingType.setName(rs.getString(ENTITY_NAME));
        } catch (SQLException ex) {
            LOG.error(ERR_CANNOT_EXTRACT_TYPE, ex);
            throw new DBException(ERR_CANNOT_EXTRACT_TYPE, ex);
        }
        return shippingType;
    }

    private PaymentType mapPaymentType(ResultSet rs) throws DBException {
        PaymentType paymentType = new PaymentType();
        try {
            paymentType.setId(rs.getLong(ENTITY_ID));
            paymentType.setName(rs.getString(ENTITY_NAME));
        } catch (SQLException ex) {
            LOG.error(ERR_CANNOT_EXTRACT_TYPE, ex);
            throw new DBException(ERR_CANNOT_EXTRACT_TYPE, ex);
        }
        return paymentType;
    }

}
