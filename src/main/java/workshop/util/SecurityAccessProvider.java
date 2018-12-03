package workshop.util;

import workshop.bean.Constraint;

import java.util.List;

public class SecurityAccessProvider {

    private List<Constraint> constraints;

    public SecurityAccessProvider(List<Constraint> constraints) {
        this.constraints = constraints;
    }

    public boolean isURLInConstraint(String url) {
        return constraints.stream()
                .map(Constraint::getUrlPattern)
                .anyMatch(url::matches);
    }

    public boolean isUserInRole(String url, String role) {
        return constraints.stream()
                .filter(constraint -> url.matches(constraint.getUrlPattern()))
                .findFirst()
                .get().getRole()
                .stream()
                .anyMatch(role::equals);
    }

}
