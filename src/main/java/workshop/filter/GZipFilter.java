package workshop.filter;

import workshop.gzip.GZipResponseWrapper;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GZipFilter implements Filter {

    private static final Logger LOG = Logger.getLogger(GZipFilter.class);
    private static final String ACCEPT_ENCODING_HEADER = "accept-encoding";
    private static final String GZIP_NAME = "gzip";

    /**
     * {@inheritDoc}
     */
    @Override
    public void init(FilterConfig filterConfig) {
        LOG.debug("gzip filter was initialized.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        String acceptEncodingHeader = request.getHeader(ACCEPT_ENCODING_HEADER);
        if (acceptEncodingHeader != null && acceptEncodingHeader.contains(GZIP_NAME)) {
            GZipResponseWrapper wrappedResponse = new GZipResponseWrapper(response);
            chain.doFilter(req, wrappedResponse);
            wrappedResponse.closeWriter();
            return;
        }
        chain.doFilter(req, res);
    }

    /**
     * {@inheritDoc}
     */
    public void destroy() {
        LOG.debug("gzip filter was destroyed.");
    }

}

