package ua.nure.ponomarev.web.validator;

import ua.nure.ponomarev.web.form.impl.AuthorizationForm;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Bogdan_Ponamarev.
 */
public class AuthorizationValidator implements Validator<AuthorizationForm> {
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("[A-Za-zа-яА-я0-9\\-\\_]{5,15}", Pattern.UNICODE_CASE);
    private static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile("\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}");

    @Override
    public List<String> validate(AuthorizationForm form) {
        List<String> errors = new ArrayList<>();
        phoneNumberValidation(form, errors);
        passwordValidation(form, errors);
        return errors;
    }

    private void phoneNumberValidation(AuthorizationForm form, List<String> errors) {
        Matcher matcher = PHONE_NUMBER_PATTERN.matcher(form.getPhoneNumber());
        if (!matcher.matches()) {
            errors.add("Phone number is not correct");
        }
    }

    private void passwordValidation(AuthorizationForm form, List<String> errors) {
        Matcher passwordMatcher = PASSWORD_PATTERN.matcher(form.getPassword());
        if (!passwordMatcher.matches()) {
            errors.add("Password is not correct");
        }
    }

}
