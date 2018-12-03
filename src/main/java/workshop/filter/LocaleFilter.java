package workshop.filter;

import workshop.localization.LocaleRequestWrapper;
import workshop.localization.strategy.LocaleStorageStrategy;
import workshop.localization.strategy.LocaleStorageStrategyContainer;
import org.apache.commons.lang3.LocaleUtils;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class LocaleFilter implements Filter {

    private static final Logger LOG = Logger.getLogger(LocaleFilter.class);
    private static final String LOCALES_PARAM_NAME = "locales";
    private static final String DEFAULT_LOCALE_PARAM_NAME = "defaultLocale";
    private static final String DELIMITER = ",";
    private static final String LOCALE_STORING_STRATEGY = "storingStrategy";
    private static final String COOKIE_EXPIRING_TIME = "cookieExpiringTime";
    private static final String LANG = "lang";
    private static final String PARAMETERS_DELIMITER = "&";
    private static final String QUERY_STRING = "queryString";
    private static final Pattern LOCALE_PATTERN = Pattern.
            compile("^([a-zA-Z]{2})|([a-zA-Z]{2}_[a-zA-Z]{2})|([a-zA-Z]{2}_[a-zA-Z]{2}_[a-zA-Z]{2})$");
    private Locale defaultLocale;
    private List<Locale> locales;
    private LocaleStorageStrategy localeStorageStrategy;
    private int cookieExpiringTime;

    /**
     * {@inheritDoc}
     */
    @Override
    public void init(FilterConfig filterConfig) {
        defaultLocale = LocaleUtils.toLocale(filterConfig.getInitParameter(DEFAULT_LOCALE_PARAM_NAME));
        locales = Arrays.stream(filterConfig.getInitParameter(LOCALES_PARAM_NAME).split(DELIMITER))
                .map(LocaleUtils::toLocale)
                .collect(Collectors.toList());
        cookieExpiringTime = Integer.parseInt(filterConfig.getServletContext().getInitParameter(COOKIE_EXPIRING_TIME));
        localeStorageStrategy = new LocaleStorageStrategyContainer(cookieExpiringTime)
                .getStrategy(filterConfig.getInitParameter(LOCALE_STORING_STRATEGY));
        LOG.debug("Locale filter was initialised.");
    }

    /**
     * {@inheritDoc}
     * Rewrites locale for request.
     */
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        setParameters(request);
        Locale newLocale = getNewLocale(request);
        LocaleRequestWrapper newRequest = new LocaleRequestWrapper(request, newLocale);
        localeStorageStrategy.setLocale(newRequest, response, newLocale);
        chain.doFilter(newRequest, response);
    }

    private Locale getNewLocale(HttpServletRequest request) {
        Locale resultLocale = userDefinedLocale(request);
        if (resultLocale != null) {
            return resultLocale;
        }
        resultLocale = storageDefinedLocale(request);
        if (resultLocale != null) {
            return resultLocale;
        }
        resultLocale = browserDefinedLocale(request);
        if (resultLocale != null) {
            return resultLocale;
        }
        return defaultLocale;


    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void destroy() {
        LOG.debug("Local filter was destroyed.");
    }

    private Locale userDefinedLocale(HttpServletRequest request) {
        String localeName = request.getParameter(LANG);
        if (localeName != null && LOCALE_PATTERN.matcher(localeName).matches()) {
            for (Locale locale : locales) {
                if (locale.equals(LocaleUtils.toLocale(localeName))) {
                    return locale;
                }
            }
        }
        return null;
    }

    private Locale storageDefinedLocale(HttpServletRequest request) {
        return localeStorageStrategy.getLocale(request);
    }

    private Locale browserDefinedLocale(HttpServletRequest request) {
        Predicate<Locale> contains = locale -> locales.stream()
                .anyMatch(availableLocale -> availableLocale.toString().contains(locale.toString())
                                           || locale.toString().contains(availableLocale.toString()));
        return Collections.list(request.getLocales()).stream()
                .filter(contains)
                .findFirst()
                .orElse(defaultLocale);
    }

    private void setParameters(HttpServletRequest request) {
        String queryString = request.getQueryString();
        if (queryString != null) {
            String result = Arrays.stream(queryString.split(PARAMETERS_DELIMITER))
                    .filter(string -> !string.startsWith(LANG))
                    .collect(Collectors.joining(PARAMETERS_DELIMITER));
            request.setAttribute(QUERY_STRING, result);
        }
    }

}
