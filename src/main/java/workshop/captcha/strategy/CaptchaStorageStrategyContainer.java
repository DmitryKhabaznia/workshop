package workshop.captcha.strategy;

import workshop.captcha.strategy.impl.CookieStorageStrategy;
import workshop.captcha.strategy.impl.HiddenFieldStorageStrategy;
import workshop.captcha.strategy.impl.SessionStorageStrategy;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * Container for strategies of storing captcha.
 */
public class CaptchaStorageStrategyContainer {

    private static final Logger LOG = Logger.getLogger(CaptchaStorageStrategyContainer.class);
    private static final String HIDDEN_FIELD_STRATEGY = "hidden";
    private static final String COOKIE_STRATEGY = "cookie";
    private static final String SESSION_STRATEGY = "session";

    private Map<String, CaptchaStorageStrategy> strategies;

    /**
     * Constructs container with specified time of expiring of captcha.
     *
     * @param time specified time of expiring of captcha
     */
    public CaptchaStorageStrategyContainer(int time) {
        strategies = new HashMap<>();
        strategies.put(HIDDEN_FIELD_STRATEGY, new HiddenFieldStorageStrategy(time));
        strategies.put(COOKIE_STRATEGY, new CookieStorageStrategy(time));
        strategies.put(SESSION_STRATEGY, new SessionStorageStrategy());
        LOG.debug("Strategy container was initialized.");
    }

    /**
     * Gets strategy from container by specified strategy name.
     *
     * @param strategyName specified strategy name
     * @return {@link CaptchaStorageStrategy}
     */
    public CaptchaStorageStrategy getStrategy(String strategyName) {
        if (strategyName == null || !strategies.containsKey(strategyName)) {
            return null;
        }
        LOG.debug("New strategy was chosen: -> " + strategyName);
        return strategies.get(strategyName);
    }

}
