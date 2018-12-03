package workshop.service;

import workshop.captcha.Captcha;
import workshop.captcha.CaptchaGenerator;
import workshop.captcha.strategy.CaptchaStorageStrategy;
import workshop.exception.AppException;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Service for handling with captcha.
 */
public class CaptchaService {

    private static final Logger LOG = Logger.getLogger(CaptchaService.class);
    private CaptchaStorageStrategy captchaStorageStrategy;
    private int timeout;

    /**
     * Creates {@link CaptchaService} with specified {@link CaptchaStorageStrategy} and specified timeout
     *
     * @param captchaStorageStrategy specified {@link CaptchaStorageStrategy}
     * @param timeout                specified timeout
     */
    public CaptchaService(CaptchaStorageStrategy captchaStorageStrategy, int timeout) {
        this.captchaStorageStrategy = captchaStorageStrategy;
        this.timeout = timeout;
    }

    public void createCaptcha(HttpServletRequest request, HttpServletResponse response) throws AppException {
        CaptchaGenerator captchaGenerator = new CaptchaGenerator();
        captchaStorageStrategy.setCaptcha(request, response, captchaGenerator.create(timeout));
        LOG.debug("New captcha was created.");
    }

    public Captcha getCaptcha(HttpServletRequest request) throws AppException {
        return captchaStorageStrategy.getCaptcha(request);
    }

}
