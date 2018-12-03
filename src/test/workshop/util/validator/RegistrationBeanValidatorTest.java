package workshop.util.validator;

import workshop.captcha.Captcha;
import workshop.bean.RegistrationFormBean;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static workshop.constants.Constants.Messages.*;

public class RegistrationBeanValidatorTest {

    private static final String CAPTCHA_ERROR_DIV_ID = "captcha_error";
    private static final String FIRST_NAME_ERROR_DIV_ID = "first_name_error";
    private static final String SECOND_NAME_ERROR_DIV_ID = "second_name_error";
    private static final String LOGIN_ERROR_DIV_ID = "login_error";
    private static final String EMAIL_ERROR_DIV_ID = "email_error";
    private static final String PASS_ERROR_DIV_ID = "password_error";
    private static final String PASS_CONFIRMATION_ERROR_DIV_ID = "password_confirm_error";
    private static final String PASS_EQUALS_ERROR_DIV_ID = "equals_error";
    private static final String GENDER_ERROR_DIV_ID = "gender_error";

    private static final String EMPTY_FIELD = "";
    private static final String VALID_NAME = "validName";
    private static final String INVALID_NAME = "invalidName123";
    private static final String VALID_LOGIN = "valid_login";
    private static final String INVALID_LOGIN = "invalid.login";
    private static final String VALID_EMAIL = "valid@email.com";
    private static final String INVALID_EMAIL = "invalidEmail";
    private static final String VALID_PASS = "Valid.password123";
    private static final String ANOTHER_VALID_PASS = "AnotherValid.password123";
    private static final String INVALID_PASS = "invalidPassword";
    private static final String VALID_GENDER = "male";
    private static final String INVALID_GENDER = "gender";
    private static final String VALID_CAPTCHA_VALUE = "value";
    private static final String INVALID_CAPTCHA_VALUE = "invalidValue";
    private static final Map<String, String> EMPTY_MAP = new HashMap<>();
    private Map<String, String> errorMap;
    private Captcha captcha;
    private RegistrationFormBean registrationFormBean;
    private RegisterFormBeanValidator registerFormBeanValidator;

    @Before
    public void setUp() {
        captcha = mock(Captcha.class);
        registrationFormBean = mock(RegistrationFormBean.class);
        registerFormBeanValidator = new RegisterFormBeanValidator();
        when(captcha.isExpired()).thenReturn(false);
        when(captcha.getValue()).thenReturn(VALID_CAPTCHA_VALUE);
    }

    @Test
    public void testsIfValidateMethodReturnsEmptyMapIfAllFieldsAreValid() {
        errorMap = EMPTY_MAP;
        when(registrationFormBean.getFirstName()).thenReturn(VALID_NAME);
        when(registrationFormBean.getSecondName()).thenReturn(VALID_NAME);
        when(registrationFormBean.getLogin()).thenReturn(VALID_LOGIN);
        when(registrationFormBean.getEmail()).thenReturn(VALID_EMAIL);
        when(registrationFormBean.getGender()).thenReturn(VALID_GENDER);
        when(registrationFormBean.getPassword()).thenReturn(VALID_PASS);
        when(registrationFormBean.getPasswordConfirm()).thenReturn(VALID_PASS);
        when(registrationFormBean.getCaptcha()).thenReturn(VALID_CAPTCHA_VALUE);
        assertEquals(errorMap, registerFormBeanValidator.validate(registrationFormBean, captcha));
    }

    @Test
    public void testsIfValidateMethodReturnsMapWithErrorsIfSomeFieldsAreEmpty() {
        errorMap = new HashMap<>();
        errorMap.put(FIRST_NAME_ERROR_DIV_ID, EMPTY_FIELD_MESSAGE);
        errorMap.put(SECOND_NAME_ERROR_DIV_ID, EMPTY_FIELD_MESSAGE);
        when(registrationFormBean.getFirstName()).thenReturn(EMPTY_FIELD);
        when(registrationFormBean.getSecondName()).thenReturn(EMPTY_FIELD);
        when(registrationFormBean.getLogin()).thenReturn(VALID_LOGIN);
        when(registrationFormBean.getEmail()).thenReturn(VALID_EMAIL);
        when(registrationFormBean.getGender()).thenReturn(VALID_GENDER);
        when(registrationFormBean.getPassword()).thenReturn(VALID_PASS);
        when(registrationFormBean.getPasswordConfirm()).thenReturn(VALID_PASS);
        when(registrationFormBean.getCaptcha()).thenReturn(VALID_CAPTCHA_VALUE);
        assertEquals(errorMap, registerFormBeanValidator.validate(registrationFormBean, captcha));
    }

