package workshop.controller;

import workshop.db.entity.impl.User;
import workshop.service.AvatarService;
import org.apache.log4j.Logger;
import workshop.constants.Constants;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;


/**
 * Servlet that sets avatar.
 */
@WebServlet("/userAvatar")
public class AvatarServlet extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(AvatarService.class);
    private AvatarService avatarService;

    /**
     * {@inheritDoc}
     */
    @Override
    public void init(ServletConfig config) {
        ServletContext servletContext = config.getServletContext();
        avatarService = (AvatarService) servletContext.getAttribute(Constants.AppContextConstants.AVATAR_SERVICE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) {
        try (BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream())) {
            LOG.debug("AvatarServlet was started.");
            User user = (User) request.getSession().getAttribute(Constants.AppContextConstants.USER_ATTRIBUTE);
            bos.write(Files.readAllBytes(avatarService.getAvatarPath(request, user.getLogin())));
            bos.flush();
        } catch (IOException ex) {
            LOG.error("IOException was occurred", ex);
        }
    }

}
