package ua.nure.ponomarev.document;

import lombok.*;

/**
 * Dto that represent Payment entity for rendering.
 *
 * @author Bogdan_Ponamarev.
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class RenderPaymentDto {
    private String documentNumber;
    private String payer;
    private String amountCursive;
    private String date;
    private String receiverAccount;
    private String receiver;
    private String payerAccount;
    private String amount;
}
