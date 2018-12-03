package workshop.controller;

import workshop.exception.AppException;
import workshop.bean.LoginFormBean;
import workshop.db.entity.impl.User;
import workshop.exception.ServiceException;
import workshop.service.UserService;
import org.apache.log4j.Logger;
import workshop.constants.Constants;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


/**
 * Servlet for user login.
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private static final String LOGIN_FIELD = "login";
    private static final String PASS_FIELD = "password";

    private static final String ERROR_DIV = "login_error";
    private static final String USER_EXISTS_ERROR_MESSAGE = "User with such login doesn't exist.";
    private static final String PASS_IS_INVALID_ERROR_MESSAGE = "Password is invalid.";
    private static final String REFERER = "referer";
    private static final String SEPARATOR = "/";
    private static final Logger LOG = Logger.getLogger(LoginServlet.class);
    private UserService userService;

    /**
     * {@inheritDoc}
     */
    @Override
    public void init(ServletConfig config) {
        ServletContext servletContext = config.getServletContext();
        userService = (UserService) servletContext.getAttribute(Constants.AppContextConstants.USER_SERVICE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        LOG.debug("Login servlet starts.");
        String redirectPath = processAndGetRedirectPath(req);
        LOG.debug("Forward to path: " + redirectPath);
        resp.sendRedirect(redirectPath);
    }

    /**
     * Checks if user is registered in application and returns path for sendRedirecting
     *
     * @param req specified {@link HttpServletRequest}
     * @return path for sendRedirecting
     */
    private String processAndGetRedirectPath(HttpServletRequest req) {
        String resultPath = Constants.AppContextConstants.ERROR_PAGE;
        try {
            String contextPath = req.getContextPath();
            String previousPagePath = req.getHeader(REFERER).split(contextPath)[1];
            resultPath = contextPath + previousPagePath;
            if (isLogin(req) && previousPagePath.equals(SEPARATOR + Constants.AppContextConstants.REGISTRATION_SERVLET)) {
                resultPath = Constants.AppContextConstants.INDEX_PAGE;
            }
        } catch (AppException ex) {
            LOG.error("Can not login user.", ex);
            req.setAttribute(Constants.AppContextConstants.ERROR_MESSAGE_DIV, ex.getMessage());
        }
        return resultPath;
    }

    /**
     * Checks if user is login.
     *
     * @param req specified {@link HttpServletRequest}
     * @return <tt>true</tt> if user is found in database, <tt>false</tt> otherwise
     */
    private boolean isLogin(HttpServletRequest req) throws ServiceException {
        boolean result = false;
        LoginFormBean loginFormBean = new LoginFormBean();
        fillFormBean(loginFormBean, req);
        User user = userService.findUserByLogin(loginFormBean.getLogin());
        HttpSession session = req.getSession();
        if (user != null)
            if (user.getPassword().equals(loginFormBean.getPassword())) {
                session.setAttribute(Constants.AppContextConstants.USER_ATTRIBUTE, user);
                result = true;
            } else {
                session.setAttribute(ERROR_DIV, PASS_IS_INVALID_ERROR_MESSAGE);
            }
        else {
            session.setAttribute(ERROR_DIV, USER_EXISTS_ERROR_MESSAGE);
        }
        return result;
    }

    private void fillFormBean(LoginFormBean loginFormBean, HttpServletRequest req) {
        loginFormBean.setLogin(req.getParameter(LOGIN_FIELD));
        loginFormBean.setPassword(req.getParameter(PASS_FIELD));
    }

}
