package workshop.db.entity.impl;

import workshop.db.entity.Entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class Order extends Entity {

    private String status;
    private String statusDescription;
    private LocalDateTime orderDate;
    private String shippingType;
    private String deliveryAddress;
    private String paymentType;
    private User user;
    private List<OrderProduct> orderProducts;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public String getShippingType() {
        return shippingType;
    }

    public void setShippingType(String shippingType) {
        this.shippingType = shippingType;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<OrderProduct> getOrderProducts() {
        return orderProducts;
    }

    public void setOrderProducts(List<OrderProduct> orderProducts) {
        this.orderProducts = orderProducts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Order order = (Order) o;
        return Objects.equals(getStatus(), order.getStatus()) &&
                Objects.equals(getStatusDescription(), order.getStatusDescription()) &&
                Objects.equals(getOrderDate(), order.getOrderDate()) &&
                Objects.equals(getShippingType(), order.getShippingType()) &&
                Objects.equals(getDeliveryAddress(), order.getDeliveryAddress()) &&
                Objects.equals(getPaymentType(), order.getPaymentType()) &&
                Objects.equals(getUser(), order.getUser()) &&
                Objects.equals(getOrderProducts(), order.getOrderProducts());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStatus(),
                getStatusDescription(),
                getOrderDate(),
                getShippingType(),
                getDeliveryAddress(),
                getPaymentType(),
                getUser(),
                getOrderProducts());
    }

    @Override
    public String toString() {
        return "Order{" +
                "status='" + status + '\'' +
                ", statusDescription='" + statusDescription + '\'' +
                ", orderDate=" + orderDate +
                ", shippingType='" + shippingType + '\'' +
                ", deliveryAddress='" + deliveryAddress + '\'' +
                ", paymentType='" + paymentType + '\'' +
                ", user=" + user +
                ", orderProducts=" + orderProducts +
                '}';
    }

}
