package mlm.tool.mungwin.com.mlmtool.exchange.payment.dto.request.users;

import lombok.Data;

@Data
public class ConfirmTransactionRequestUDTO {
    private String transactionCode;
    private Boolean confirm;
}
