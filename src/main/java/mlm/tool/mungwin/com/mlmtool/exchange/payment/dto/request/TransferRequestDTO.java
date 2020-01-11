package mlm.tool.mungwin.com.mlmtool.exchange.payment.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class TransferRequestDTO {
    private String initiatedBy;
    private String currency;
    private String senderRegCode;
    private String password;
    private List<DepositListing> receivers;
}
