package workshop.bean;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;
import java.util.Objects;

public class SecurityBean {

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "constraint")
    private List<Constraint> constraints;

    public List<Constraint> getConstraints() {
        return constraints;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SecurityBean that = (SecurityBean) o;
        return Objects.equals(getConstraints(), that.getConstraints());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getConstraints());
    }

    @Override
    public String toString() {
        return "SecurityBean{" +
                "constraints=" + constraints +
                '}';
    }

}
