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
    public List<String> validate(AccountForm form) {
        List<String> errors = new ArrayList<>();
        cardNumberValidation(errors,form);
        amountValidation(errors,form);
        CVVValidation(errors,form);
        dateValidation(errors,form);
        return errors;
    }

    private void amountValidation(List<String>  errors,AccountForm form) {
        if(!amountPattern.matcher(form.getAmount()).matches()){
            errors.add("Amount of money is not valid");
        }
    }
    private void CVVValidation(List<String>  errors,AccountForm form) {
        if(!CVVPattern.matcher(form.getCVV()).matches()){
            errors.add("CVV of card is not valid");
        }
    }
    private void dateValidation(List<String>  errors,AccountForm form) {
        if(!datePattern.matcher(form.getValidTrue()).matches()){
            errors.add("Date of card is not valid");
        }
    }
    private void cardNumberValidation(List<String>  errors,AccountForm form) {
        if (!cardNumberPattern.matcher(form.getCardNumber()).matches()) {
            errors.add( "Card number is not valid");
        }
    }
}
