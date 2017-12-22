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
    private Pattern phoneNumberPattern = Pattern.compile("^((\\+380)[\\- ]?)?(\\(?\\d{9,10}\\)?)$");

    public Map<String, String> validate(RegistrationForm form) {
        Map<String, String> result = new HashMap<>();
        emailValidation(result,form);
        phoneNumberValidation(result,form);
        loginValidator(result,form);
        passwordValidator(result,form);
        return result;
    }

    private void emailValidation(Map<String,String> result, RegistrationForm form) {
        Matcher matcher = emailPattern.matcher(form.getEmail());
        if (!matcher.matches()) {
            result.put("emailFormat", "Email is not correct");
        }
    }

    private void phoneNumberValidation(Map<String,String> result, RegistrationForm form) {
        Matcher matcher = phoneNumberPattern.matcher(form.getPhoneNumber());
        if (!matcher.matches()) {
            result.put("phoneNumberFormat", "Phone number is not correct");
        }
    }

    private void loginValidator(Map<String,String> result, RegistrationForm form) {
        Matcher matcher = loginPasswordPattern.matcher(form.getLogin());
        if (form.getLogin().length() > 15) {
            result.put("loginLongSize", "Login can`t be more then 15 characters");
        }
        if (form.getLogin().length() < 5) {
            result.put("loginShortSize", "Login can`t be less then 5 characters");
        }
        if (!matcher.matches()){//Need finding only one time
            result.put("loginFormat", "Login can only contain letters and digits");
        }
    }
    private void passwordValidator(Map<String,String> result,RegistrationForm form){
        Matcher firstPasMatcher=loginPasswordPattern.matcher(form.getFirstPassword());
        Matcher secondPasMatcher=loginPasswordPattern.matcher(form.getSecondPassword());
        if(!form.getFirstPassword().equals(form.getSecondPassword())){
            result.put("passwordEquals","Passwords are`nt equals");
        }
        if(form.getFirstPassword().length()>15
                ||form.getSecondPassword().length()>15){
            result.put("passwordLongSize", "Password can`t be more then 15 characters");
        }
        if(form.getFirstPassword().length()<5
                ||form.getSecondPassword().length()<5){
            result.put("passwordShortSize", "Password can`t be less then 5 characters");
        }
        if(!firstPasMatcher.matches()||!secondPasMatcher.matches()){
            result.put("passwordFormat","Password can only contain letters and digits");
        }
    }
}
