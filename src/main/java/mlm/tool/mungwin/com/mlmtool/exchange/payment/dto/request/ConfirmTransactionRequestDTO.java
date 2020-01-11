package mlm.tool.mungwin.com.mlmtool.exchange.payment.dto.request;

import lombok.Data;

@Data
public class ConfirmTransactionRequestDTO {
    private String confirmedBy;
    private String transactionCode;
    private Boolean confirm;
}
