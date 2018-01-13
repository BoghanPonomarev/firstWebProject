package ua.nure.ponomarev.web.validator;

import ua.nure.ponomarev.web.form.AccountForm;

import java.util.*;
import java.util.regex.Pattern;

/**
 * @author Bogdan_Ponamarev.
 */
public class AccountValidator implements Validator<AccountForm> {
    private Pattern amountPattern = Pattern.compile("\\d+");
    private Pattern CVVPattern = Pattern.compile("\\d{3}");
    private Pattern datePattern = Pattern.compile("\\d{2}-\\d{2}");
    private Pattern cardNumberPattern = Pattern.compile("\\d{13,16}");

    @Override
    public Map<String, String> validate(AccountForm form) {
        HashMap<String, String> errors = new HashMap<>();
        cardNumberValidation(errors,form);
        amountValidation(errors,form);
        CVVValidation(errors,form);
        dateValidation(errors,form);
        return errors;
    }

    private void amountValidation(Map<String,String> errors,AccountForm form) {
        if(!amountPattern.matcher(form.getAmount()).matches()){
            errors.put("amount","Amount of money is not valid");
        }
    }
    private void CVVValidation(Map<String,String> errors,AccountForm form) {
        if(!CVVPattern.matcher(form.getCVV()).matches()){
            errors.put("CVV","CVV of card is not valid");
        }
    }
    private void dateValidation(Map<String,String> errors,AccountForm form) {
        if(!datePattern.matcher(form.getValidTrue()).matches()){
            errors.put("date","Date of card is not valid");
        }
    }
    private void cardNumberValidation(Map<String,String> errors,AccountForm form) {
        if (!cardNumberPattern.matcher(form.getCardNumber()).matches()) {
            errors.put("card number", "Card number is not valid");
        }
    }
}
