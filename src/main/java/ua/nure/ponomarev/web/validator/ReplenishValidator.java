package ua.nure.ponomarev.web.validator;

import ua.nure.ponomarev.web.form.impl.ReplenishForm;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author Bogdan_Ponamarev.
 */
public class ReplenishValidator implements Validator<ReplenishForm> {
    private static final Pattern ACCOUNT_NAME_PATTERN = Pattern.compile("[A-Za-zа-яА-я0-9\\-]+");
    private static final Pattern AMOUNT_PATTERN = Pattern.compile("[0-9]{0,10}\\.?[0-9]{0,2}");
    private static final Pattern CURRENCY_PATTERN = Pattern.compile("\\w{1,4}");

    @Override
    public List<String> validate(ReplenishForm form) {
        List<String> errors = new ArrayList<>();
        accountNameValidation(errors, form);
        amountValidation(errors, form);
        currencyValidation(errors, form);
        return errors;
    }

    private void accountNameValidation(List<String> errors, ReplenishForm form) {
        if (form.getAccountName() == null) {
            errors.add("Account must be filled");
            return;
        }
        if (form.getAccountName().length() > 13) {
            errors.add("Account name too long(max length 13)");
        }
        if (form.getAccountName().length() < 3) {
            errors.add("Account name too short(max length 13)");
        }
        if (!ACCOUNT_NAME_PATTERN.matcher(form.getAccountName()).matches()) {
            errors.add("Account name is not valid");
        }
    }

    private void amountValidation(List<String> errors, ReplenishForm form) {
        if (!AMOUNT_PATTERN.matcher(form.getAmount()).matches()) {
            errors.add("Amount is not valid");
        }
    }

    private void currencyValidation(List<String> errors, ReplenishForm form) {
        if (!CURRENCY_PATTERN.matcher(form.getCurrency()).matches()) {
            errors.add("Currency is not valid");
        }
    }
}
