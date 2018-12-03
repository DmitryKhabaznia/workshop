package workshop.localization.strategy.impl;

import workshop.localization.strategy.LocaleStorageStrategy;
import org.apache.commons.lang3.LocaleUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Locale;

public class CookieStorageStrategy implements LocaleStorageStrategy {

    private int expiringTime;

    public CookieStorageStrategy(int expiringTime) {
        this.expiringTime = expiringTime;
    }

    @Override
    public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale){
        Cookie cookie = new Cookie(LOCALE_PARAMETER_NAME, locale.toString());
        cookie.setMaxAge(expiringTime);
        response.addCookie(cookie);
    }

    @Override
    public Locale getLocale(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        return cookies == null ? null : Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals(LOCALE_PARAMETER_NAME))
                .map(Cookie::getValue)
                .map(LocaleUtils::toLocale)
                .findAny()
                .orElse(null);
    }
}
