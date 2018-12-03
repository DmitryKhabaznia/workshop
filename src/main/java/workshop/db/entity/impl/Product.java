package workshop.db.entity.impl;

import workshop.db.entity.Entity;

import java.math.BigDecimal;
import java.util.Objects;

public class Product extends Entity {

    private String name;
    private BigDecimal price;
    private Manufacturer manufacturer;
    private Category category;
    private String description;
    private String imageName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Product product = (Product) o;
        return Objects.equals(name, product.name) &&
                Objects.equals(price, product.price) &&
                Objects.equals(manufacturer, product.manufacturer) &&
                Objects.equals(category, product.category) &&
                Objects.equals(description, product.description) &&
                Objects.equals(imageName, product.imageName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(),
                getPrice(),
                getManufacturer(),
                getCategory(),
                getDescription(),
                getImageName());
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + getId() +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", manufacturer=" + manufacturer +
                ", category=" + category +
                ", description='" + description + '\'' +
                ", imageName='" + imageName + '\'' +
                '}';
    }

}
