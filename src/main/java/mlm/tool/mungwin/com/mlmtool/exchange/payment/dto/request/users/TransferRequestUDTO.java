package mlm.tool.mungwin.com.mlmtool.exchange.payment.dto.request.users;

import lombok.Data;
import mlm.tool.mungwin.com.mlmtool.exchange.payment.dto.request.DepositListing;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class TransferRequestUDTO {
    @NotNull(message = "currency is required")
    private String currency;
    @NotBlank(message = "password is required")
    private String password;
    @NotNull(message = "at least one receiver is required")
    private List<DepositListing> receivers;
}
