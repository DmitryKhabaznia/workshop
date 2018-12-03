package workshop.captcha.strategy.impl;

import workshop.captcha.Captcha;
import workshop.captcha.strategy.CaptchaCleaner;
import workshop.captcha.strategy.CaptchaStorageStrategy;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicLong;

import static java.util.concurrent.TimeUnit.SECONDS;
import static java.util.regex.Pattern.compile;

/**
 * Storage strategy that store captcha in hidden field of page.
 */
public class HiddenFieldStorageStrategy implements CaptchaStorageStrategy {

    private static final Logger LOG = Logger.getLogger(HiddenFieldStorageStrategy.class);
    private static final String NUMBER_REGEX = "\\d*";
    private Map<Long, Captcha> captchaMap;
    private AtomicLong idGenerator;
    private CaptchaCleaner captchaCleaner;
    private int time;

    public HiddenFieldStorageStrategy(int time) {
        this.captchaMap = new ConcurrentHashMap<>();
        idGenerator = new AtomicLong();
        this.time = time;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCaptcha(HttpServletRequest request, HttpServletResponse response, Captcha captcha) {
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
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Captcha getCaptcha(HttpServletRequest request) {
        return captchaMap.get(getId(request));
    }

    private Long getId(HttpServletRequest request) {
        String id = request.getParameter(CAPTCHA_ID_PARAMETER_NAME);
        LOG.debug("Captcha was occurred.");
        return id != null && compile(NUMBER_REGEX).matcher(id).matches() ? Long.parseLong(id) : -1L;
    }
}
