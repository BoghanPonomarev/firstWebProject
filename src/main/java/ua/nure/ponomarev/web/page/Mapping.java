package ua.nure.ponomarev.web.page;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.*;

/**
 * @author Bogdan_Ponamarev.
 */
public final class Mapping {
    private static Logger logger = LogManager.getLogger(Mapping.class);
    private static final Map<Page,String> pages=new HashMap<>();
    private static final Map<String,Class> commands = new HashMap<>();
  static{
        Properties properties = new Properties();
      ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
      try ( InputStream input = classLoader.getResourceAsStream("Mapping.properties")){
            properties.load(input);
        } catch (IOException e) {
           logger.fatal("Can`t load properties file for mapping "+e);
        }
      for (String key:properties.stringPropertyNames()) {
            try {
                commands.put(key,Class.forName(properties.getProperty(key)));
            } catch (ClassNotFoundException e) {
                logger.error("One of commands is not found "+e);
            }
        }
       pages.put(Page.DEBUG_MAPPING_PAGE,"/html/debug_mapping.html");
        pages.put(Page.ACCOUNTS_PAGE,"/jsp/accounts.jsp");
        pages.put(Page.ADMIN_PAGE,"/jsp/admin_show_users.jsp");
        pages.put(Page.AUTHORIZATION_PAGE,"/jsp/authorization.jsp");
        pages.put(Page.PHONE_CONFIRM_PAGE,"/jsp/phone_confirm.jsp");
        pages.put(Page.REGISTRATION_PAGE,"/jsp/registration.jsp");
        pages.put(Page.PROFILE_PAGE,"/jsp/profile.jsp");
    }
    public enum Page {
        DEBUG_MAPPING_PAGE,
        ACCOUNTS_PAGE,
        ADMIN_PAGE,
        AUTHORIZATION_PAGE,
        PHONE_CONFIRM_PAGE,
        REGISTRATION_PAGE,
        PROFILE_PAGE
    }
    public static Class getCommand(String command){
        return commands.get(command);
    }
    public static String getPagePath(Page page){
        return pages.get(page);
    }

    public static void main(String[] args) {
        InputStream input;
        Properties prop = new Properties();
        try {
            input = new FileInputStream("src/main/resources/mapping/Mapping.properties");
            prop.load(input);
        } catch (IOException e) {
            logger.fatal("Can`t load properties file for mapping "+e);
        }
    }
}
