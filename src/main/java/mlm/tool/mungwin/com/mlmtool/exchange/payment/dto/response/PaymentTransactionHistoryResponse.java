package mlm.tool.mungwin.com.mlmtool.exchange.payment.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class PaymentTransactionHistoryResponse {
    private List<PaymentTransactionResponseDTO> asSender;
    private List<PaymentTransactionResponseDTO> asReceiver;

    public PaymentTransactionHistoryResponse(List<PaymentTransactionResponseDTO> asSender,
                                             List<PaymentTransactionResponseDTO> asReceiver) {
        this.asSender = asSender;
        this.asReceiver = asReceiver;
    }
}
