package mlm.tool.mungwin.com.mlmtool.exchange.payment.dto.request;

import lombok.Data;

@Data
public class CreateOwnAccountRequestDTO {
    private String accountName;
    private String registrationCode;
    private Double amount;
    private String currency;
    private String initiatedBy;
    private String password;
}
