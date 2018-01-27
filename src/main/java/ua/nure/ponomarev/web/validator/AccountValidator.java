package ua.nure.ponomarev.web.validator;

import ua.nure.ponomarev.web.form.impl.AccountForm;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author Bogdan_Ponamarev.
 */
public class AccountValidator implements Validator<AccountForm> {
    private static final String CURRENCY_PATTERN = "[A-Z]{1,4}";
    private static final Pattern AMOUNT_PATTERN = Pattern.compile("[0-9]{0,10}\\.?[0-9]+");
    private static final Pattern CVV_PATTERN = Pattern.compile("\\d{3}");
    private static final Pattern DATE_PATTERN = Pattern.compile("\\d{2}-\\d{2}");
    private static final Pattern CARD_NUMBER_PATTERN = Pattern.compile("\\d{14,16}");
    private static final Pattern ACCOUNT_NAME_PATTERN = Pattern.compile("[A-Za-zа-яА-я0-9\\-]{3,13}");

    @Override
    public List<String> validate(AccountForm form) {
        List<String> errors = new ArrayList<>();
        cardNumberValidation(errors, form);
        amountValidation(errors, form);
        CVVValidation(errors, form);
        dateValidation(errors, form);
        accountNameValidation(errors, form);
        return errors;
    }

    private void amountValidation(List<String> errors, AccountForm form) {
        if (!AMOUNT_PATTERN.matcher(form.getAmount()).matches()) {
            errors.add("Amount of money is not valid");
        }
    }

    private void CVVValidation(List<String> errors, AccountForm form) {
        if (!CVV_PATTERN.matcher(form.getCVV()).matches()) {
            errors.add("CVV of card is not valid");
        }
    }

    private void dateValidation(List<String> errors, AccountForm form) {
        if (!DATE_PATTERN.matcher(form.getValidTrue()).matches()) {
            errors.add("Date of card is not valid");
        }
    }

    private void cardNumberValidation(List<String> errors, AccountForm form) {
        if (!CARD_NUMBER_PATTERN.matcher(form.getCardNumber()).matches()) {
            errors.add("Card number is not valid");
        }
    }

    private void currencyValidation(List<String> errors, AccountForm form) {
        if (!form.getCurrency().matches(CURRENCY_PATTERN)) {
            errors.add("Currency is not valid");
        }
    }

    private void accountNameValidation(List<String> errors, AccountForm form) {
        if (!ACCOUNT_NAME_PATTERN.matcher(form.getName()).matches()) {
            errors.add("Account name is not valid");
        }
    }
}
