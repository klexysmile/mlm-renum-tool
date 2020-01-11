package mlm.tool.mungwin.com.mlmtool.exchange.payment.dto.request;

import lombok.Data;

@Data
public class DepositRequestDTO {
    private String initiatedBy;
    private Double amount;
    private String currency;
    private String registrationCode;
}
