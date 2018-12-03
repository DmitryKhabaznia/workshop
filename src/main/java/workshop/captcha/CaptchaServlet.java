package workshop.captcha;

import workshop.service.CaptchaService;
import workshop.exception.AppException;
import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

import static workshop.constants.Constants.AppContextConstants.CAPTCHA_SERVICE;


/**
 * Servlet that sets captcha into output stream of specified response.
 */
@WebServlet("/captcha")
public class CaptchaServlet extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(CaptchaServlet.class);
    private CaptchaService captchaService;

    /**
     * {@inheritDoc}
     */
    @Override
    public void init() {
        LOG.debug("CaptchaServlet was initialized.");
        captchaService = (CaptchaService) getServletContext().getAttribute(CAPTCHA_SERVICE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {
            LOG.debug("CaptchaServlet was started.");
            Captcha captcha = captchaService.getCaptcha(req);
            OutputStream os = resp.getOutputStream();
            if (captcha != null) {
                ImageIO.write(captcha.getImage(), "png", os);
                os.flush();
                os.close();
            }
        } catch (IOException e) {
            LOG.debug("IOException was occurred", e);
        } catch (AppException e) {
            LOG.debug("Can not generate captcha", e);
        }
    }

}
