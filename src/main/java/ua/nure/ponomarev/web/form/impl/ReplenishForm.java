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
public class ReplenishForm implements Form {
    private String accountName;
    private String amount;
    private String currency;
}
