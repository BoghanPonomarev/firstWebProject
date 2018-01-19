package ua.nure.ponomarev.sender.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.nure.ponomarev.exception.LogicException;
import ua.nure.ponomarev.exception.SMSSenderException;
import ua.nure.ponomarev.sender.SmsSender;
import java.io.*;

import java.net.URL;
import java.util.Properties;

/**
 * @author Bogdan_Ponamarev.
 */

public class SmsSenderImpl implements SmsSender {
    private static final String SMS_CHARSET = "UTF-8";
    private static Logger logger = LogManager.getLogger(SmsSenderImpl.class);
    private Properties properties;

    public SmsSenderImpl() {
        properties = new Properties();
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            InputStream input = classLoader.getResourceAsStream("Notification.properties");
            properties.load(input);
        } catch (IOException e) {
            logger.fatal("I could not get the settings for working with phone sender", e);
        }
    }


    @Override
    public String sendPinCode(String phoneNumber, String massage) throws SMSSenderException {
        URL url;
        InputStream is = null;
        InputStreamReader reader= null;
        if (!properties.containsKey("mail.login")) {
            logger.error("Could not send mail notification because properties file not found");
            throw new SMSSenderException("Dear user no we can`t send massage to you!\nWe give our apologise...", LogicException.ExceptionType.SERVER_EXCEPTION, new FileNotFoundException());
        }
        try {
            url = new URL(composeUrl(phoneNumber, massage));
            //is = url.openStream();
            if(Boolean.parseBoolean(properties.getProperty("phone.debug"))){
                return "OK";
            }
            StringBuilder answer = new StringBuilder();
            reader = new InputStreamReader(is, SMS_CHARSET);
            int ch;
            while ((ch = reader.read()) != -1) {
                answer.append((char) ch);
            }
            return answer.toString();
        } catch (IOException e) {
            logger.error("Massage was`nt sent " + e);
            throw new SMSSenderException("We give apologise but now we can`t send any sms"
                    , LogicException.ExceptionType.SERVER_EXCEPTION, e);
        } finally {
                try {
                    if(reader!=null) {
                        reader.close();
                    }
                    if (is != null) {
                        is.close();
                    }
                } catch (IOException e) {
                    logger.error("Input stream was`nt closed");
                }
        }
    }

    private String composeUrl(String phoneNumber, String massage) {
        return properties.getProperty("phone.uri") + "?login=" + properties.getProperty("phone.login")
                + "&psw=" + properties.getProperty("phone.password")
                + "&fmt=" + properties.getProperty("phone.fmt")
                + "&phones=" + phoneNumber
                + "&mes=" + massage;
    }

}
