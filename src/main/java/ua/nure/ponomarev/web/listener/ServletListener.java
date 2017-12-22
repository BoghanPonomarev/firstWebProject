package ua.nure.ponomarev.web.listener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.nure.ponomarev.dao.impl.DbUserDao;
import ua.nure.ponomarev.db.connectionPool.ConnectionPool;
import ua.nure.ponomarev.db.connectionPool.ConnectionPoolImpl;
import ua.nure.ponomarev.db.dataSource.DataSource;
import ua.nure.ponomarev.db.dataSource.DataSourceImpl;
import ua.nure.ponomarev.service.api.UserService;
import ua.nure.ponomarev.service.impl.UserServiceImpl;
import ua.nure.ponomarev.transactions.Transaction;
import ua.nure.ponomarev.web.form.FormMaker;
import ua.nure.ponomarev.web.mail.MailSender;
import ua.nure.ponomarev.web.mail.MailSenderImpl;
import ua.nure.ponomarev.web.validator.RegistrationValidator;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * @author Bogdan_Ponamarev.
 */
@WebListener
public class ServletListener implements ServletContextListener {
    private Logger logger = LogManager.getLogger(ServletListener.class);

    public ServletListener() {
        logger.info("Listener was created");
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("Application is being initialized");
        ServletContext servletContext = sce.getServletContext();
        servletContext.setAttribute("registration_validator", new RegistrationValidator());
        servletContext.setAttribute("form_maker", new FormMaker());
        DataSource dataSource = new DataSourceImpl();
        ConnectionPool connectionPool = ConnectionPoolImpl.getInstance("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/webproject", "Bohdan", "1111", 5);
        dataSource.initConnectionPool(connectionPool);
        servletContext.setAttribute("data_source", dataSource);
        Transaction transaction = new Transaction(dataSource);
        UserService userService = new UserServiceImpl(transaction, new DbUserDao(dataSource));
        servletContext.setAttribute("user_service", userService);
        MailSender mailSender = new MailSenderImpl();
        servletContext.setAttribute("mail_sender", mailSender);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("Application is being destroyed");
    }
}
