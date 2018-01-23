package ua.nure.ponomarev.entity;

import lombok.*;

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
    private String name;
    private boolean banned;
    private boolean isRequestedForUnban;
    public Account(int id, Card card) {
        this.id = id;
        this.card = card;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    public static class Card {
        private BigDecimal amount;
        private String cardNumber;
        private LocalDate validThru;
        private String CVV;
        private String currency;
    }
}
