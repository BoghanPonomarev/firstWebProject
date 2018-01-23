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
    private static final Pattern EMAIL_PATTERN = Pattern.compile("[\\w-]+([^@,\\s<>\\(\\)]*[\\w-]+)?@[\\w-]+(\\.[\\w-]+)*\\.[a-z]{2,}", Pattern.UNICODE_CASE);
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("[A-Za-zа-яА-я0-9\\-\\_]+", Pattern.UNICODE_CASE);
    private static final Pattern NAME_PATTERN = Pattern.compile("[A-ZА-Я]{1}[A-Za-zа-яА-я0-9\\- ]+", Pattern.UNICODE_CASE);
    private static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile("\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}");

    public List<String> validate(RegistrationForm form) {
        List<String> errors = new ArrayList<>();
        phoneNumberValidation(errors, form.getPhoneNumber());
        passwordValidator(errors, form.getFirstPassword(), form.getSecondPassword());
        return errors;
    }

    public void phoneNumberValidation(List<String> errors, String phoneNumber) {
        Matcher matcher = PHONE_NUMBER_PATTERN.matcher(phoneNumber);
        if (!matcher.matches()) {
            errors.add("Phone number is not correct");
        }
    }

    public void nameValidation(List<String> errors, String name, String variable) {
        Matcher matcher = NAME_PATTERN.matcher(name);
        if (name.length() > 15) {
            errors.add(variable + " can`t be more then 15 characters");
        }
        if (name.length() < 2) {
            errors.add(variable + " can`t be less then 2 characters");
        }
        if (!matcher.matches()) {
            errors.add(variable + " can only contain letters,spaces and dash");
        }
    }

    public void passwordValidator(List<String> errors, String password, String secondPas) {
        Matcher firstPasMatcher = PASSWORD_PATTERN.matcher(password);
        if (!password.equals(secondPas)) {
            errors.add("Passwords are`nt equals");
        }
        if (password.length() > 15) {
            errors.add("Password can`t be more then 15 characters");
        }
        if (password.length() < 5) {
            errors.add("Password can`t be less then 5 characters");
        }
        if (!firstPasMatcher.matches()) {
            errors.add("Password can only contain letters and digits");
        }
    }

    public void emailValidation(List<String> errors, String email) {
        Matcher firstPasMatcher = EMAIL_PATTERN.matcher(email);
        if (!firstPasMatcher.matches()) {
            errors.add("Email is incorrect");
        }
    }
}
