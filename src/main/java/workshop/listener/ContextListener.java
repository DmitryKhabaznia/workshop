package workshop.listener;

import workshop.db.ConnectionHolder;
import workshop.db.ConnectionManager;
import workshop.db.TransactionManager;
import workshop.db.dao.OrderDAO;
import workshop.db.dao.ProductDAO;
import workshop.db.dao.UserDAO;
import workshop.db.dao.impl.OrderDAOImpl;
import workshop.db.dao.impl.ProductDAOImpl;
import workshop.db.dao.impl.UserDAOImpl;
import workshop.exception.DBException;
import workshop.captcha.strategy.CaptchaStorageStrategy;
import workshop.captcha.strategy.CaptchaStorageStrategyContainer;
import workshop.util.converter.impl.UserConverter;
import workshop.util.validator.RegisterFormBeanValidator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import workshop.constants.Constants;
import workshop.service.*;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import java.io.File;


/**
 * Listener for app.
 */
@WebListener
public class ContextListener implements ServletContextListener {

    private static final Logger LOG = Logger.getLogger(ContextListener.class);
    private static final String STORAGE_STRATEGY = "captchaStoringStrategy";
    private static final String TIMEOUT = "timeout";
    private static final String IMAGE_DIRECTORY_PATH = "imageDirectoryPath";

    /**
     * {@inheritDoc}
     */
    @Override
    public void contextInitialized(ServletContextEvent event) {
        ServletContext servletContext = event.getServletContext();
        initLog4J(servletContext);
        try {
            initAppContext(servletContext);
        } catch (DBException e) {
            LOG.error("Can not initialize app context.", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }

    private void initLog4J(ServletContext servletContext) {
        PropertyConfigurator.configure(servletContext.getRealPath("WEB-INF/log4j.properties"));
        LOG.debug("Log4j has been initialized");
    }

    private void initAppContext(ServletContext servletContext) throws DBException {
        LOG.debug("Initializing services.");
        ConnectionHolder connectionHolder = new ConnectionHolder(new ConnectionManager());
        TransactionManager transactionManager = new TransactionManager(connectionHolder);
        initAvatarService(servletContext);
        initCaptchaService(servletContext);
        initUserService(servletContext, connectionHolder, transactionManager);
        initProductService(servletContext, connectionHolder, transactionManager);
        initOrderService(servletContext, connectionHolder, transactionManager);
        servletContext.setAttribute(Constants.AppContextConstants.REGISTER_FORM_BEAN_VALIDATOR, new RegisterFormBeanValidator());
        servletContext.setAttribute(Constants.AppContextConstants.USER_CONVERTER, new UserConverter());
        LOG.debug("Services were initialized.");
    }

    private void initUserService(ServletContext servletContext, ConnectionHolder connectionHolder, TransactionManager transactionManager) {
        UserDAO userDAO = new UserDAOImpl(connectionHolder);
        UserService userService = new UserService(transactionManager, userDAO);
        servletContext.setAttribute(Constants.AppContextConstants.USER_SERVICE, userService);
        LOG.debug("User service was initialized");
    }

    private void initCaptchaService(ServletContext servletContext) {
        int timeout = Integer.parseInt(servletContext.getInitParameter(TIMEOUT));
        String strategy = servletContext.getInitParameter(STORAGE_STRATEGY);
        CaptchaStorageStrategyContainer captchaStorageStrategyContainer = new CaptchaStorageStrategyContainer(timeout);
        CaptchaStorageStrategy captchaStorageStrategy = captchaStorageStrategyContainer.getStrategy(strategy);
        LOG.debug("Storage service was chosen: -> " + captchaStorageStrategy.getClass().getName());
        CaptchaService captchaService = new CaptchaService(captchaStorageStrategy, timeout);
        servletContext.setAttribute(Constants.AppContextConstants.CAPTCHA_SERVICE, captchaService);
        LOG.debug("Captcha service was initialized");
    }

    private void initAvatarService(ServletContext servletContext) {
        String imageDirectoryPath = servletContext.getInitParameter(IMAGE_DIRECTORY_PATH);
        File imageDirectory = new File(new File(servletContext.getRealPath(File.separator)).getParent() + imageDirectoryPath);
        if (!imageDirectory.exists()) {
            imageDirectory.mkdir();
        }
        AvatarService avatarService = new AvatarService(imageDirectory.getAbsolutePath() + File.separator);
        servletContext.setAttribute(Constants.AppContextConstants.AVATAR_SERVICE, avatarService);
        LOG.debug("Avatar service was initialized");
    }

    private void initProductService(ServletContext servletContext, ConnectionHolder connectionHolder, TransactionManager transactionManager) {
        ProductDAO productDAO = new ProductDAOImpl(connectionHolder);
        ProductService productService = new ProductService(transactionManager, productDAO);
        servletContext.setAttribute(Constants.AppContextConstants.PRODUCT_SERVICE, productService);
        LOG.debug("Product service was initialized.");
    }

    private void initOrderService(ServletContext servletContext, ConnectionHolder connectionHolder, TransactionManager transactionManager) {
        OrderDAO orderDAO = new OrderDAOImpl(connectionHolder);
        OrderService orderService = new OrderService(transactionManager, orderDAO);
        servletContext.setAttribute(Constants.AppContextConstants.ORDER_SERVICE, orderService);
        LOG.debug("OrderFormBean service was initialized.");
    }

}
