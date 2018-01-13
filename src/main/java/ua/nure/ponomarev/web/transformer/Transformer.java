package ua.nure.ponomarev.web.transformer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.nure.ponomarev.entity.Account;
import ua.nure.ponomarev.entity.User;
import ua.nure.ponomarev.exception.TransformationException;
import ua.nure.ponomarev.web.form.AccountForm;
import ua.nure.ponomarev.web.form.RegistrationForm;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author Bogdan_Ponamarev.
 */
public class Transformer {
    private static Logger logger = LogManager.getLogger(Transformer.class);
    public static User transformToUser(RegistrationForm registrationForm){
        User user=new User();
        user.setEmail(registrationForm.getEmail());
        user.setLogin(registrationForm.getLogin());
        user.setPassword(registrationForm.getFirstPassword());
        user.setPhoneNumber(registrationForm.getPhoneNumber());
        return user;
    }

    public static Account transformToAccount(AccountForm accountForm) throws TransformationException {
        Account.Card card = new Account.Card();
        accountFormChecking(accountForm);
        card.setAmount(BigDecimal.valueOf(Double.parseDouble(accountForm.getAmount())));
        card.setCardNumber(accountForm.getCardNumber());
        card.setCVV(accountForm.getCVV());
        card.setValidThru(LocalDate.parse("20" + accountForm.getValidTrue()+"-01"));
        return new Account(card,0);
    }

    /**
     * In this method and also Class i need little validation , because i a not
     * sure that in all cases I transform account after processing in validation Class
     * @param accountForm form that was fill by FormMaker and currently should
     *                   be processed by validation class
     * @throws TransformationException if there is invalid datain form
     */
    private static void accountFormChecking(AccountForm accountForm) throws TransformationException {
        if(accountForm.getAmount()==null||!accountForm.getAmount().chars().allMatch(Character::isDigit)){
            logger.info("Invalid amount data from user");
            throw new TransformationException();
        }
        if(accountForm.getCardNumber()==null){
            logger.info("Card number is null");
            throw new TransformationException();
        }
        if(accountForm.getCVV()==null){
            logger.info("CVV is null");
            throw new TransformationException();
        }
        if(accountForm.getValidTrue()==null||!accountForm.getValidTrue().matches("\\d{2}-\\d{2}")){
            logger.info("Date is not correct or null");
            throw new TransformationException();
        }
    }
}
