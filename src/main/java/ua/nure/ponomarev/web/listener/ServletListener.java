package ua.nure.ponomarev.web.listener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.nure.ponomarev.dao.impl.SqlUserDao;
import ua.nure.ponomarev.encoder.impl.AESEncoder;
import ua.nure.ponomarev.sender.SmsSender;
import ua.nure.ponomarev.sender.impl.SmsSenderImpl;
import ua.nure.ponomarev.service.UserService;
import ua.nure.ponomarev.service.impl.NotificationServiceImpl;
import ua.nure.ponomarev.service.impl.UserServiceImpl;
import ua.nure.ponomarev.transaction.TransactionManager;
import ua.nure.ponomarev.web.form.FormMakerImpl;
import ua.nure.ponomarev.sender.EmailSender;
import ua.nure.ponomarev.sender.impl.EmailSenderImpl;
import ua.nure.ponomarev.web.validator.AuthorizationValidator;
import ua.nure.ponomarev.web.validator.RegistrationValidator;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;

/**
 * @author Bogdan_Ponamarev.
 */
@WebListener
public class ServletListener implements ServletContextListener {
    private Logger logger = LogManager.getLogger(ServletListener.class);
    @Resource(name = "jdbc/data_source")
    private DataSource dataSource;


    public ServletListener() {
        logger.info("Application was started");
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("Application is being initialized");
        ServletContext servletContext = sce.getServletContext();
        servletContext.setAttribute("data_source", dataSource);
        formMakerInitialized(servletContext);
        userServiceInitialized(servletContext);
        notificatorInitialized(servletContext);
        registrationValidatorInitialized(servletContext);
        encoderInitialized(servletContext);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("Application was destroyed");
    }

    private void userServiceInitialized(ServletContext servletContext) {
        TransactionManager transactionManager = new TransactionManager(dataSource);
        UserService userService = new UserServiceImpl(transactionManager, new SqlUserDao(dataSource));
        servletContext.setAttribute("user_service", userService);
    }

    private void notificatorInitialized(ServletContext servletContext) {
        EmailSender emailSender = new EmailSenderImpl();
        SmsSender smsSender = new SmsSenderImpl();
        servletContext.setAttribute("notification_service", new NotificationServiceImpl(smsSender,emailSender));
    }

    private void formMakerInitialized(ServletContext servletContext) {
        servletContext.setAttribute("form_maker", new FormMakerImpl());
    }

    private void registrationValidatorInitialized(ServletContext servletContext) {
        servletContext.setAttribute("registration_validator", new RegistrationValidator());
        servletContext.setAttribute("authorization_validator", new AuthorizationValidator());
    }
    private void encoderInitialized(ServletContext servletContext){
        try {
            servletContext.setAttribute("encoder",new AESEncoder());
        } catch (Exception e) {
            logger.fatal("Encoder does not initialized " + e);
        }
    }
}
