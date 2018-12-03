package workshop.filter;

import workshop.db.entity.impl.User;
import workshop.util.SecurityAccessProvider;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static workshop.constants.Constants.AppContextConstants.ERROR_PAGE;
import static workshop.constants.Constants.AppContextConstants.REGISTRATION_SERVLET;
import static workshop.constants.Constants.AppContextConstants.USER_ATTRIBUTE;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SecurityFilterTest {

    private static final String URL = "/shop/index.jsp";
    private static final String ROLE = "role";
    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpServletRequest request;
    @Mock
    private FilterChain filterChain;
    @Mock
    private SecurityAccessProvider accessProvider;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private HttpSession httpSession;
    @Mock
    private User user;

    @InjectMocks
    private SecurityFilter securityFilter;

    @Before
    public void setUp() {
        when(request.getRequestURI()).thenReturn(URL);
        when(request.getSession()).thenReturn(httpSession);
        when(user.getRole()).thenReturn(ROLE);
    }

    @Test
    public void shouldCheckIfUrlInConstraints() throws IOException, ServletException {
        when(accessProvider.isURLInConstraint(URL)).thenReturn(false);
        securityFilter.doFilter(request, response, filterChain);
        verify(accessProvider).isURLInConstraint(URL);
        verify(accessProvider, never()).isUserInRole(anyString(), anyString());
        verify(filterChain).doFilter(any(HttpServletRequest.class), any(HttpServletResponse.class));
    }

    @Test
    public void shouldCheckIfUserIsLogined() throws IOException, ServletException {
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(accessProvider.isURLInConstraint(URL)).thenReturn(true);
        securityFilter.doFilter(request, response, filterChain);
        verify(accessProvider).isURLInConstraint(URL);
        verify(accessProvider, never()).isUserInRole(URL, ROLE);
        verify(request).getRequestDispatcher(REGISTRATION_SERVLET);
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void shouldCheckIfUserInRole() throws IOException, ServletException {
        when(request.getSession()).thenReturn(httpSession);
        when(httpSession.getAttribute(USER_ATTRIBUTE)).thenReturn(user);
        when(request.getRequestURI()).thenReturn(URL);
        when(accessProvider.isURLInConstraint(URL)).thenReturn(true);
        when(accessProvider.isUserInRole(URL, ROLE)).thenReturn(false);
        securityFilter.doFilter(request, response, filterChain);
        verify(accessProvider).isURLInConstraint(URL);
        verify(accessProvider).isUserInRole(URL, ROLE);
        verify(response).sendRedirect(ERROR_PAGE);
    }


    @Test
    public void shouldCheckAllAndDoFilter() throws IOException, ServletException {
        when(httpSession.getAttribute(USER_ATTRIBUTE)).thenReturn(user);
        when(request.getRequestURI()).thenReturn(URL);
        when(accessProvider.isURLInConstraint(URL)).thenReturn(true);
        when(accessProvider.isUserInRole(URL, ROLE)).thenReturn(true);
        securityFilter.doFilter(request, response, filterChain);
        verify(accessProvider).isURLInConstraint(URL);
        verify(accessProvider).isUserInRole(URL, ROLE);
        verify(filterChain).doFilter(any(HttpServletRequest.class), any(HttpServletResponse.class));
    }

}