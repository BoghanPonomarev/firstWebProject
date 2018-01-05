package ua.nure.ponomarev.sender.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.nure.ponomarev.exception.LogicException;
import ua.nure.ponomarev.exception.MailSenderException;
import ua.nure.ponomarev.sender.EmailSender;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import  javax.mail.Transport;

/**
 * @author Bogdan_Ponamarev.
 */
public class EmailSenderImpl implements EmailSender {
    private static Logger logger = LogManager.getLogger(EmailSenderImpl.class);
    private Properties properties;
    public EmailSenderImpl()  {
        properties = new Properties();
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            InputStream input = classLoader.getResourceAsStream("Notification.properties");
            properties.load(input);
        } catch (IOException e) {
           logger.fatal("I could not get the settings for working with mail sender",e);
        }
    }

    @Override
    public void sendEmail(String recipientEmail,String topic, String massageHTML) throws MailSenderException {
        Properties props = System.getProperties();
        if(!properties.containsKey("mail.login")){
            logger.error("Could not send mail notification because properties file not found");
            throw new MailSenderException("Dear user no we can`t send massage to you!\nWe give our apologise...", LogicException.ExceptionType.SERVER_EXCEPTION,new FileNotFoundException());
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
            msg.setSubject(topic);
            msg.setText(massageHTML, properties.getProperty("mail.mime.charset"),"html");
            Transport.send(msg , properties.getProperty("mail.login"), properties.getProperty("mail.password"));

        } catch (MessagingException e) {
         logger.error("Could not send mail_notification", e);
         throw new MailSenderException("Dear user no we can`t send massage to you!\nWe give our apologise...", LogicException.ExceptionType.SERVER_EXCEPTION,e);
        }
    }

}
