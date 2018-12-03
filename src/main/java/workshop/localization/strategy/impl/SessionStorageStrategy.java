package workshop.localization.strategy.impl;

import workshop.localization.strategy.LocaleStorageStrategy;
import org.apache.commons.lang3.LocaleUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

public class SessionStorageStrategy implements LocaleStorageStrategy {

    @Override
    public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {
        request.getSession().setAttribute(LOCALE_PARAMETER_NAME, locale.toString());
    }

    @Override
    public Locale getLocale(HttpServletRequest request) {
        Object localeName = request.getSession().getAttribute(LOCALE_PARAMETER_NAME);
        return localeName == null ? null : LocaleUtils.toLocale((String) localeName);
    }

}
