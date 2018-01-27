package ua.nure.ponomarev.web.validator;

import ua.nure.ponomarev.web.form.impl.PaymentForm;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Bogdan_Ponamarev.
 */
public class PaymentValidator implements Validator<PaymentForm> {
    private static final Pattern AMOUNT_PATTERN = Pattern.compile("[0-9]{0,10}\\.?[0-9]{0,2}");
    private static final Pattern CARD_NUMBER_PATTERN = Pattern.compile("\\d{14,16}");
    private static final Pattern ACCOUNT_NAME_PATTERN = Pattern.compile("[A-Za-zа-яА-я0-9\\-]{3,13}");
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("[A-Za-zа-яА-я0-9\\-\\_]+", Pattern.UNICODE_CASE);
    public static final int MAX_PAYMENT_AMOUNT = 1000;

    @Override
    public List<String> validate(PaymentForm form) {
        List<String> errors= new ArrayList<>();
        amountValidation(errors,form);
        passwordValidator(errors,form);
        identityValidator(errors,form);
        return errors;
    }

    private void amountValidation(List<String> errors, PaymentForm form) {
        if (!AMOUNT_PATTERN.matcher(form.getAmount()).matches()) {
            errors.add("Amount of money is not valid");
        }else{
            if(Double.parseDouble(form.getAmount())> MAX_PAYMENT_AMOUNT){
                errors.add("To large number(max - "+MAX_PAYMENT_AMOUNT+")");
            }
        }
    }
    private void passwordValidator(List<String> errors, PaymentForm form) {
        Matcher firstPasMatcher = PASSWORD_PATTERN.matcher(form.getPassword());
        if (form.getPassword().length() > 15) {
            errors.add("Password can`t be more then 15 characters");
        }
        if (form.getPassword().length() < 5) {
            errors.add("Password can`t be less then 5 characters");
        }
        if (!firstPasMatcher.matches()) {
            errors.add("Password can only contain letters and digits");
        }
    }
    private void identityValidator(List<String> errors, PaymentForm form) {
        Matcher cardNumber = CARD_NUMBER_PATTERN.matcher(form.getRecipientAccountIdentity());
        Matcher accountName = ACCOUNT_NAME_PATTERN.matcher(form.getRecipientAccountIdentity());
        if (cardNumber.matches()&&accountName.matches()) {
            errors.add("Identity is not valid");
        }
    }
}
