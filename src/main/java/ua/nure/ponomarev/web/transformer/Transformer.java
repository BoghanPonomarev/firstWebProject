package ua.nure.ponomarev.web.transformer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.nure.ponomarev.entity.Account;
import ua.nure.ponomarev.entity.Payment;
import ua.nure.ponomarev.entity.User;
import ua.nure.ponomarev.web.form.impl.AccountForm;
import ua.nure.ponomarev.web.form.impl.PaymentForm;
import ua.nure.ponomarev.web.form.impl.RegistrationForm;
import ua.nure.ponomarev.web.form.impl.UserForm;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author Bogdan_Ponamarev.
 */
public class Transformer {
    private static final String DEFAULT_CURRENCY = "USD";
    private static Logger logger = LogManager.getLogger(Transformer.class);

    public static User transformToUser(RegistrationForm registrationForm) {
        User user = new User();
        user.setPassword(registrationForm.getFirstPassword());
        user.setPhoneNumber(registrationForm.getPhoneNumber());
        return user;
    }

    public static User transformToUser(UserForm userForm) {
        User user = new User();
        user.setEmail(userForm.getEmail());
        user.setPhoneNumber(userForm.getPhoneNumber());
        user.setId(userForm.getId());
        user.setFirstName(userForm.getFirstName());
        user.setSecondName(userForm.getSecondName());
        user.setThirdName(userForm.getThirdName());
        user.setPassword(userForm.getPassword());
        return user;
    }
    public static Account transformToAccount(AccountForm accountForm) {
        Account.Card card = new Account.Card();
        BigDecimal amount=new BigDecimal(0);
        if (accountForm.getAmount() != null || accountForm.getAmount().chars().allMatch(Character::isDigit)) {
           amount = BigDecimal.valueOf(Double.parseDouble(accountForm.getAmount()));
        }
        if (accountForm.getCardNumber() == null) {
            logger.info("Card number is null");
            card.setCardNumber("000000000000000");
        } else {
            card.setCardNumber(accountForm.getCardNumber());
        }
        if (accountForm.getCVV() == null) {
            logger.info("CVV is null");
            card.setCVV("000");
        } else {
            card.setCVV(accountForm.getCVV());
        }
        if (accountForm.getValidTrue() == null || !accountForm.getValidTrue().matches("\\d{2}-\\d{2}")) {
            logger.info("Date is not correct or null");
            card.setValidThru(LocalDate.parse("2030-01-01"));
        } else {
            card.setValidThru(LocalDate.parse("20" + accountForm.getValidTrue() + "-01"));
        }
        String currency= DEFAULT_CURRENCY;
        if (accountForm.getCurrency() != null) {
            currency = accountForm.getCurrency();
        }
        return new Account().builder().card(card)
                .id(0).name(accountForm.getName()).isBanned(false).isRequestedForUnban(false)
                .currency(currency).balance(amount).build();
    }

}
