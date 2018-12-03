package workshop.bean;

import java.util.Objects;

public class OrderFormBean {

    private String paymentType;
    private String shippingType;
    private String deliveryAddress;
    private String paymentCardNumber;
    private String paymentCardDate;
    private String paymentCardCVV;

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
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

    public String getPaymentCardNumber() {
        return paymentCardNumber;
    }

    public void setPaymentCardNumber(String paymentCardNumber) {
        this.paymentCardNumber = paymentCardNumber;
    }

    public String getPaymentCardDate() {
        return paymentCardDate;
    }

    public void setPaymentCardDate(String paymentCardDate) {
        this.paymentCardDate = paymentCardDate;
    }

    public String getPaymentCardCVV() {
        return paymentCardCVV;
    }

    public void setPaymentCardCVV(String paymentCardCVV) {
        this.paymentCardCVV = paymentCardCVV;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OrderFormBean that = (OrderFormBean) o;
        return Objects.equals(getPaymentType(), that.getPaymentType()) &&
                Objects.equals(getShippingType(), that.getShippingType()) &&
                Objects.equals(getDeliveryAddress(), that.getDeliveryAddress()) &&
                Objects.equals(getPaymentCardNumber(), that.getPaymentCardNumber()) &&
                Objects.equals(getPaymentCardDate(), that.getPaymentCardDate()) &&
                Objects.equals(getPaymentCardCVV(), that.getPaymentCardCVV());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPaymentType(),
                getShippingType(),
                getDeliveryAddress(),
                getPaymentCardNumber(),
                getPaymentCardDate(),
                getPaymentCardCVV());
    }

    @Override
    public String toString() {
        return "OrderFormBean{" +
                "paymentType='" + paymentType + '\'' +
                ", shippingType='" + shippingType + '\'' +
                ", deliveryAddress='" + deliveryAddress + '\'' +
                ", paymentCardNumber='" + paymentCardNumber + '\'' +
                ", paymentCardDate='" + paymentCardDate + '\'' +
                ", paymentCardCVV='" + paymentCardCVV + '\'' +
                '}';
    }

}
