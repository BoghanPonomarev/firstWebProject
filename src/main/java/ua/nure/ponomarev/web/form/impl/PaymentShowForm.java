package ua.nure.ponomarev.web.form.impl;

import lombok.Getter;
import lombok.Setter;
import ua.nure.ponomarev.entity.Payment;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author Bogdan_Ponamarev.
 */
@Getter
@Setter
public class PaymentShowForm {
    private int id;
    private String recipient;
    private String sender;
    private LocalDateTime date;
    private BigDecimal amount;
    private Payment.Status status;
    private String currency;
    private Payment.Type type;
}
