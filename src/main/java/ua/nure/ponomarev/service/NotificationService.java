package ua.nure.ponomarev.service;

import ua.nure.ponomarev.exception.MailSenderException;
import ua.nure.ponomarev.exception.SMSSenderException;

/**
 * @author Bogdan_Ponamarev.
 */
public interface NotificationService {
    void sendConfirmEmail(String email) throws MailSenderException;

    boolean isValidEmailId(int id);

    String removeEmailId(int id);

    void sendPinCode(String phoneNumber) throws SMSSenderException;

    boolean isValidPhonePinCode(int pinCode, String phoneNumber);

    void removePhonePinCode(String phoneNumber);
}
