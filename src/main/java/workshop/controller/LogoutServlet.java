package workshop.controller;

import org.apache.log4j.Logger;
import workshop.constants.Constants;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


/**
 * Servlet for logout from the application.
 */
@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(LogoutServlet.class);

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        LOG.debug("Logout servlet starts.");
        String forward = processRequest(req);
        LOG.debug("Forward to path: " + forward);
        resp.sendRedirect(forward);
    }

    /**
     * Processing request by invalidation of session.
     *
     * @param req specified {@link HttpServletRequest}
     */
    private String processRequest(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return Constants.AppContextConstants.INDEX_PAGE;
    }

}
