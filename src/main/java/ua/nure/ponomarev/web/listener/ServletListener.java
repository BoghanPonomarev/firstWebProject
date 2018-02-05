package ua.nure.ponomarev.web.listener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.nure.ponomarev.currency.CurrencyManager;
import ua.nure.ponomarev.currency.CurrencyManagerImpl;
import ua.nure.ponomarev.dao.AccountDao;
import ua.nure.ponomarev.dao.UserDao;
import ua.nure.ponomarev.dao.impl.*;
import ua.nure.ponomarev.document.ReportGenerator;
import ua.nure.ponomarev.document.ReportGeneratorImpl;
import ua.nure.ponomarev.hash.HashGenerator;
import ua.nure.ponomarev.hash.ShaHashGeneratorImpl;
import ua.nure.ponomarev.holder.RequestedAccountHolderImpl;
import ua.nure.ponomarev.sender.EmailSender;
import ua.nure.ponomarev.sender.SmsSender;
import ua.nure.ponomarev.sender.impl.EmailSenderImpl;
import ua.nure.ponomarev.sender.impl.SmsSenderImpl;
import ua.nure.ponomarev.service.UserService;
import ua.nure.ponomarev.service.impl.*;
import ua.nure.ponomarev.transaction.TransactionManager;
import ua.nure.ponomarev.web.form.impl.FormMakerImpl;
import ua.nure.ponomarev.web.validator.*;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;
import java.io.IOException;

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
    private UserDao userDao;
    private CurrencyManager currencyManager;

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
        localeServiceInitialized(servletContext);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("Application was destroyed");
    }

    private void paymentServiceInitialized(ServletContext servletContext) {
        currencyManager = new CurrencyManagerImpl(new CurrencyDaoImpl(dataSource));
        ReportGenerator reportGenerator=null;
        try {
            reportGenerator = new ReportGeneratorImpl();
        } catch (IOException e) {
            logger.fatal("Could not create report generator "+e);
        }
        servletContext.setAttribute("payment_service", new PaymentServiceImpl(
                transactionManager, new SqlPaymentDao(dataSource, accountDao), accountDao
                , currencyManager,reportGenerator
        ));
    }

    private void accountServiceInitialized(ServletContext servletContext) {
        accountDao = new SqlAccountDao(dataSource);
        servletContext.setAttribute("account_service", new AccountServiceImpl(accountDao
                , transactionManager, hash,currencyManager,new RequestedAccountHolderImpl(accountDao,transactionManager)));
    }

    private void userServiceInitialized(ServletContext servletContext) {
        servletContext.setAttribute("hash", hash);
        userDao =new SqlUserDao(dataSource);
        UserService userService = new UserServiceImpl(transactionManager, userDao, hash);
        servletContext.setAttribute("user_service", userService);
    }

    private void currencyServiceInitialized(ServletContext servletContext) {
        servletContext.setAttribute("currency_service"
                , new CurrencyServiceImpl(transactionManager, new CurrencyDaoImpl(dataSource)));
    }

    private void notificationServiceInitialized(ServletContext servletContext) {
        EmailSender emailSender = new EmailSenderImpl();
        SmsSender smsSender = new SmsSenderImpl();
        servletContext.setAttribute("notification_service", new NotificationServiceImpl(smsSender, emailSender));
    }
    private void localeServiceInitialized(ServletContext servletContext) {
        servletContext.setAttribute("locale_service"
                , new LocaleServiceImpl(transactionManager,new LocaleDaoImpl(dataSource)));
    }

    private void formMakerInitialized(ServletContext servletContext) {
        servletContext.setAttribute("form_maker", new FormMakerImpl());
    }

    private void registrationValidatorInitialized(ServletContext servletContext) {
        servletContext.setAttribute("registration_validator", new RegistrationValidator());
        servletContext.setAttribute("authorization_validator", new AuthorizationValidator());
        servletContext.setAttribute("account_validator", new AccountValidator());
        servletContext.setAttribute("payment_validator", new PaymentValidator());
        servletContext.setAttribute("replenish_validator", new ReplenishValidator());
    }

}