    @Test
    public void testsIfValidateMethodReturnsMapWithErrorsIfFieldsAreInvalid() {
        errorMap = new HashMap<>();
        errorMap.put(FIRST_NAME_ERROR_DIV_ID, FIRST_NAME_INVALID_MESSAGE);
        errorMap.put(SECOND_NAME_ERROR_DIV_ID, SECOND_NAME_INVALID_MESSAGE);
        errorMap.put(LOGIN_ERROR_DIV_ID, LOGIN_INVALID_MESSAGE);
        errorMap.put(EMAIL_ERROR_DIV_ID, EMAIL_INVALID_MESSAGE);
        errorMap.put(GENDER_ERROR_DIV_ID, EMPTY_FIELD_MESSAGE);
        errorMap.put(PASS_ERROR_DIV_ID, PASS_INVALID_MESSAGE);
        errorMap.put(PASS_CONFIRMATION_ERROR_DIV_ID, PASS_INVALID_MESSAGE);
        errorMap.put(CAPTCHA_ERROR_DIV_ID, CAPTCHA_VALUE_INVALID_MESSAGE);
        when(registrationFormBean.getFirstName()).thenReturn(INVALID_NAME);
        when(registrationFormBean.getSecondName()).thenReturn(INVALID_NAME);
        when(registrationFormBean.getLogin()).thenReturn(INVALID_LOGIN);
        when(registrationFormBean.getEmail()).thenReturn(INVALID_EMAIL);
        when(registrationFormBean.getGender()).thenReturn(INVALID_GENDER);
        when(registrationFormBean.getPassword()).thenReturn(INVALID_PASS);
        when(registrationFormBean.getPasswordConfirm()).thenReturn(INVALID_PASS);
        when(registrationFormBean.getCaptcha()).thenReturn(INVALID_CAPTCHA_VALUE);
        assertEquals(errorMap, registerFormBeanValidator.validate(registrationFormBean, captcha));
    }

    @Test
    public void testsIfValidateMethodReturnsMapWithErrorsIfPasswordsAreNotEquals() {
        errorMap = new HashMap<>();
        errorMap.put(PASS_EQUALS_ERROR_DIV_ID, PASS_NOT_EQUALS_MESSAGE);
        when(registrationFormBean.getFirstName()).thenReturn(VALID_NAME);
        when(registrationFormBean.getSecondName()).thenReturn(VALID_NAME);
        when(registrationFormBean.getLogin()).thenReturn(VALID_LOGIN);
        when(registrationFormBean.getEmail()).thenReturn(VALID_EMAIL);
        when(registrationFormBean.getGender()).thenReturn(VALID_GENDER);
        when(registrationFormBean.getPassword()).thenReturn(VALID_PASS);
        when(registrationFormBean.getPasswordConfirm()).thenReturn(ANOTHER_VALID_PASS);
        when(registrationFormBean.getCaptcha()).thenReturn(VALID_CAPTCHA_VALUE);
        assertEquals(errorMap, registerFormBeanValidator.validate(registrationFormBean, captcha));
    }

    @Test
    public void testsIfValidateMethodReturnsMapWithErrorsIfCaptchaIsNullOrExpired() {
        errorMap = new HashMap<>();
        when(captcha.isExpired()).thenReturn(true);
        errorMap.put(CAPTCHA_ERROR_DIV_ID, TIME_OUT_ERROR_MESSAGE);
        when(registrationFormBean.getFirstName()).thenReturn(VALID_NAME);
        when(registrationFormBean.getSecondName()).thenReturn(VALID_NAME);
        when(registrationFormBean.getLogin()).thenReturn(VALID_LOGIN);
        when(registrationFormBean.getEmail()).thenReturn(VALID_EMAIL);
        when(registrationFormBean.getGender()).thenReturn(VALID_GENDER);
        when(registrationFormBean.getPassword()).thenReturn(VALID_PASS);
        when(registrationFormBean.getPasswordConfirm()).thenReturn(VALID_PASS);
        assertEquals(errorMap, registerFormBeanValidator.validate(registrationFormBean, captcha));
    }

}