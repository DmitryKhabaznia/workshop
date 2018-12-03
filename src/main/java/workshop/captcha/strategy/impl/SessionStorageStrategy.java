package workshop.captcha.strategy.impl;

import workshop.captcha.Captcha;
import workshop.captcha.strategy.CaptchaStorageStrategy;
import workshop.exception.AppException;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static workshop.constants.Constants.Messages.SESSION_IS_NULL_MESSAGE;


/**
 * Storage strategy that store captcha in session.
 */
public class SessionStorageStrategy implements CaptchaStorageStrategy {

    private static final Logger LOG = Logger.getLogger(SessionStorageStrategy.class);
    private static final String CAPTCHA_ATTRIBUTE_NAME = "captcha";

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCaptcha(HttpServletRequest request, HttpServletResponse response, Captcha captcha) {
        request.getSession().setAttribute(CAPTCHA_ATTRIBUTE_NAME, captcha);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Captcha getCaptcha(HttpServletRequest request) throws AppException {
        HttpSession session = request.getSession(false);
        if (session == null) {
            LOG.error("Session is null.");
            throw new AppException(SESSION_IS_NULL_MESSAGE);
        }
        Object captcha = session.getAttribute(CAPTCHA_ATTRIBUTE_NAME);
        return captcha == null ? null : (Captcha) captcha;
    }

}
