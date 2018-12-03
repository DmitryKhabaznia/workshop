package workshop.filter;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Filter that disable cache.
 */
public class NoCacheFilter implements Filter {

    private static final Logger LOG = Logger.getLogger(NoCacheFilter.class);
    private static final String CACHE_CONTROL_HEADER_NAME = "Cache-Control";
    private static final String NO_CACHE_HEADER_VALUE = "no-cache, no-store, must-revalidate";
    private static final String PRAGMA_HEADER_NAME = "Pragma";
    private static final String NO_CACHE_PRAGMA_HEADER_VALUE = "no-cache";
    private static final String EXPIRATION_HEADER = "Expires";
    private static final int EXPIRATION_HEADER_VALUE = 0;

    /**
     * {@inheritDoc}
     */
    @Override
    public void init(FilterConfig filterConfig) {
        LOG.debug("No cache filter was initialized.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        resp.setHeader(CACHE_CONTROL_HEADER_NAME, NO_CACHE_HEADER_VALUE);
        resp.setHeader(PRAGMA_HEADER_NAME, NO_CACHE_PRAGMA_HEADER_VALUE);
        resp.setDateHeader(EXPIRATION_HEADER, EXPIRATION_HEADER_VALUE);
        chain.doFilter(req, resp);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void destroy() {
        LOG.debug("No cache filter was destroyed.");
    }

}
