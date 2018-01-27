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
    private int recipientId;
    private int senderId;
    private LocalDateTime date;
    private BigDecimal amount;
    private Status status;
    private String currency;
    private Type type;
    public enum Type{
        INCOMING,
        OUTGOING
    }
    public enum Status{
        PREPARED,
        SENT
    }
}
