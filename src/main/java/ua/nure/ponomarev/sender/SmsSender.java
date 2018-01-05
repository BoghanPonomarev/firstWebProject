package ua.nure.ponomarev.sender;


import ua.nure.ponomarev.exception.SMSSenderException;

/**
 * @author Bogdan_Ponamarev.
 */
public interface SmsSender {
    String sendPinCode(String phoneNumber,String massage) throws SMSSenderException;
}
