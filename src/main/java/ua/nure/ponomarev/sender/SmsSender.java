package ua.nure.ponomarev.sender;


import ua.nure.ponomarev.exception.SmsSenderException;

/**
 * @author Bogdan_Ponamarev.
 */
public interface SmsSender {
    String sendPinCode(String phoneNumber, String massage) throws SmsSenderException;
}
