package ua.nure.ponomarev.service.impl;


import ua.nure.ponomarev.notification.mail_notification.MailNotificator;
import ua.nure.ponomarev.notification.phone_notification.PhoneNotificator;
import ua.nure.ponomarev.service.NotificationService;
import ua.nure.ponomarev.web.exception.MailException;

/**
 * @author Bogdan_Ponamarev.
 */
public class NotificationServiceImpl implements NotificationService {
    private PhoneNotificator phoneNotificator;
    private MailNotificator mailNotificator;

    public NotificationServiceImpl(PhoneNotificator phoneNotificator, MailNotificator mailNotificator) {
        this.phoneNotificator = phoneNotificator;
        this.mailNotificator = mailNotificator;
    }

    @Override
    public void sendMail(String email) throws MailException {
        mailNotificator.sendEmail(email);
    }

    @Override
    public boolean isValidEmailId(int id) {
        return mailNotificator.isValidId(id);
    }

    @Override
    public String removeEmailId(int id) {
        return mailNotificator.removeId(id);
    }

    @Override
    public void sendPinCode(String phoneNumber) {
        phoneNotificator.sendPinCode(phoneNumber);
    }

    @Override
    public boolean isValidPhonePinCode(int pinCode, String phoneNumber) {
        return phoneNotificator.isValidPinCode(pinCode,phoneNumber);
    }

    @Override
    public void removePhonePinCode(String phoneNumber) {
        phoneNotificator.removePinCode(phoneNumber);
    }
}
