package ua.nure.ponomarev.entity;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author Bogdan_Ponamarev.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Payment {
    private int id;
    private int userId;
    private int accountId;
    private LocalDateTime date;
    private BigDecimal amount;
    private String sender;
    private String recipient;
}
