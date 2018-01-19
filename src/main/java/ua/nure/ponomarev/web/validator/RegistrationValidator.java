package ua.nure.ponomarev.web.validator;


import ua.nure.ponomarev.web.form.RegistrationForm;

import java.util.ArrayList;
import java.util.List;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Bogdan_Ponamarev.
 */
public class RegistrationValidator implements Validator<RegistrationForm> {
    //private Pattern emailPattern = Pattern.compile("[\\w-]+([^@,\\s<>\\(\\)]*[\\w-]+)?@[\\w-]+(\\.[\\w-]+)*\\.[a-z]{2,}", Pattern.UNICODE_CASE);
    private Pattern passwordPattern = Pattern.compile("[A-Za-zа-яА-я0-9\\-\\_]+", Pattern.UNICODE_CASE);
    private Pattern phoneNumberPattern = Pattern.compile("\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}");

    public List<String> validate(RegistrationForm form) {
        List<String> errors = new ArrayList<>();
        phoneNumberValidation(errors,form);
        passwordValidator(errors,form);
        return errors;
    }

    private void phoneNumberValidation(List<String>  errors, RegistrationForm form) {
        Matcher matcher = phoneNumberPattern.matcher(form.getPhoneNumber());
        if (!matcher.matches()) {
            errors.add("Phone number is not correct");
        }
    }
//USE IT TO NAME AND OTHER STUFF
    /*private void loginValidator(List<String>  errors, RegistrationForm form) {
        Matcher matcher = passwordPattern.matcher(form.getLogin());
        if (form.getLogin().length() > 15) {
            errors.add( "Login can`t be more then 15 characters");
        }
        if (form.getLogin().length() < 5) {
            errors.add( "Login can`t be less then 5 characters");
        }
        if (!matcher.matches()){//Need finding only one time
            errors.add("Login can only contain letters and digits");
        }
    }*/
    private void passwordValidator(List<String>  errors,RegistrationForm form){
        Matcher firstPasMatcher= passwordPattern.matcher(form.getFirstPassword());
        Matcher secondPasMatcher= passwordPattern.matcher(form.getSecondPassword());
        if(!form.getFirstPassword().equals(form.getSecondPassword())){
            errors.add("Passwords are`nt equals");
        }
        if(form.getFirstPassword().length()>15
                ||form.getSecondPassword().length()>15){
            errors.add( "Password can`t be more then 15 characters");
        }
        if(form.getFirstPassword().length()<5
                ||form.getSecondPassword().length()<5){
            errors.add( "Password can`t be less then 5 characters");
        }
        if(!firstPasMatcher.matches()||!secondPasMatcher.matches()){
            errors.add("Password can only contain letters and digits");
        }
    }
}
