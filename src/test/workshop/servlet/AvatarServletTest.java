package workshop.servlet;

import workshop.controller.AvatarServlet;
import workshop.db.entity.impl.User;
import workshop.service.AvatarService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedOutputStream;

import static workshop.constants.Constants.AppContextConstants.AVATAR_SERVICE;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AvatarServletTest {

    @Mock
    private AvatarService avatarService;
    @Mock
    private HttpSession session;
    @Mock
    private HttpServletRequest request;
    @Mock
    private User user;
    @Mock
    private HttpServletResponse response;
    @Mock
    private ServletConfig servletConfig;
    @Mock
    private ServletContext servletContext;
    @Mock
    private BufferedOutputStream bos;

    @InjectMocks
    AvatarServlet avatarServlet;

    @Test
    public void initTest() {
        when(servletConfig.getServletContext()).thenReturn(servletContext);
        avatarServlet.init(servletConfig);
        verify(servletConfig).getServletContext();
        verify(servletContext).getAttribute(AVATAR_SERVICE);
    }

}