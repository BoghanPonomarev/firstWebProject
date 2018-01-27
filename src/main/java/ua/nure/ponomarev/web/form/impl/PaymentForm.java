package ua.nure.ponomarev.web.form.impl;

import lombok.Getter;
import lombok.Setter;
import ua.nure.ponomarev.web.form.Form;

import java.math.BigDecimal;

/**
 * @author Bogdan_Ponamarev.
 */
@Getter
@Setter
public class PaymentForm implements Form {
    private String recipientAccountIdentity;
    private String amount;
    private int accountId;
    private String password;
    private String currency;
}
