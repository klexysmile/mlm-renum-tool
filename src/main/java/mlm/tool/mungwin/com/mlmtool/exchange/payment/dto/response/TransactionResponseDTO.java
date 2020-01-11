package mlm.tool.mungwin.com.mlmtool.exchange.payment.dto.response;

import lombok.Data;

@Data
public class TransactionResponseDTO {
    private Long transactionId;
    private String transactionCode;
    private String transactionStatus;
    private String transactionDetailsUri;
}
