package ua.nure.ponomarev.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author Bogdan_Ponamarev.
 */
@Getter
@AllArgsConstructor
public class Account {
    private Card card;
    private int id;
    private boolean banned;
    @Getter
    @Setter
    public static class Card{
        private BigDecimal amount;
        private String cardNumber;
        private LocalDate validThru;
        private String CVV;
        private int currencyId;
    }
}
