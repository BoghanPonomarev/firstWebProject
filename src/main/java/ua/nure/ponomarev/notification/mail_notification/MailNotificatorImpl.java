package ua.nure.ponomarev.notification.mail_notification;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.nure.ponomarev.notification.Token;
import ua.nure.ponomarev.notification.TokenSet;
import ua.nure.ponomarev.web.exception.LogicException;
import ua.nure.ponomarev.web.exception.MailException;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;
import  javax.mail.Transport;

/**
 * @author Bogdan_Ponamarev.
 */
public class MailNotificatorImpl implements MailNotificator {
    private Logger logger = LogManager.getLogger(MailNotificatorImpl.class);
    private Set<Token> tokens;
    private Properties properties;
    private static final String END_LINK ="'>confirm registration</a>";
    public MailNotificatorImpl()  {
        tokens = new TokenSet(10);
        properties = new Properties();
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            InputStream input = classLoader.getResourceAsStream("Notification.properties");
            properties.load(input);
        } catch (IOException e) {
           logger.fatal("I could not get the settings for working with mail notification",e);
        }
    }

    @Override
    public void sendEmail(String recipientEmail) throws MailException {
        Properties props = System.getProperties();
        if(!properties.containsKey("mail.content")){
            logger.error("Could not send mail_notification because properties file not found");
            throw new MailException("Dear user no we can`t send massage to you!\nWe give our apologise...", LogicException.ExceptionType.SERVER_EXCEPTION,new FileNotFoundException());
        }
        props.put("mail.smtp.port", properties.getProperty("mail.smtp.port"));
        props.put("mail.smtp.host", properties.getProperty("mail.smtp.host"));
        props.put("mail.smtp.auth", properties.getProperty("mail.smtp.auth"));
        props.put("mail.smtp.starttls.enable",  properties.getProperty("mail.smtp.starttls.enable"));
        Session session = Session.getInstance(props);

        MimeMessage msg = new MimeMessage(session);
        try {
            msg.setFrom(new InternetAddress(properties.getProperty("mail.login")));
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));
            msg.setSubject( properties.getProperty("mail.topic"));

            int id = makeId();
            msg.setText( properties.getProperty("mail.content")+id+END_LINK, properties.getProperty("mail.mime.charset"),"html");
            Transport.send(msg , properties.getProperty("mail.login"), properties.getProperty("mail.password"));
            tokens.add(new Token(recipientEmail,60,id));
        } catch (MessagingException e) {
         logger.error("Could not send mail_notification", e);
         throw new MailException("Dear user no we can`t send massage to you!\nWe give our apologise...", LogicException.ExceptionType.SERVER_EXCEPTION,e);
        }
    }

    @Override
    public boolean isValidId(int id) {
        for (Token tok : tokens) {
            if (tok.getData() == id) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * Method for deleting token when user confirms email
     * @return email if container of tokens contains id or null if dose`nt
     */
    @Override
    public String removeId(int id) {
        for (Token tok : tokens) {
            if (tok.getData() == id) {
                tokens.remove(tok);
                return tok.getIdentificationName();
            }
        }
        return null;
    }


    private int makeId(){
        int id=0;
        boolean isExistId=false;
        do{
            id = (int)(Math.random()*Integer.MAX_VALUE/1.5);
            for (Token tok: tokens){
                if(tok.getData()==id){
                    isExistId=true;
                }
            }
        }while(isExistId);
        return id;
    }
}
