package workshop.bean;

import workshop.db.entity.impl.Product;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.*;

public class Cart {

    private Map<Product, Integer> productMap;

    public Cart() {
        productMap = new HashMap<>();
    }

    public void addProduct(Product product, int count) {
        productMap.merge(product, count, Integer::sum);
    }

    public void changeCount(long productId, int count) {
        productMap.put(getProduct(productId), count);
    }

    public boolean removeProduct(long productId) {
        return productMap.remove(getProduct(productId)) != null;
    }

    public void removeAll() {
        productMap = new HashMap<>();
    }

    public Map<Product, Integer> getAllProducts() {
        return new HashMap<>(productMap);
    }

    public int getCount() {
        return productMap
                .values()
                .stream()
                .mapToInt(i -> i)
                .sum();
    }

    public double getTotalSum() {
        return productMap
                .entrySet()
                .stream()
                .map(e -> e
                        .getKey()
                        .getPrice()
                        .multiply(new BigDecimal(e.getValue())))
                .reduce(getIdentity(), BigDecimal::add)
                .doubleValue();
    }

    public double getSubtotal(long id) {
        Product product = getProduct(id);
        return BigDecimal
                .valueOf(productMap.get(product))
                .multiply(product.getPrice())
                .doubleValue();
    }

    private Product getProduct(long productId) {
        return productMap
                .keySet()
                .stream()
                .filter(e -> e.getId() == productId)
                .findFirst()
                .get();
    }

    private BigDecimal getIdentity() {
        return new BigDecimal(0, new MathContext(Integer.MAX_VALUE, RoundingMode.CEILING));
    }

}
