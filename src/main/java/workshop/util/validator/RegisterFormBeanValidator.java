package workshop.util.validator;

import workshop.captcha.Captcha;
import workshop.bean.RegistrationFormBean;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static workshop.constants.Constants.Messages.*;
import static workshop.constants.Gender.*;

/**
 * Validates specified {@link RegistrationFormBean}.
 */
public class RegisterFormBeanValidator {

    /**
     * Logger
     */
    private static final Logger LOG = Logger.getLogger(RegisterFormBeanValidator.class);

    /**
     * AppContextConstants
     */
    private static final String CAPTCHA_ERROR_DIV_ID = "captcha_error";
    private static final String FIRST_NAME_ERROR_DIV_ID = "first_name_error";
    private static final String SECOND_NAME_ERROR_DIV_ID = "second_name_error";
    private static final String LOGIN_ERROR_DIV_ID = "login_error";
    private static final String EMAIL_ERROR_DIV_ID = "email_error";
    private static final String PASS_ERROR_DIV_ID = "password_error";
    private static final String PASS_CONFIRMATION_ERROR_DIV_ID = "password_confirm_error";
    private static final String PASS_EQUALS_ERROR_DIV_ID = "equals_error";
    private static final String GENDER_ERROR_DIV_ID = "gender_error";
    private static final String NAME_REGEX = "^[A-Za-z\\u0410-\\u044F]{3,15}$";
    private static final String LOGIN_REGEX = "^[A-Za-z0-9_]{3,15}$";
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9.%-]+@[a-zA-Z0-9.%-]+\\.[a-zA-Z]{2,6}$";
    private static final String PASS_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[\\.@#$%^&+=])(?=\\S+$).{8,}$";

    private Map<String, String> errorMap;

    /**
     * Validates specified {@link RegisterFormBeanValidator} and returns {@link Map} with error messages.
     *
     * @param registrationFormBean specified {@link RegistrationFormBean}
     * @param captcha              specified {@link Captcha}
     */
    public Map<String, String> validate(RegistrationFormBean registrationFormBean, Captcha captcha) {
        errorMap = new HashMap<>();
        LOG.debug("Inputted data: " + registrationFormBean.toString());
        validateField(NAME_REGEX, FIRST_NAME_ERROR_DIV_ID, FIRST_NAME_INVALID_MESSAGE, registrationFormBean.getFirstName());
        validateField(NAME_REGEX, SECOND_NAME_ERROR_DIV_ID, SECOND_NAME_INVALID_MESSAGE, registrationFormBean.getSecondName());
        validateField(LOGIN_REGEX, LOGIN_ERROR_DIV_ID, LOGIN_INVALID_MESSAGE, registrationFormBean.getLogin());
        validateField(EMAIL_REGEX, EMAIL_ERROR_DIV_ID, EMAIL_INVALID_MESSAGE, registrationFormBean.getEmail());
        validateField(PASS_REGEX, PASS_ERROR_DIV_ID, PASS_INVALID_MESSAGE, registrationFormBean.getPassword());
        validateField(PASS_REGEX, PASS_CONFIRMATION_ERROR_DIV_ID, PASS_INVALID_MESSAGE, registrationFormBean.getPasswordConfirm());
        validateCaptcha(registrationFormBean, captcha);
        validatePasswordEquals(registrationFormBean);
        validateGender(registrationFormBean);
        LOG.debug("Validation finished.");
        return errorMap;
    }

    /**
     * Checks if value of specified field is empty.
     *
     * @param divId name of div container of specified field on jsp page
     * @param field name of field
     * @return <tt>true</tt> if value of specified field is empty or null, <tt>false</tt> otherwise
     */
    private boolean isEmpty(String divId, String field) {
        if (field == null || field.isEmpty()) {
            errorMap.put(divId, EMPTY_FIELD_MESSAGE);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Validates value of specified field with specified regular expression.
     *
     * @param regex   specified regular expression
     * @param divId   name of div container of specified field on jsp page
     * @param message message for UI
     * @param field   name of field
     */
    private void validateField(String regex, String divId, String message, String field) {
        if (!isEmpty(divId, field)) {
            Matcher matcher = Pattern.compile(regex).matcher(field);
            if (!matcher.matches()) {
                errorMap.put(divId, message);
            }
        }
    }

    /**
     * Checks if values in password field and password confirmation field are equal.
     */
    private void validatePasswordEquals(RegistrationFormBean registrationFormBean) {
        if (!registrationFormBean.getPassword().equals(registrationFormBean.getPasswordConfirm())) {
            errorMap.put(PASS_EQUALS_ERROR_DIV_ID, PASS_NOT_EQUALS_MESSAGE);
        }
    }

    /**
     * Checks if value of gender field is not null and equal of specified values.
     */
    private void validateGender(RegistrationFormBean registrationFormBean) {
        String gender = registrationFormBean.getGender();
        if (!isEmpty(GENDER_ERROR_DIV_ID, gender) &&
                !(gender.equalsIgnoreCase(MALE.name()) || gender.equalsIgnoreCase(FEMALE.name()))) {
            errorMap.put(GENDER_ERROR_DIV_ID, EMPTY_FIELD_MESSAGE);
        }
    }


    /**
     * Checks if value of captcha field is equal of value of captcha.
     */
    private void validateCaptcha(RegistrationFormBean registrationFormBean, Captcha captcha) {
        if (captcha == null || captcha.isExpired()) {
            errorMap.put(CAPTCHA_ERROR_DIV_ID, TIME_OUT_ERROR_MESSAGE);
        } else if (!isEmpty(CAPTCHA_ERROR_DIV_ID, registrationFormBean.getCaptcha()) &&
                !registrationFormBean.getCaptcha().equals(captcha.getValue())) {
            errorMap.put(CAPTCHA_ERROR_DIV_ID, CAPTCHA_VALUE_INVALID_MESSAGE);
        }
    }

}


