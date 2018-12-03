package workshop.captcha;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import workshop.exception.AppException;
import workshop.service.CaptchaService;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CaptchaServletTest {

    private static final BufferedImage BUFFERED_IMAGE = new BufferedImage(300, 200,3);

    @Mock
    private CaptchaService captchaService;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private ServletOutputStream outputStream;
    @Mock
    private Captcha captcha;

    @InjectMocks
    private CaptchaServlet captchaServlet;

    @Test
    public void shouldCallGetCaptchaAndWriteImage() throws AppException, IOException {
        when(captchaService.getCaptcha(request)).thenReturn(captcha);
        when(response.getOutputStream()).thenReturn(outputStream);
        when(captcha.getImage()).thenReturn(BUFFERED_IMAGE);
        captchaServlet.doGet(request, response);
        verify(captchaService).getCaptcha(request);
        verify(outputStream).close();
    }

}