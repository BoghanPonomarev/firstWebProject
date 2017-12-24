package ua.nure.ponomarev.notification.mail_notification;

import ua.nure.ponomarev.web.exception.MailException;

/**
 * @author Bogdan_Ponamarev.
 */
public interface MailSender {
    void sendEmail(String email) throws MailException;

    boolean isValidId(int id);

    String removeId(int id);

}
