package ua.nure.ponomarev.web.page;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author Bogdan_Ponamarev.
 */
public final class Mapping {
    private static final Map<Page, String> pages = new HashMap<>();
    private static final Map<String, Class> commands = new HashMap<>();
    private static Logger logger = LogManager.getLogger(Mapping.class);

    static {
        Properties properties = new Properties();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try (InputStream input = classLoader.getResourceAsStream("Mapping.properties")) {
            properties.load(input);
        } catch (IOException e) {
            logger.fatal("Can`t load properties file for mapping " + e);
        }
        for (String key : properties.stringPropertyNames()) {
            try {
                commands.put(key, Class.forName(properties.getProperty(key)));
            } catch (ClassNotFoundException e) {
                logger.error("One of commands is not found " + e);
            }
        }
        pages.put(Page.ADMIN_SHOW_USERS, "/WEB-INF/jsp/users.jsp");
        pages.put(Page.AUTHORIZATION_PAGE, "/WEB-INF/jsp/authorization.jsp");
        pages.put(Page.REGISTRATION_PAGE, "/WEB-INF/jsp/registration.jsp");
        pages.put(Page.USER_PROFILE_PAGE, "/WEB-INF/jsp/profile.jsp");
        pages.put(Page.USER_SETTING_PAGE, "/WEB-INF/jsp/userSettings.jsp");
        pages.put(Page.USER_SETTING_CONGRATULATION_PAGE, "/WEB-INF/jsp/congratulation_about_changing_data.jsp");
        pages.put(Page.USER_SETTING_ERROR_PAGE, "/WEB-INF/jsp/changing_data_error.jsp");
        pages.put(Page.USER_ADD_ACCOUNT_PAGE, "/WEB-INF/jsp/addAccountForm.jsp");
        pages.put(Page.PAYMENT_FORM_PAGE,"/WEB-INF/jsp/addPaymentForm.jsp");
        pages.put(Page.PAYMENT_SUCCESSFUL_PAGE,"/WEB-INF/jsp/successfulPaymentPreparing.jsp");
        pages.put(Page.PAYMENT_DELETING_PAGE,"/WEB-INF/jsp/deletingPayment.jsp");
        pages.put(Page.PAYMENT_EXECUTING_PAGE,"/WEB-INF/jsp/executePayment.jsp");
        pages.put(Page.PAYMENT_SHOW_ALL,"/WEB-INF/jsp/payments.jsp");
        pages.put(Page.REPLENISH_FORM_PAGE,"/WEB-INF/jsp/replenishForm.jsp");
        pages.put(Page.REPLENISH_SUCCESS_PAGE,"/WEB-INF/jsp/successfulReplenish.jsp");
        pages.put(Page.ADMIN_REQUESTED_ACCOUNTS_PAGE,"/WEB-INF/jsp/requestedAccounts.jsp");
        pages.put(Page.USER_SUCCESS_EMAIL_SENDING_PAGE,"/WEB-INF/jsp/successEmailSending.jsp");
        pages.put(Page.USER_SUCCESS_EMAIL_CONFIRM_PAGE,"/WEB-INF/jsp/successEmailConfirm.jsp");
    }

    public static Class getCommand(String command) {
        return commands.get(command);
    }

    public static String getPagePath(Page page) {
        return pages.get(page);
    }

    public static void main(String[] args) {
        InputStream input;
        Properties prop = new Properties();
        try {
            input = new FileInputStream("src/main/resources/mapping/Mapping.properties");
            prop.load(input);
        } catch (IOException e) {
            logger.fatal("Can`t load properties file for mapping " + e);
        }
    }

    public enum Page {
        ADMIN_SHOW_USERS,
        ADMIN_REQUESTED_ACCOUNTS_PAGE,
        AUTHORIZATION_PAGE,
        REGISTRATION_PAGE,
        USER_ADD_ACCOUNT_PAGE,
        USER_PROFILE_PAGE,
        USER_SETTING_PAGE,
        USER_SETTING_CONGRATULATION_PAGE,
        USER_SETTING_ERROR_PAGE,
        USER_SUCCESS_EMAIL_SENDING_PAGE,
        USER_SUCCESS_EMAIL_CONFIRM_PAGE,
        PAYMENT_FORM_PAGE,
        PAYMENT_SUCCESSFUL_PAGE,
        PAYMENT_DELETING_PAGE,
        PAYMENT_EXECUTING_PAGE,
        PAYMENT_SHOW_ALL,
        REPLENISH_FORM_PAGE,
        REPLENISH_SUCCESS_PAGE
    }
}
