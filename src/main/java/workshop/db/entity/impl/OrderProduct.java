package workshop.db.entity.impl;

import workshop.db.entity.Entity;

import java.math.BigDecimal;
import java.util.Objects;

public class OrderProduct extends Entity {

    private Product product;
    private int count;
    private BigDecimal currentPrice;

    public OrderProduct(Product product, int count) {
        this.product = product;
        this.count = count;
        currentPrice = product.getPrice();
    }


    public Product getProduct() {
        return product;
    }

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public int getCount() {
        return count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OrderProduct orderProduct = (OrderProduct) o;
        return count == orderProduct.count &&
                Objects.equals(product, orderProduct.product) &&
                Objects.equals(currentPrice, orderProduct.currentPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, count, currentPrice);
    }

    @Override
    public String toString() {
        return "OrderProduct{" +
                ", product=" + product +
                ", currentPrice=" + currentPrice +
                ", count=" + count +
                '}';
    }

}
