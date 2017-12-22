package ua.nure.ponomarev.web.form;

import java.time.LocalDateTime;

/**
 * @author Bogdan_Ponamarev.
 */
public class RegistrationForm implements Form {
    private String email;

    private String phoneNumber;

    private String login;

    private String firstPassword;

    private String secondPassword;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getFirstPassword() {
        return firstPassword;
    }

    public void setFirstPassword(String firstPassword) {
        this.firstPassword = firstPassword;
    }

    public String getSecondPassword() {
        return secondPassword;
    }

    public void setSecondPassword(String secondPassword) {
        this.secondPassword = secondPassword;
    }

    class LoginFormBuilder{
    private LoginFormBuilder(){
    }
        /**
         * Create Builder soon
         */
    }
}
