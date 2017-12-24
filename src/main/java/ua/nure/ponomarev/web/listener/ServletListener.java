package ua.nure.ponomarev.web.listener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.nure.ponomarev.dao.impl.DbUserDao;
import ua.nure.ponomarev.service.api.UserService;
import ua.nure.ponomarev.service.impl.UserServiceImpl;
import ua.nure.ponomarev.transactions.Transaction;
import ua.nure.ponomarev.web.form.FormMaker;
import ua.nure.ponomarev.web.mail.MailSender;
import ua.nure.ponomarev.web.mail.MailSenderImpl;
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
    private Transaction transaction;
    private UserService userService;
    private MailSender mailSender;
    private FormMaker formMaker;
    private RegistrationValidator registrationValidator;

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
        mailSenderInitialized(servletContext);
        registrationValidatorInitialized(servletContext);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("Application was destroyed");
    }

    private void userServiceInitialized(ServletContext servletContext) {
        transaction = new Transaction(dataSource);
        userService = new UserServiceImpl(transaction, new DbUserDao(dataSource));
        servletContext.setAttribute("user_service", userService);
    }

    private void mailSenderInitialized(ServletContext servletContext) {
        mailSender = new MailSenderImpl();
        servletContext.setAttribute("mail_sender", mailSender);
    }

    private void formMakerInitialized(ServletContext servletContext) {
        formMaker = new FormMaker();
        servletContext.setAttribute("form_maker", formMaker);
    }

    private void registrationValidatorInitialized(ServletContext servletContext) {
        registrationValidator = new RegistrationValidator();
        servletContext.setAttribute("registration_validator", registrationValidator);
    }
}
