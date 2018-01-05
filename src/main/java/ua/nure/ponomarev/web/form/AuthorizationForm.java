package ua.nure.ponomarev.web.form;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Bogdan_Ponamarev.
 */
@Getter
@Setter
public class AuthorizationForm implements Form{
    private String phoneNumber;
    private String password;
}
