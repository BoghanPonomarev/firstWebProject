package ua.nure.ponomarev.web.listener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.nure.ponomarev.currency.CurrencyManagerImpl;
import ua.nure.ponomarev.dao.AccountDao;
import ua.nure.ponomarev.dao.impl.CurrencyDaoImpl;
import ua.nure.ponomarev.dao.impl.SqlAccountDao;
import ua.nure.ponomarev.dao.impl.SqlPaymetDao;
import ua.nure.ponomarev.dao.impl.SqlUserDao;
import ua.nure.ponomarev.hash.HashGenerator;
import ua.nure.ponomarev.hash.ShaHashGeneratorImpl;
import ua.nure.ponomarev.sender.EmailSender;
import ua.nure.ponomarev.sender.SmsSender;
import ua.nure.ponomarev.sender.impl.EmailSenderImpl;
import ua.nure.ponomarev.sender.impl.SmsSenderImpl;
import ua.nure.ponomarev.service.UserService;
import ua.nure.ponomarev.service.impl.*;
import ua.nure.ponomarev.transaction.TransactionManager;
import ua.nure.ponomarev.web.form.impl.FormMakerImpl;
import ua.nure.ponomarev.web.validator.AccountValidator;
import ua.nure.ponomarev.web.validator.AuthorizationValidator;
import ua.nure.ponomarev.web.validator.PaymentValidator;
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
    private TransactionManager transactionManager;
    private HashGenerator hash = new ShaHashGeneratorImpl();
    private AccountDao accountDao;

    public ServletListener() {
        logger.info("Application was started");
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("Application is being initialized");
        transactionManager = new TransactionManager(dataSource);
        ServletContext servletContext = sce.getServletContext();
        servletContext.setAttribute("data_source", dataSource);
        currencyServiceInitialized(servletContext);
        formMakerInitialized(servletContext);
        userServiceInitialized(servletContext);
        notificationServiceInitialized(servletContext);
        accountServiceInitialized(servletContext);
        paymentServiceInitialized(servletContext);
        registrationValidatorInitialized(servletContext);

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("Application was destroyed");
    }

    private void paymentServiceInitialized(ServletContext servletContext) {
        servletContext.setAttribute("payment_service", new PaymentServiceImpl(
                transactionManager, new SqlPaymetDao(dataSource), accountDao
                , new CurrencyManagerImpl(new CurrencyDaoImpl(dataSource))
        ));
    }

    private void accountServiceInitialized(ServletContext servletContext) {
        accountDao = new SqlAccountDao(dataSource);
        servletContext.setAttribute("account_service", new AccountServiceImpl(accountDao
                , transactionManager, hash));
    }

    private void userServiceInitialized(ServletContext servletContext) {
        servletContext.setAttribute("hash", hash);
        UserService userService = new UserServiceImpl(transactionManager, new SqlUserDao(dataSource), hash);
        servletContext.setAttribute("user_service", userService);
    }

    private void currencyServiceInitialized(ServletContext servletContext) {
        servletContext.setAttribute("currency_service"
                , new CurrencyServiceImpl(transactionManager,new CurrencyDaoImpl(dataSource)));
    }

    private void notificationServiceInitialized(ServletContext servletContext) {
        EmailSender emailSender = new EmailSenderImpl();
        SmsSender smsSender = new SmsSenderImpl();
        servletContext.setAttribute("notification_service", new NotificationServiceImpl(smsSender, emailSender));
    }

    private void formMakerInitialized(ServletContext servletContext) {
        servletContext.setAttribute("form_maker", new FormMakerImpl());
    }

    private void registrationValidatorInitialized(ServletContext servletContext) {
        servletContext.setAttribute("registration_validator", new RegistrationValidator());
        servletContext.setAttribute("authorization_validator", new AuthorizationValidator());
        servletContext.setAttribute("account_validator", new AccountValidator());
        servletContext.setAttribute("payment_validator", new PaymentValidator());
    }

}
