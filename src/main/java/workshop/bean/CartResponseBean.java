package workshop.bean;

import java.util.Objects;

public class CartResponseBean {

    private int count;
    private double total;
    private double subtotal;

    public CartResponseBean(int count, double total, double subtotal) {
        this.count = count;
        this.total = total;
        this.subtotal = subtotal;
    }

    public CartResponseBean(int count, double total) {
        this.count = count;
        this.total = total;
        this.subtotal = 0;
    }

    public int getCount() {
        return count;
    }

    public double getTotal() {
        return total;
    }

    public double getSubtotal() {
        return subtotal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CartResponseBean that = (CartResponseBean) o;
        return count == that.count &&
                Double.compare(that.total, total) == 0 &&
                Double.compare(that.subtotal, subtotal) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(count, total, subtotal);
    }

}
