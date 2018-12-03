package workshop.captcha.strategy;

import workshop.captcha.Captcha;
import workshop.exception.AppException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Interface for strategy of storing captcha that defined in web.xml
 */
public interface CaptchaStorageStrategy {

    String CAPTCHA_ID_PARAMETER_NAME = "captchaId";

    /**
     * Set specified captcha in storage that defined by strategy.
     *
     * @param request  {@link HttpServletRequest}
     * @param response {@link HttpServletResponse}
     * @param captcha  specified {@link Captcha}
     */
    void setCaptcha(HttpServletRequest request, HttpServletResponse response, Captcha captcha) throws AppException;

    /**
     * Gets {@link Captcha} from storage that defined by strategy.
     *
     * @param request {@link HttpServletRequest}
     * @return {@link Captcha} from storage
     */
    Captcha getCaptcha(HttpServletRequest request) throws AppException;

}
