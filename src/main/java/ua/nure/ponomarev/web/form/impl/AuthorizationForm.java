package ua.nure.ponomarev.web.form.impl;

import lombok.Getter;
import lombok.Setter;
import ua.nure.ponomarev.web.form.Form;

/**
 * @author Bogdan_Ponamarev.
 */
@Getter
@Setter
public class AuthorizationForm implements Form {
    private String phoneNumber;
    private String password;
}
