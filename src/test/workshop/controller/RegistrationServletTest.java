package workshop.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import workshop.bean.RegistrationFormBean;
import workshop.captcha.Captcha;
import workshop.db.entity.impl.User;
import workshop.exception.AppException;
import workshop.service.AvatarService;
import workshop.service.CaptchaService;
import workshop.service.UserService;
import workshop.util.converter.impl.UserConverter;
import workshop.util.validator.RegisterFormBeanValidator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.HashMap;

import static workshop.constants.Constants.AppContextConstants.INDEX_PAGE;
import static workshop.constants.Constants.AppContextConstants.REGISTRATION_SERVLET;
import static workshop.constants.Constants.AppContextConstants.SIGN_UP_PAGE;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RegistrationServletTest {

    @Mock
    private User user;
    @Mock
    private Captcha captcha;
    @Mock
    private UserService userService;
    @Mock
    private HttpSession httpSession;
    @Mock
    private HttpServletRequest request;
    @Mock
    private UserConverter userConverter;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private CaptchaService captchaService;
    @Mock
    private AvatarService avatarService;
    @Mock
    private RegisterFormBeanValidator registerFormBeanValidator;

    @InjectMocks
    private RegistrationServlet registrationServlet;

    @Test
    public void shouldForwardToSignUpPageWhenDoGetCalls() throws ServletException, IOException, AppException {
        when(captchaService.getCaptcha(request)).thenReturn(captcha);
        when(request.getRequestDispatcher(SIGN_UP_PAGE)).thenReturn(requestDispatcher);
        registrationServlet.doGet(request, response);
        InOrder inOrder = inOrder(captchaService, request);
        inOrder.verify(captchaService).createCaptcha(request, response);
        inOrder.verify(request).getRequestDispatcher(SIGN_UP_PAGE);
    }

    @Test
    public void shouldSendRedirectToIndexPageWhenErrorMapIsEmpty() throws ServletException, IOException, AppException {
        when(request.getSession()).thenReturn(httpSession);
        when(registerFormBeanValidator.validate(any(RegistrationFormBean.class), any(Captcha.class))).thenReturn(new HashMap<>());
        when(userConverter.convert(any(RegistrationFormBean.class))).thenReturn(user);
        when(userService.addUser(user)).thenReturn(user);
        registrationServlet.doPost(request, response);
        InOrder inOrder = inOrder(captchaService, registerFormBeanValidator,
                userConverter, userService, response);
        inOrder.verify(captchaService).getCaptcha(request);
        inOrder.verify(registerFormBeanValidator).validate(any(RegistrationFormBean.class), any(Captcha.class));
        inOrder.verify(userConverter).convert(any(RegistrationFormBean.class));
        inOrder.verify(userService).addUser(user);
        inOrder.verify(response).sendRedirect(INDEX_PAGE);
    }

    @Test
    public void shouldSendRedirectToRegistrationServletWhenErrorMapIsNotEmpty() throws ServletException, IOException, AppException {
        when(request.getSession()).thenReturn(httpSession);
        when(registerFormBeanValidator.validate(any(RegistrationFormBean.class), any(Captcha.class))).thenReturn(new HashMap<>());
        when(userConverter.convert(any(RegistrationFormBean.class))).thenReturn(user);
        when(userService.addUser(user)).thenReturn(null);
        registrationServlet.doPost(request, response);
        InOrder inOrder = inOrder(captchaService, registerFormBeanValidator,
                userConverter, userService, httpSession, response);
        inOrder.verify(captchaService).getCaptcha(request);
        inOrder.verify(registerFormBeanValidator).validate(any(RegistrationFormBean.class), any(Captcha.class));
        inOrder.verify(userConverter).convert(any(RegistrationFormBean.class));
        inOrder.verify(userService).addUser(user);
        inOrder.verify(httpSession, atLeast(2)).setAttribute(anyString(),anyObject());
        inOrder.verify(response).sendRedirect(REGISTRATION_SERVLET);
    }

}