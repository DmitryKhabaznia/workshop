package workshop.filter;

import workshop.bean.SecurityBean;
import workshop.db.entity.impl.User;
import workshop.util.SecurityAccessProvider;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static workshop.constants.Constants.AppContextConstants.*;

public class SecurityFilter implements Filter {

    private static final Logger LOG = Logger.getLogger(SecurityFilter.class);
    private static final String SECURITY_FILE = "securityFile";
    private static final String ERR_MESSAGE = "Access denied";
    private SecurityBean securityBean;
    private SecurityAccessProvider accessProvider;

    @Override
    public void init(FilterConfig filterConfig) {
        LOG.debug("Security filter was initialized.");
        String fileName = filterConfig.getServletContext().getInitParameter(SECURITY_FILE);
        if (fileName != null) {
            securityBean = parseXML(filterConfig.getServletContext().getInitParameter(SECURITY_FILE));
            accessProvider = new SecurityAccessProvider(securityBean.getConstraints());
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        LOG.debug("Security filter starts.");
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        User user = (User) req.getSession().getAttribute(USER_ATTRIBUTE);
        String url = req.getRequestURI();
        if (!accessProvider.isURLInConstraint(url)) {
            chain.doFilter(req, resp);
            return;
        }
        if (user == null) {
            req.getRequestDispatcher(REGISTRATION_SERVLET).forward(req, resp);
            return;
        }
        if (!accessProvider.isUserInRole(req.getRequestURI(), user.getRole())) {
            req.getSession().setAttribute(PREVIOUS_PAGE, url);
            req.getSession().setAttribute(ERROR_MESSAGE_DIV, ERR_MESSAGE);
            resp.sendRedirect(ERROR_PAGE);
            return;
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        LOG.debug("Security filter was destroyed.");
    }

    private SecurityBean parseXML(String fileName) {
        try {
            XmlMapper xmlMapper = new XmlMapper();
            return xmlMapper.readValue(new FileInputStream(new File(fileName)), SecurityBean.class);
        } catch (IOException e) {
            LOG.error("Parsing of xml file was failed.", e);
        }
        return null;
    }

}
