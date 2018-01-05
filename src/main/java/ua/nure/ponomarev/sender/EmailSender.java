package ua.nure.ponomarev.sender;

import ua.nure.ponomarev.exception.MailSenderException;

/**
 * @author Bogdan_Ponamarev.
 */
public interface EmailSender {
    void sendEmail(String email,String topic,String massageHTML) throws MailSenderException;
}
