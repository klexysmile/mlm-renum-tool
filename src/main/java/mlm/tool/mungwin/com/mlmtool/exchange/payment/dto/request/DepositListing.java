package mlm.tool.mungwin.com.mlmtool.exchange.payment.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepositListing {
    @NotBlank(message = "receiverRegCode is required")
    private String receiverRegCode;
    @NotNull(message = "amount cannot be null")
    private Double amount;
}
