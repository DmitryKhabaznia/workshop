package workshop.service;

import workshop.db.entity.impl.User;
import workshop.exception.ServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AvatarServiceTest {

    private static final String IMAGE_DIRECTORY_PATH = "/images/default_avatar";
    private static final String IMAGE_EXTENSION = ".jpeg";
    private static final String LOGIN = "login";
    private static final String IMAGE_NAME = "imageName";
    private static final String REAL_PATH = "realPath";

    @Mock
    private Part part;
    @Mock
    private User user;
    @Mock
    private HttpServletRequest request;
    @Mock
    private ServletContext servletContext;

    @InjectMocks
    private AvatarService avatarService = new AvatarService(IMAGE_DIRECTORY_PATH);

    @Test
    public void shouldInvokeMethodWriteOnPart() throws ServiceException, IOException {
        when(user.getLogin()).thenReturn(LOGIN);
        when(part.getSize()).thenReturn(1l);
        avatarService.setAvatar(part, user);
        verify(part).write(IMAGE_DIRECTORY_PATH + File.separator + LOGIN + IMAGE_EXTENSION);
    }

    @Test
    public void shouldReturnsDefaultAvatarPath() {
        when(request.getServletContext()).thenReturn(servletContext);
        when(servletContext.getRealPath(IMAGE_DIRECTORY_PATH + IMAGE_EXTENSION)).thenReturn(REAL_PATH);
        assertEquals(new File(REAL_PATH).toPath(), avatarService.getAvatarPath(request, IMAGE_NAME));
    }

}