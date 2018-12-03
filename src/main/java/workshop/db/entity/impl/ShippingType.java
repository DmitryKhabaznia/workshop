package workshop.db.entity.impl;

import workshop.db.entity.Entity;

import java.util.Objects;

public class ShippingType extends Entity {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ShippingType that = (ShippingType) o;
        return Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }

    @Override
    public String toString() {
        return "ShippingType{" +
                "name='" + name + '\'' +
                '}';
    }

}
