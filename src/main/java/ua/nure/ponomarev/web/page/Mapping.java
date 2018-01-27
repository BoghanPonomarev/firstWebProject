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
        pages.put(Page.ADMIN_PAGE, "/jsp/show_users.jsp");
        pages.put(Page.AUTHORIZATION_PAGE, "/jsp/authorization.jsp");
        pages.put(Page.REGISTRATION_PAGE, "/jsp/registration.jsp");
        pages.put(Page.USER_PROFILE_PAGE, "/jsp/profile.jsp");
        pages.put(Page.USER_SETTING_PAGE, "/jsp/user_settings.jsp");
        pages.put(Page.USER_SETTING_CONGRATULATION_PAGE, "/jsp/congratulation_about_changing_data.jsp");
        pages.put(Page.USER_SETTING_ERROR_PAGE, "/jsp/changing_data_error.jsp");
        pages.put(Page.USER_ADD_ACCOUNT_PAGE, "/jsp/add_account.jsp");
        pages.put(Page.PAYMENT_ADD_PAGE,"/jsp/add_payment.jsp");
        pages.put(Page.PAYMENT_SUCCESSFUL_PAGE,"/jsp/successful_payment_preparing.jsp");
        pages.put(Page.PAYMENT_SUCCESSFUL_DELETE_PAGE,"/jsp/successes_deleting_payment.jsp");
        pages.put(Page.PAYMENT_EXECUTING_PAGE,"/jsp/execute_payment.jsp");


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
        ADMIN_PAGE,
        AUTHORIZATION_PAGE,
        REGISTRATION_PAGE,
        USER_ADD_ACCOUNT_PAGE,
        USER_PROFILE_PAGE,
        USER_SETTING_PAGE,
        USER_SETTING_CONGRATULATION_PAGE,
        USER_SETTING_ERROR_PAGE,
        PAYMENT_ADD_PAGE,
        PAYMENT_SUCCESSFUL_PAGE,
        PAYMENT_SUCCESSFUL_DELETE_PAGE,
        PAYMENT_EXECUTING_PAGE
    }
}
