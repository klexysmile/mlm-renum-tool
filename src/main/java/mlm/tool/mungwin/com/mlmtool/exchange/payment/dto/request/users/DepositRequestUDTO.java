package mlm.tool.mungwin.com.mlmtool.exchange.payment.dto.request.users;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class DepositRequestUDTO {
    @NotNull(message = "amount is required")
    private Double amount;
    @NotBlank(message = "currency is required")
    private String currency;
}
