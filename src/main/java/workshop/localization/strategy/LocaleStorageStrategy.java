package workshop.localization.strategy;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * Interface for strategy of storing locale that defined in web.xml
 */
public interface LocaleStorageStrategy {

    String LOCALE_PARAMETER_NAME = "LOCALE";

    /**
     * Set specified locale in storage that defined by strategy.
     *
     * @param request  {@link HttpServletRequest}
     * @param response {@link HttpServletResponse}
     * @param captcha  specified {@link Locale}
     */
    void setLocale(HttpServletRequest request, HttpServletResponse response, Locale captcha);

    /**
     * Gets {@link Locale} from storage that defined by strategy.
     *
     * @param request {@link HttpServletRequest}
     * @return {@link Locale} from storage
     */
    Locale getLocale(HttpServletRequest request);

}
