package ua.nure.ponomarev.notification.phone_notification;


/**
 * @author Bogdan_Ponamarev.
 */
public interface PhoneNotificator {
    void sendPinCode(String phoneNumber) ;

    boolean isValidPinCode(int pinCode, String phoneNumber);

    void removePinCode(String phoneNumber);
}
