package workshop.db.dto;

import workshop.db.entity.impl.Product;

import java.util.List;

public class ProductsDTO {

    private int lastPageNumber;
    private List<Product> productList;

    public int getLastPageNumber() {
        return lastPageNumber;
    }

    public void setLastPageNumber(int lastPageNumber) {
        this.lastPageNumber = lastPageNumber;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

}
