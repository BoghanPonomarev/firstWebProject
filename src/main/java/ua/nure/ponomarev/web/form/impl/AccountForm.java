package ua.nure.ponomarev.web.form.impl;

import lombok.Getter;
import lombok.Setter;
import ua.nure.ponomarev.web.form.Form;

/**
 * @author Bogdan_Ponamarev.
 */
@Getter
@Setter
public class AccountForm implements Form {
    private String amount;
    private String cardNumber;
    private String validTrue;
    private String CVV;
    private String name;
    private String currency;
}
