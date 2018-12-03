package workshop.util.converter.impl;

import workshop.bean.RegistrationFormBean;
import workshop.util.converter.Converter;
import workshop.db.entity.impl.User;
import org.apache.log4j.Logger;

import java.util.Map;
import java.util.TreeMap;

import static workshop.constants.Gender.*;

/**
 * Converts specified {@link RegistrationFormBean} to {@link User}.
 */
public class UserConverter implements Converter<RegistrationFormBean, User> {

    private static final Logger LOG = Logger.getLogger(UserConverter.class);
    private static final String DEFAULT_USER_ROLE = "user";
    private Map<String, Boolean> genderMap;

    /**
     * Creates new {@link UserConverter}.
     */
    public UserConverter() {
        genderMap = new TreeMap<>();
        genderMap.put("male", true);
        genderMap.put("female", false);
    }

    /**
     * Converts specified {@link RegistrationFormBean} to new instance of {@link User}
     * filled with data from {@link RegistrationFormBean}.
     *
     * @param registrationFormBean specified {@link RegistrationFormBean}
     * @return new instance of {@link User} filled with data from {@link RegistrationFormBean}
     */
    @Override
    public User convert(RegistrationFormBean registrationFormBean) {
        User user = new User(
                registrationFormBean.getFirstName(),
                registrationFormBean.getSecondName(),
                registrationFormBean.getLogin(),
                registrationFormBean.getEmail(),
                registrationFormBean.getPassword(),
                getBooleanValue(registrationFormBean.getGender()),
                DEFAULT_USER_ROLE);
        LOG.debug("RegistrationFormBean was converted to User.");
        return user;
    }

}
