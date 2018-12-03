package workshop.captcha.strategy;

import workshop.captcha.Captcha;
import org.apache.log4j.Logger;

import java.util.Map;

/**
 * {@link Thread} that cleans specified map with captchas.
 */
public class CaptchaCleaner extends Thread {

    private static final Logger LOG = Logger.getLogger(CaptchaCleaner.class);

    private Map<Long, Captcha> captchaMap;

    /**
     * Constructs {@link CaptchaCleaner} with specified map with captcha.
     *
     * @param captchaMap specified {@link Map}
     */
    public CaptchaCleaner(Map<Long, Captcha> captchaMap) {
        this.captchaMap = captchaMap;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void run() {
        int size = captchaMap.size();
        captchaMap
                .entrySet()
                .stream()
                .filter(e -> e.getValue().isExpired())
                .map(Map.Entry::getKey)
                .forEach(captchaMap::remove);
        if (captchaMap.size() < size) {
            LOG.debug("Captcha map was cleaned.");
        }
    }
}
