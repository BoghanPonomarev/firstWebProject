package ua.nure.ponomarev.web.form;

import lombok.Getter;
import lombok.Setter;
/**
 * @author Bogdan_Ponamarev.
 */
@Getter
@Setter
public class RegistrationForm implements Form {
    private String email;
    private String phoneNumber;
    private String login;
    private String firstPassword;
    private String secondPassword;
}


