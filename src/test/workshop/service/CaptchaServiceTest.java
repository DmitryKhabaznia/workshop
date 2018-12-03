package workshop.service;

import workshop.captcha.Captcha;
import workshop.captcha.strategy.CaptchaStorageStrategy;
import workshop.exception.AppException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CaptchaServiceTest {

    private static final int TIMEOUT = 10;

    @Mock
    private CaptchaStorageStrategy captchaStorageStrategy;
    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private CaptchaService captchaService = new CaptchaService(captchaStorageStrategy, TIMEOUT);

    @Test
    public void shouldInvokeGeCaptchaMethodOnStrategy() throws AppException {
        captchaService.getCaptcha(request);
        verify(captchaStorageStrategy).getCaptcha(request);
    }

    @Test
    public void shouldSetCaptchaInCaptchaStrategy() throws Exception {
        captchaService.createCaptcha(request, response);
        verify(captchaStorageStrategy).setCaptcha(any(request.getClass()), any(response.getClass()), any(Captcha.class));
    }

}