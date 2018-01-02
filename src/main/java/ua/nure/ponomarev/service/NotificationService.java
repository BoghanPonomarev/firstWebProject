package ua.nure.ponomarev.service;

import ua.nure.ponomarev.web.exception.MailException;

/**
 * @author Bogdan_Ponamarev.
 */
public interface NotificationService {
    void sendMail(String email) throws MailException;

    boolean isValidEmailId(int id);

    String removeEmailId(int id);

    void sendPinCode(String phoneNumber);

    boolean isValidPhonePinCode(int pinCode, String phoneNumber);

    void removePhonePinCode(String phoneNumber);
}
