package mlm.tool.mungwin.com.mlmtool.exchange.payment.dto.request.users;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class PayWithCashUDTO {
    @NotNull(message = "orderId is required")
    private Long orderId;
    @NotBlank(message = "currencySymbol is required")
    private String currencySymbol;

    public PayWithCashUDTO() {
    }


    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }

}
