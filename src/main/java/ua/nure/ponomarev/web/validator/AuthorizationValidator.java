package ua.nure.ponomarev.web.validator;

import ua.nure.ponomarev.web.form.AuthorizationForm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Bogdan_Ponamarev.
 */
public class AuthorizationValidator implements Validator<AuthorizationForm> {
    private Pattern passwordPattern = Pattern.compile("[A-Za-zа-яА-я0-9\\-\\_]{5,15}", Pattern.UNICODE_CASE);
    private Pattern phoneNumberPattern = Pattern.compile("\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}");

    @Override
    public List<String> validate(AuthorizationForm form) {
        List<String> errors=new ArrayList<>();
        phoneNumberValidation(form,errors);
        passwordValidation(form,errors);
        return errors;
    }

    private void phoneNumberValidation(AuthorizationForm form, List<String> errors) {
        Matcher matcher = phoneNumberPattern.matcher(form.getPhoneNumber());
        if (!matcher.matches()) {
            errors.add( "Phone number is not correct");
        }
    }

    private void passwordValidation(AuthorizationForm form, List<String>  errors) {
        Matcher passwordMatcher = passwordPattern.matcher(form.getPassword());
        if (!passwordMatcher.matches()) {
            errors.add("Password is not correct");
        }
    }

}
