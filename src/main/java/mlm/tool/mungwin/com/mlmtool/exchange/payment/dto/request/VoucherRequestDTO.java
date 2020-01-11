package mlm.tool.mungwin.com.mlmtool.exchange.payment.dto.request;

import javax.validation.constraints.NotNull;

public class VoucherRequestDTO {
    @NotNull(message = "amount is required")
    private Double amount;

    public VoucherRequestDTO() {
    }


    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

}
