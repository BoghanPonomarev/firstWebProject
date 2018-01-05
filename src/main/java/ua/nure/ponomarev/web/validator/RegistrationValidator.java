package ua.nure.ponomarev.web.validator;


import ua.nure.ponomarev.web.form.RegistrationForm;

import java.util.HashMap;
import java.util.Map;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Bogdan_Ponamarev.
 */
public class RegistrationValidator implements Validator<RegistrationForm> {
    private Pattern emailPattern = Pattern.compile("[\\w-]+([^@,\\s<>\\(\\)]*[\\w-]+)?@[\\w-]+(\\.[\\w-]+)*\\.[a-z]{2,}", Pattern.UNICODE_CASE);
    private Pattern loginPasswordPattern = Pattern.compile("[A-Za-zа-яА-я0-9\\-\\_]+", Pattern.UNICODE_CASE);
    private Pattern phoneNumberPattern = Pattern.compile("\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}");

    public Map<String, String> validate(RegistrationForm form) {
        Map<String, String> errors = new HashMap<>();
        emailValidation(errors,form);
        phoneNumberValidation(errors,form);
        loginValidator(errors,form);
        passwordValidator(errors,form);
        return errors;
    }

    private void emailValidation(Map<String,String> errors, RegistrationForm form) {
        Matcher matcher = emailPattern.matcher(form.getEmail());
        if (!matcher.matches()) {
            errors.put("emailFormat", "Email is not correct");
        }
    }

    private void phoneNumberValidation(Map<String,String> errors, RegistrationForm form) {
        Matcher matcher = phoneNumberPattern.matcher(form.getPhoneNumber());
        if (!matcher.matches()) {
            errors.put("phoneNumberFormat", "Phone number is not correct");
        }
    }

    private void loginValidator(Map<String,String> errors, RegistrationForm form) {
        Matcher matcher = loginPasswordPattern.matcher(form.getLogin());
        if (form.getLogin().length() > 15) {
            errors.put("loginLongSize", "Login can`t be more then 15 characters");
        }
        if (form.getLogin().length() < 5) {
            errors.put("loginShortSize", "Login can`t be less then 5 characters");
        }
        if (!matcher.matches()){//Need finding only one time
            errors.put("loginFormat", "Login can only contain letters and digits");
        }
    }
    private void passwordValidator(Map<String,String> errors,RegistrationForm form){
        Matcher firstPasMatcher=loginPasswordPattern.matcher(form.getFirstPassword());
        Matcher secondPasMatcher=loginPasswordPattern.matcher(form.getSecondPassword());
        if(!form.getFirstPassword().equals(form.getSecondPassword())){
            errors.put("passwordEquals","Passwords are`nt equals");
        }
        if(form.getFirstPassword().length()>15
                ||form.getSecondPassword().length()>15){
            errors.put("passwordLongSize", "Password can`t be more then 15 characters");
        }
        if(form.getFirstPassword().length()<5
                ||form.getSecondPassword().length()<5){
            errors.put("passwordShortSize", "Password can`t be less then 5 characters");
        }
        if(!firstPasMatcher.matches()||!secondPasMatcher.matches()){
            errors.put("passwordFormat","Password can only contain letters and digits");
        }
    }
}
