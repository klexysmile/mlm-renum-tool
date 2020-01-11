package mlm.tool.mungwin.com.mlmtool.exchange.payment.dto.response;

import lombok.Data;

@Data
public class PaymentTransactionResponseDTO {
    private String transactionCode;
    private String paymentChannel;
    private String initiatedBy;
    private String authorizedBy;
    private String paymentTransactionId;
    private String senderAccount;
    private Double amount;
    private String receiverAccount;
    private String status;
    private String comment;
}
