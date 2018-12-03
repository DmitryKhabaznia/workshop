package workshop.localization.strategy;

import workshop.localization.strategy.impl.CookieStorageStrategy;
import workshop.localization.strategy.impl.SessionStorageStrategy;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * Container for strategies of storing locale.
 */
public class LocaleStorageStrategyContainer {

    private static final Logger LOG = Logger.getLogger(LocaleStorageStrategyContainer.class);
    private static final String COOKIE_STRATEGY = "cookie";
    private static final String SESSION_STRATEGY = "session";

    private Map<String, LocaleStorageStrategy> strategies;

    /**
     * Constructs container.
     */
    public LocaleStorageStrategyContainer(int cookieExpiringTime) {
        strategies = new HashMap<>();
        strategies.put(COOKIE_STRATEGY, new CookieStorageStrategy(cookieExpiringTime));
        strategies.put(SESSION_STRATEGY, new SessionStorageStrategy());
        LOG.debug("Strategy container was initialized.");
    }

    /**
     * Gets strategy from container by specified strategy name.
     *
     * @param strategyName specified strategy name
     * @return {@link LocaleStorageStrategy}
     */
    public LocaleStorageStrategy getStrategy(String strategyName) {
        if (strategyName == null || !strategies.containsKey(strategyName)) {
            return null;
        }
        LOG.debug("New strategy was chosen: -> " + strategyName);
        return strategies.get(strategyName);
    }

}
