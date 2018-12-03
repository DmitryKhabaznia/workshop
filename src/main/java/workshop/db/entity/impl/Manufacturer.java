package workshop.db.entity.impl;

import workshop.db.entity.Entity;

import java.util.Objects;

public class Manufacturer extends Entity {

    private String name;

    public Manufacturer() {
    }

    public Manufacturer(String name) {

        this.name = name;
    }

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
        Manufacturer that = (Manufacturer) o;
        return Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }

    @Override
    public String toString() {
        return "Manufacturer{" +
                "id=" + getId() +
                " ,name='" + name + '\'' +
                '}';
    }

}
