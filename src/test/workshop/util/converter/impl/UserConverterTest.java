package workshop.util.converter.impl;

import workshop.bean.RegistrationFormBean;
import workshop.db.entity.impl.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserConverterTest {

    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastName";
    private static final String LOGIN = "login";
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    private static final String MALE_GENDER = "male";
    private static final String ROLE = "user";

    @Mock
    private RegistrationFormBean registrationFormBean;

    @InjectMocks
    private UserConverter userConverter;

    private User user;

    @Before
    public void setUp () {
        user = new User();
        user.setFirstName(FIRST_NAME);
        user.setLastName(LAST_NAME);
        user.setLogin(LOGIN);
        user.setEmail(EMAIL);
        user.setPassword(PASSWORD);
        user.setMale(true);
        user.setRole(ROLE);
    }

    @Test
    public void shouldReturnUserThatEqualsOfSettedUpUser() {
        when(registrationFormBean.getFirstName()).thenReturn(FIRST_NAME);
        when(registrationFormBean.getSecondName()).thenReturn(LAST_NAME);
        when(registrationFormBean.getLogin()).thenReturn(LOGIN);
        when(registrationFormBean.getEmail()).thenReturn(EMAIL);
        when(registrationFormBean.getPassword()).thenReturn(PASSWORD);
        when(registrationFormBean.getGender()).thenReturn(MALE_GENDER);
        assertEquals(user, userConverter.convert(registrationFormBean));
    }

}