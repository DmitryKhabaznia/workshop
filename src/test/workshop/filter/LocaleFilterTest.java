package workshop.filter;

import workshop.localization.strategy.LocaleStorageStrategy;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.*;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LocaleFilterTest {

    private static final String DEFAULT_LOCALE = "ru";
    private static final String DEFAULT_LOCALES = "en,ru";
    private static final String LOCALES_PARAM_NAME = "locales";
    private static final String DEFAULT_LOCALE_PARAM_NAME = "defaultLocale";
    private static final String COOKIE_EXPIRING_TIME = "cookieExpiringTime";
    private static final int TIME = 3500;

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private FilterConfig filterConfig;
    @Mock
    private ServletContext servletContext;
    @Mock
    private LocaleStorageStrategy localeStorageStrategy;
    @Mock
    private FilterChain filterChain;

    @InjectMocks
    private LocaleFilter localeFilter = new LocaleFilter();

    @Before
    public void setUp() {
        when(filterConfig.getInitParameter(DEFAULT_LOCALE_PARAM_NAME)).thenReturn(DEFAULT_LOCALE);
        when(filterConfig.getInitParameter(LOCALES_PARAM_NAME)).thenReturn(DEFAULT_LOCALES);
        when(filterConfig.getServletContext()).thenReturn(servletContext);
        when(servletContext.getInitParameter(COOKIE_EXPIRING_TIME)).thenReturn(String.valueOf(TIME));
        localeFilter.init(filterConfig);
        MockitoAnnotations.initMocks(this);

    }

    @Test
    public void shouldSetLocaleFromRequestParameter() throws IOException, ServletException {
        when(request.getParameter("lang")).thenReturn("ru");
        localeFilter.doFilter(request, response, filterChain);
        verify(request, times(1)).getParameter("lang");
        verify(localeStorageStrategy, times(0)).getLocale(request);
        verify(request, times(0)).getLocales();
        filterChain.doFilter(request, response);
    }

    @Test
    public void shouldSetLocaleFromStorageStrategy() throws IOException, ServletException {
        when(localeStorageStrategy.getLocale(request)).thenReturn(new Locale("ru"));
        localeFilter.doFilter(request, response, filterChain);
        verify(request, times(1)).getParameter("lang");
        verify(localeStorageStrategy, times(1)).getLocale(request);
        verify(request, times(0)).getLocales();
        filterChain.doFilter(request, response);
    }

    @Test
    public void shouldSetLocaleFromBrowserSuggestions() throws IOException, ServletException {
        List<Locale> locales = new ArrayList<>();
        locales.add(new Locale("ru"));
        locales.add(new Locale("fr"));
        when(request.getLocales()).thenReturn(Collections.enumeration(locales));
        localeFilter.doFilter(request, response, filterChain);
        verify(request, times(1)).getParameter("lang");
        verify(localeStorageStrategy, times(1)).getLocale(request);
        verify(request, times(1)).getLocales();
        filterChain.doFilter(request, response);
    }

    @Test
    public void shouldSetDefaultLocale() throws IOException, ServletException {
        List<Locale> locales = new ArrayList<>();
        locales.add(new Locale("kz"));
        when(request.getLocales()).thenReturn(Collections.enumeration(locales));
        localeFilter.doFilter(request, response, filterChain);
        verify(request, times(1)).getParameter("lang");
        verify(localeStorageStrategy, times(1)).getLocale(request);
        verify(request, times(1)).getLocales();
        filterChain.doFilter(request, response);
    }

}