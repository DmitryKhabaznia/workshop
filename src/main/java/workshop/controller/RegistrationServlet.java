package workshop.controller;

import workshop.captcha.Captcha;
import workshop.bean.RegistrationFormBean;
import workshop.db.entity.impl.User;
import workshop.exception.ServiceException;
import workshop.service.AvatarService;
import workshop.service.CaptchaService;
import workshop.exception.AppException;
import workshop.service.UserService;
import workshop.util.converter.impl.UserConverter;
import workshop.util.validator.RegisterFormBeanValidator;
import org.apache.log4j.Logger;
import workshop.constants.Constants;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

import static org.apache.commons.lang3.StringUtils.EMPTY;

/**
 * Servlet for registration user in the application.
 */
@MultipartConfig(fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 5)
@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(RegistrationServlet.class);

    private static final String FIRST_NAME_FIELD = "firstName";
    private static final String SECOND_NAME_FIELD = "secondName";
    private static final String LOGIN_FIELD = "login";
    private static final String EMAIL_FIELD = "email";
    private static final String PASS_FIELD = "password";
    private static final String PASS_CONFIRM_FIELD = "passwordConfirm";
    private static final String GENDER_FIELD = "gender";
    private static final String CAPTCHA_FIELD = "captcha";
    private static final String SUCCESS_MESSAGE = "User was successfully registered.";
    private static final String ERROR_DIV_ID = "error";
    private static final String AVATAR_NAME = "imageName";

    private UserService userService;
    private AvatarService avatarService;
    private UserConverter userConverter;
    private CaptchaService captchaService;
    private RegisterFormBeanValidator registerFormBeanValidator;

    /**
     * {@inheritDoc}
     */
    @Override
    public void init(ServletConfig config) {
        ServletContext servletContext = config.getServletContext();
        userService = (UserService) servletContext.getAttribute(Constants.AppContextConstants.USER_SERVICE);
        userConverter = (UserConverter) servletContext.getAttribute(Constants.AppContextConstants.USER_CONVERTER);
        avatarService = (AvatarService) servletContext.getAttribute(Constants.AppContextConstants.AVATAR_SERVICE);
        captchaService = (CaptchaService) servletContext.getAttribute(Constants.AppContextConstants.CAPTCHA_SERVICE);
        registerFormBeanValidator = (RegisterFormBeanValidator) servletContext.getAttribute(Constants.AppContextConstants.REGISTER_FORM_BEAN_VALIDATOR);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.debug("Register servlet starts. Method GET.");
        String forward = Constants.AppContextConstants.ERROR_PAGE;
        try {
            captchaService.createCaptcha(req, resp);
            forward = Constants.AppContextConstants.SIGN_UP_PAGE;
        } catch (AppException ex) {
            LOG.error("Can not process doGet method", ex);
            req.setAttribute(Constants.AppContextConstants.ERROR_MESSAGE_DIV, ex.getMessage());
        }
        LOG.debug("Forward to path: " + forward);
        req.getRequestDispatcher(forward).forward(req, resp);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.debug("Register servlet starts. Method POST.");
        String forward = processAndGetRedirectPath(req);
        LOG.debug("Forward to path: " + forward);
        resp.sendRedirect(forward);
    }

    /**
     * Processes request by validation data, that was inputted by user, constructs error map or adds new user to
     * application repository. Returns path for forwarding.
     *
     * @param req specified {@link HttpServletRequest}
     * @return path for forwarding
     */
    private String processAndGetRedirectPath(HttpServletRequest req) throws IOException, ServletException {
        String forward = Constants.AppContextConstants.ERROR_PAGE;
        try {
            RegistrationFormBean registrationFormBean = new RegistrationFormBean();
            fillFormBean(registrationFormBean, req);
            Captcha captcha = captchaService.getCaptcha(req);
            Map<String, String> errorMap = registerFormBeanValidator.validate(registrationFormBean, captcha);
            forward = Constants.AppContextConstants.REGISTRATION_SERVLET;
            if (errorMap.isEmpty() && addUser(req, errorMap, registrationFormBean)) {
                HttpSession session = req.getSession();
                Object previousPage = session.getAttribute(Constants.AppContextConstants.PREVIOUS_PAGE);
                forward = previousPage == null? Constants.AppContextConstants.INDEX_PAGE : previousPage.toString();
                session.removeAttribute(Constants.AppContextConstants.PREVIOUS_PAGE);
            }
            setErrors(errorMap, registrationFormBean, req);
        } catch (AppException ex) {
            LOG.debug("Can not register user", ex);
            req.setAttribute(Constants.AppContextConstants.ERROR_MESSAGE_DIV, ex.getMessage());
        }
        return forward;
    }

    /**
     * Sets specified {@link RegistrationFormBean} with errors and specified {@link RegistrationFormBean} to session, if it is not empty.
     *
     * @param errorMap             specified {@link Map}
     * @param registrationFormBean specified {@link RegistrationFormBean}
     * @param req                  specified {@link HttpServletRequest}
     */
    private void setErrors(Map<String, String> errorMap, RegistrationFormBean registrationFormBean, HttpServletRequest req) {
        if (!errorMap.isEmpty()) {
            LOG.debug("Registration failed.");
            LOG.debug("Setting attribute to session context: -> " + errorMap.toString());
            registrationFormBean.setPassword(EMPTY);
            registrationFormBean.setPasswordConfirm(EMPTY);
            HttpSession session = req.getSession();
            session.setAttribute("errors", errorMap);
            session.setAttribute("formBean", registrationFormBean);
        }
    }

    /**
     * If error map is empty converts specified {@link RegistrationFormBean} to User entity and adds it to repository.
     *
     * @param errorMap             specified map with errors
     * @param registrationFormBean specified {@link RegistrationFormBean}
     * @return <tt>true</tt> if user was added, <tt>false</tt> otherwise
     */
    private boolean addUser(HttpServletRequest req, Map<String, String> errorMap, RegistrationFormBean registrationFormBean) throws IOException, ServletException, ServiceException {
        User user = userService.addUser(userConverter.convert(registrationFormBean));
        if (user != null) {
            req.getSession().setAttribute(Constants.AppContextConstants.USER_ATTRIBUTE, user);
            avatarService.setAvatar(req.getPart(AVATAR_NAME), user);
            req.getSession().setAttribute(Constants.AppContextConstants.SUCCESS_MESSAGE_DIV, SUCCESS_MESSAGE);
            LOG.debug("Registration successes.");
            return true;
        } else {
            errorMap.put(ERROR_DIV_ID, Constants.Messages.USER_EXISTS_ERROR_MESSAGE);
            return false;
        }
    }

    private void fillFormBean(RegistrationFormBean formBean, HttpServletRequest req) {
        formBean.setFirstName(req.getParameter(FIRST_NAME_FIELD));
        formBean.setSecondName(req.getParameter(SECOND_NAME_FIELD));
        formBean.setLogin(req.getParameter(LOGIN_FIELD));
        formBean.setEmail(req.getParameter(EMAIL_FIELD));
        formBean.setPassword(req.getParameter(PASS_FIELD));
        formBean.setPasswordConfirm(req.getParameter(PASS_CONFIRM_FIELD));
        formBean.setGender(req.getParameter(GENDER_FIELD));
        formBean.setCaptcha(req.getParameter(CAPTCHA_FIELD));
    }

}
