package workshop.service;

import workshop.db.Action;
import workshop.db.TransactionManager;
import workshop.db.dao.UserDAO;
import workshop.db.entity.impl.User;
import workshop.exception.DBException;
import workshop.exception.ServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.sql.SQLException;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    private static final String ID = "id";

    @Mock
    private TransactionManager transactionManager;
    @Mock
    private UserDAO userDAO;
    @Mock
    private User user;

    @InjectMocks
    private UserService userService = new UserService(transactionManager, userDAO);

    @Test
    public void shouldInvokeDoingInTransactionMethodWhenAddUser() throws ServiceException, DBException {
        userService.addUser(user);
        verify(transactionManager).doInTransactionWithResult(any(Action.class));
    }

    @Test(expected = ServiceException.class)
    public void shouldThrowServiceExceptionIfTranscationThrowsExceptionWhenAddUser() throws ServiceException, DBException {
        when(transactionManager.doInTransactionWithResult(any())).thenThrow(new DBException("", new SQLException()));
        userService.addUser(user);
    }

    @Test
    public void shouldInvokeDoingInTransactionMethodWhenFindUser() throws ServiceException, DBException {
        userService.findUserByLogin(ID);
        verify(transactionManager).doInTransactionWithResult(any(Action.class));
    }

    @Test(expected = ServiceException.class)
    public void shouldThrowServiceExceptionIfTransactionThrowsExceptionWhenFindUser() throws ServiceException, DBException {
        when(transactionManager.doInTransactionWithResult(any())).thenThrow(new DBException("", new SQLException()));
        userService.findUserByLogin(ID);
    }

}