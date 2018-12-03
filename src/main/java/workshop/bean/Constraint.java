package workshop.bean;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;
import java.util.Objects;

public class Constraint {

    @JacksonXmlProperty(localName = "url-pattern")
    private String urlPattern;

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "role")
    private List<String> role;

    public String getUrlPattern() {
        return urlPattern;
    }

    public List<String> getRole() {
        return role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Constraint that = (Constraint) o;
        return Objects.equals(getUrlPattern(), that.getUrlPattern()) &&
                Objects.equals(getRole(), that.getRole());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUrlPattern(), getRole());
    }

    @Override
    public String toString() {
        return "Constraint{" +
                "urlPattern='" + urlPattern + '\'' +
                ", role=" + role +
                '}';
    }

}
