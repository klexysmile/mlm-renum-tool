package mlm.tool.mungwin.com.mlmtool.exchange.payment.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import mlm.tool.mungwin.com.mlmtool.exchange.payment.dto.request.DepositListing;

import java.util.List;

@Data
@NoArgsConstructor
public class TransferResponseDTO {
    private List<PaymentTransactionResponseDTO> successful;
    private List<DepositListing> failed;

    public TransferResponseDTO(List<PaymentTransactionResponseDTO> successful, List<DepositListing> failed) {
        this.successful = successful;
        this.failed = failed;
    }
}
