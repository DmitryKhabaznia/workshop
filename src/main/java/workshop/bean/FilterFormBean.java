package workshop.bean;

import java.util.Arrays;
import java.util.Objects;


public class FilterFormBean {

    private int manufacturer;
    private int[] categories;
    private int minPrice;
    private int maxPrice;
    private String nameInput;
    private String sortingType;
    private int pageNumber;
    private int productCountOnPage;

    public int getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(int manufacturer) {
        this.manufacturer = manufacturer;
    }

    public int[] getCategories() {
        return categories;
    }

    public void setCategories(int[] categories) {
        this.categories = categories;
    }

    public int getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(int minPrice) {
        this.minPrice = minPrice;
    }

    public int getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(int maxPrice) {
        this.maxPrice = maxPrice;
    }

    public String getNameInput() {
        return nameInput;
    }

    public void setNameInput(String nameInput) {
        this.nameInput = nameInput;
    }

    public String getSortingType() {
        return sortingType;
    }

    public void setSortingType(String sortingType) {
        this.sortingType = sortingType;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getProductCountOnPage() {
        return productCountOnPage;
    }

    public void setProductCountOnPage(int productCountOnPage) {
        this.productCountOnPage = productCountOnPage;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FilterFormBean that = (FilterFormBean) o;
        return getManufacturer() == that.getManufacturer() &&
                getMinPrice() == that.getMinPrice() &&
                getMaxPrice() == that.getMaxPrice() &&
                getPageNumber() == that.getPageNumber() &&
                getProductCountOnPage() == that.getProductCountOnPage() &&
                Arrays.equals(getCategories(), that.getCategories()) &&
                Objects.equals(getNameInput(), that.getNameInput()) &&
                Objects.equals(getSortingType(), that.getSortingType());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int result = Objects.hash(getManufacturer(),
                getMinPrice(),
                getMaxPrice(),
                getNameInput(),
                getSortingType(),
                getPageNumber(),
                getProductCountOnPage());
        result = 31 * result + Arrays.hashCode(getCategories());
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "FilterFormBean{" +
                "manufacturer=" + manufacturer +
                ", categories=" + Arrays.toString(categories) +
                ", minPrice=" + minPrice +
                ", maxPrice=" + maxPrice +
                ", nameInput='" + nameInput + '\'' +
                ", sortingType=" + sortingType +
                ", pageNumber=" + pageNumber +
                ", productCountOnPage=" + productCountOnPage +
                '}';
    }
}

