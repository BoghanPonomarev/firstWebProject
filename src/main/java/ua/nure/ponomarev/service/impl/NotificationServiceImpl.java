package ua.nure.ponomarev.service.impl;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.nure.ponomarev.exception.MailSenderException;
import ua.nure.ponomarev.exception.SmsSenderException;
import ua.nure.ponomarev.sender.EmailSender;
import ua.nure.ponomarev.sender.SmsSender;
import ua.nure.ponomarev.service.NotificationService;
import ua.nure.ponomarev.service.Token;
import ua.nure.ponomarev.service.TokenSet;

import java.util.Set;

/**
 * @author Bogdan_Ponamarev.
 */
public class NotificationServiceImpl implements NotificationService {
    private static Logger logger = LogManager.getLogger(NotificationServiceImpl.class);
    private SmsSender phoneNotificator;
    private EmailSender mailNotificator;
    private Set<Token> smsTokens;
    private Set<Token> emailTokens;

    public NotificationServiceImpl(SmsSender phoneNotificator, EmailSender mailNotificator) {
        this.phoneNotificator = phoneNotificator;
        this.mailNotificator = mailNotificator;
        smsTokens = new TokenSet(10);
        emailTokens = new TokenSet(10);
    }

    @Override
    public void sendConfirmEmail(String email) throws MailSenderException {
        mailNotificator.sendEmail(email, "Registration", makeEmailMassage(email));
        logger.info("Letter was sent to " + email);
    }

    private String makeEmailMassage(String email) {
        int id;
        boolean isExistId = false;
        do {
            id = (int) (Math.random() * Integer.MAX_VALUE / 1.5);
            for (Token tok : emailTokens) {
                if (tok.getData() == id) {
                    isExistId = true;
                }
            }
        } while (isExistId);
        emailTokens.add(new Token(email, 60, id));
        return "<h3>Hello , here is your link<h3><br><a href='http://localhost:8080/app/confirm_registration?id=" + id + "'>confirm registration</a>";
    }

    @Override
    public boolean isValidEmailId(int id) {
        for (Token tok : emailTokens) {
            if (tok.getData() == id) {
                return true;
            }
        }
        return false;
    }

    /**
     * Method for deleting token when user confirms email
     *
     * @return email if container of tokens contains id or null if dose`nt
     */
    @Override
    public String removeEmailId(int id) {
        for (Token tok : emailTokens) {
            if (tok.getData() == id) {
                emailTokens.remove(tok);
                logger.info(tok.getIdentificationData() + " letter was deleted");
                return tok.getIdentificationData();
            }
        }
        return null;
    }

    @Override
    public void sendPinCode(String phoneNumber) throws SmsSenderException {
        int pinCode = (int) (Math.random() * 89999 + 10000);
        String res = phoneNotificator.sendPinCode(phoneNumber, "Your code:" + pinCode);
        smsTokens.add(new Token(phoneNumber, 3, pinCode));
        logger.info("Pin code (" + pinCode + ") was sent to " + phoneNumber + " answer code is: " + res);
    }

    @Override
    public boolean isValidPhonePinCode(int pinCode, String phoneNumber) {
        for (Token token : smsTokens) {
            if (token.getData() == pinCode && phoneNumber.equals(token.getIdentificationData())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void removePhonePinCode(String phoneNumber) {
        for (Token token : smsTokens) {
            if (phoneNumber.equals(token.getIdentificationData())) {
                logger.info(token.getIdentificationData() + " sms code was deleted");
                smsTokens.remove(token);
            }
        }
    }
}
