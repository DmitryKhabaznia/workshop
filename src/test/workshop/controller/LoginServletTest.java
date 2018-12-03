package workshop.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import workshop.db.entity.impl.User;
import workshop.exception.ServiceException;
import workshop.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
import static workshop.constants.Constants.AppContextConstants.INDEX_PAGE;
import static workshop.constants.Constants.AppContextConstants.USER_ATTRIBUTE;

@RunWith(MockitoJUnitRunner.class)
public class LoginServletTest {

    private static final String PASS = "pass";
    private static final String INVALID_PASS = "invalid_pass";
    private static final String PASS_FIELD = "password";
    private static final String HEADER = "header/shop/header";
    private static final String REGISTRATION_HEADER = "header/shop/registration";
    private static final String REDIRECT_PATH = "shop/header";
    private static final String CONTEXT_PATH = "shop";
    private static final String ERROR_DIV = "login_error";
    private static final String USER_EXISTS_ERROR_MESSAGE = "User with such login doesn't exist.";
    private static final String PASS_IS_INVALID_ERROR_MESSAGE = "Password is invalid.";
    @Mock
    private User user;
    @Mock
    private UserService userService;
    @Mock
    private HttpSession session;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;

    @InjectMocks
    private LoginServlet loginServlet;

    @Before
    public void setUp() {
    }

    @Test
    public void shouldAddUserToSessionAndSendRedirectToIndexPageIfUserIsExistAndPreviousPageWasRegistrationPage() throws IOException, ServiceException {
        when(request.getContextPath()).thenReturn(CONTEXT_PATH);
        when(userService.findUserByLogin(anyString())).thenReturn(user);
        when(user.getPassword()).thenReturn(PASS);
        when(request.getSession()).thenReturn(session);
        when(request.getParameter(PASS_FIELD)).thenReturn(PASS);
        when(request.getHeader(anyString())).thenReturn(REGISTRATION_HEADER);
        loginServlet.doPost(request, response);
        InOrder inOrder = inOrder(request, userService, session, user, session, response);
        inOrder.verify(request).getContextPath();
        inOrder.verify(request).getHeader(anyString());
        inOrder.verify(userService).findUserByLogin(anyString());
        inOrder.verify(request).getSession();
        inOrder.verify(user).getPassword();
        inOrder.verify(session).setAttribute(USER_ATTRIBUTE, user);
        inOrder.verify(response).sendRedirect(INDEX_PAGE);
    }

    @Test
    public void shouldAddErrorMessageToDivIfUserIsNotExist() throws IOException, ServiceException {
        when(request.getContextPath()).thenReturn(CONTEXT_PATH);
        when(request.getHeader(anyString())).thenReturn(HEADER);
        when(userService.findUserByLogin(anyString())).thenReturn(null);
        when(request.getSession()).thenReturn(session);
        loginServlet.doPost(request, response);
        InOrder inOrder = inOrder(request, userService, session, user, session, response);
        inOrder.verify(request).getContextPath();
        inOrder.verify(request).getHeader(anyString());
        inOrder.verify(userService).findUserByLogin(anyString());
        inOrder.verify(request).getSession();
        inOrder.verify(session).setAttribute(ERROR_DIV, USER_EXISTS_ERROR_MESSAGE);
        inOrder.verify(response).sendRedirect(REDIRECT_PATH);
    }

    @Test
    public void shouldAddErrorMessageToDivIfPasswordIsNotEqual() throws IOException, ServiceException {
        when(request.getContextPath()).thenReturn(CONTEXT_PATH);
        when(request.getHeader(anyString())).thenReturn(HEADER);
        when(request.getParameter(PASS_FIELD)).thenReturn(INVALID_PASS);
        when(userService.findUserByLogin(anyString())).thenReturn(user);
        when(request.getSession()).thenReturn(session);
        when(user.getPassword()).thenReturn(PASS);
        loginServlet.doPost(request, response);
        InOrder inOrder = inOrder(request, userService, session, user, session);
        inOrder.verify(request).getContextPath();
        inOrder.verify(request).getHeader(anyString());
        inOrder.verify(userService).findUserByLogin(anyString());
        inOrder.verify(request).getSession();
        inOrder.verify(user).getPassword();
        inOrder.verify(session).setAttribute(ERROR_DIV, PASS_IS_INVALID_ERROR_MESSAGE);
    }

}