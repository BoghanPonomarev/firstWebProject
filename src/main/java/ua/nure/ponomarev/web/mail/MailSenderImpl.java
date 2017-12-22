package ua.nure.ponomarev.web.mail;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
public class MailSenderImpl implements MailSender {
    private Logger logger = LogManager.getLogger(MailSenderImpl.class);
    private Set<Token> tokens;
    private Properties mailProperties;
    private static final String END_LINK ="'>confirm registration</a>";
    public MailSenderImpl()  {
        tokens = new TokenSet(5);
        mailProperties = new Properties();
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            InputStream input = classLoader.getResourceAsStream("Email.properties");
            mailProperties.load(input);
        } catch (IOException e) {
           logger.fatal("I could not get the settings for working with mail",e);
        }
    }

    @Override
    public void sendEmail(String recipientEmail) throws MailException {
        Properties props = System.getProperties();
        if(!mailProperties.containsKey("mail.content")){
            logger.error("Could not send mail because properties file not found");
            throw new MailException("Dear user no we can`t send massage to you!\nWe give our apologise...", LogicException.ExceptionType.SERVER_EXCEPTION,new FileNotFoundException());
        }
        props.put("mail.smtp.port", mailProperties.getProperty("mail.smtp.port"));
        props.put("mail.smtp.host", mailProperties.getProperty("mail.smtp.host"));
        props.put("mail.smtp.auth", mailProperties.getProperty("mail.smtp.auth"));
        props.put("mail.smtp.starttls.enable",  mailProperties.getProperty("mail.smtp.starttls.enable"));
        Session session = Session.getInstance(props);

        MimeMessage msg = new MimeMessage(session);
        try {
            msg.setFrom(new InternetAddress(mailProperties.getProperty("mail.login")));
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));
            msg.setSubject( mailProperties.getProperty("mail.topic"));

            int id = makeId();
            msg.setText( mailProperties.getProperty("mail.content")+id+END_LINK,mailProperties.getProperty("mail.mime.charset"),"html");
            Transport.send(msg , mailProperties.getProperty("mail.login"),mailProperties.getProperty("mail.password"));
            tokens.add(new Token(recipientEmail,System.currentTimeMillis()/60000,id));
        } catch (MessagingException e) {
         logger.error("Could not send mail", e);
         throw new MailException("Dear user no we can`t send massage to you!\nWe give our apologise...", LogicException.ExceptionType.SERVER_EXCEPTION,e);
        }
    }

    @Override
    public boolean isValidId(int id) {
        for (Token tok : tokens) {
            if (tok.getId() == id) {
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
            if (tok.getId() == id) {
                tokens.remove(tok);
                return tok.getEmail();
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
                if(tok.getId()==id){
                    isExistId=true;
                }
            }
        }while(isExistId);
        return id;
    }
}
