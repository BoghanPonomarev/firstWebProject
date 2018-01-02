package ua.nure.ponomarev.notification.phone_notification;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.nure.ponomarev.notification.Token;
import ua.nure.ponomarev.notification.TokenSet;

import java.io.*;

import java.net.URL;
import java.util.Properties;
import java.util.Set;

/**
 * @author Bogdan_Ponamarev.
 */

public class PhoneNotificatorImpl implements PhoneNotificator {
    private Logger logger = LogManager.getLogger(PhoneNotificatorImpl.class);
    private Properties properties;
    private Set<Token> tokens;
    private boolean isValidProperties = true;
    public PhoneNotificatorImpl() {
        tokens = new TokenSet(2);
        properties = new Properties();
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            InputStream input = classLoader.getResourceAsStream("Notification.properties");
            properties.load(input);
        } catch (IOException e) {
            isValidProperties = false;
            logger.fatal("I could not get the settings for working with phone notification", e);
        }
    }


    @Override
    public void sendPinCode(String phoneNumber) {
        URL url;
        InputStream is=null;
        try {
            int pinCode = (int)(Math.random()*89999+10000);
            tokens.add(new Token(phoneNumber,3,pinCode));
            url = new URL(composeUrl(phoneNumber, "Your pin code: " + pinCode));
            is = url.openStream();
        } catch (IOException e) {
            logger.error("Massage was`nt sent "+e);
        }
        finally {
            if(is!=null){
                try {
                    is.close();
                } catch (IOException e) {
                    logger.error("Input stream was`nt closed");
                }
            }
        }
    }

    private String composeUrl(String phoneNumber, String massage) {
        if (!isValidProperties) {
            return "";
        }
        return properties.getProperty("phone.domen") + "?login=" + properties.getProperty("phone.login")
                + "&psw=" + properties.getProperty("phone.password")
                + "&phones=" + phoneNumber
                + "&mes=" + massage;
    }
    @Override
    public boolean isValidPinCode(int pinCode,String phoneNumber) {
        for (Token token: tokens) {
            if(token.getData()==pinCode&&phoneNumber.equals(token.getIdentificationName())){
                return true;
            }
        }
        return false;
    }

    @Override
    public void removePinCode(String phoneNumber) {
        for (Token token: tokens) {
            if(phoneNumber.equals(token.getIdentificationName())){
                tokens.remove(token);
            }
        }
    }
}
