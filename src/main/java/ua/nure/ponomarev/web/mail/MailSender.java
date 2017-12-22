package ua.nure.ponomarev.web.mail;

import ua.nure.ponomarev.web.exception.MailException;

/**
 * @author Bogdan_Ponamarev.
 */
public interface MailSender {
    void sendEmail(String email) throws MailException;

    boolean isValidId(int id);

    String removeId(int id);

}
