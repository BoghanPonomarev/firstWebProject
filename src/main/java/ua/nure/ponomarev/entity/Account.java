package ua.nure.ponomarev.entity;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author Bogdan_Ponamarev.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    private Card card;
    private int id;
    private String name;
    private String currency;
    private BigDecimal balance;
    private boolean isBanned;
    private boolean isRequestedForUnban;
    public Account(int id, Card card) {
        this.id = id;
        this.card = card;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Card {
        private String cardNumber;
        private LocalDate validThru;
        private String CVV;
    }
}
