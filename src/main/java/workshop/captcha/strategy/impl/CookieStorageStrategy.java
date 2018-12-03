package workshop.captcha.strategy.impl;

import workshop.captcha.Captcha;
import workshop.captcha.strategy.CaptchaCleaner;
import workshop.captcha.strategy.CaptchaStorageStrategy;
import workshop.exception.AppException;
import org.apache.log4j.Logger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicLong;

import static workshop.constants.Constants.Messages.NO_COOKIES_MESSAGE;
import static java.lang.Long.parseLong;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Strategy that store captcha in cookies.
 */
public class CookieStorageStrategy implements CaptchaStorageStrategy {

    private static final Logger LOG = Logger.getLogger(CookieStorageStrategy.class);
    private Map<Long, Captcha> captchaMap;
    private AtomicLong idGenerator;
    private CaptchaCleaner captchaCleaner;
    private int time;

    public CookieStorageStrategy(int time) {
        this.captchaMap = new ConcurrentHashMap<>();
        idGenerator = new AtomicLong();
        this.time = time;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCaptcha(HttpServletRequest request, HttpServletResponse response, Captcha captcha) throws AppException {
        if (captchaCleaner == null) {
            captchaCleaner = new CaptchaCleaner(captchaMap);
            ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
            executorService.scheduleAtFixedRate(captchaCleaner, time, time, SECONDS);
        } else {
            captchaMap.remove(getId(request));
        }
        Long id = idGenerator.incrementAndGet();
        captchaMap.put(id, captcha);
        request.setAttribute(CAPTCHA_ID_PARAMETER_NAME, id);
        Cookie cookie = new Cookie(CAPTCHA_ID_PARAMETER_NAME, id.toString());
        response.addCookie(cookie);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Captcha getCaptcha(HttpServletRequest request) throws AppException {
        return captchaMap.get(getId(request));
    }

    private long getId(HttpServletRequest request) throws AppException {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            LOG.error("Can not get cookies.");
            throw new AppException(NO_COOKIES_MESSAGE);
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(CAPTCHA_ID_PARAMETER_NAME)) {
                return parseLong(cookie.getValue());
            }
        }
        return -1;
    }

}
