package mlm.tool.mungwin.com.mlmtool.exchange.payment.dto.request.users;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class PayWithVoucherUDTO {
    @NotBlank(message = "voucherCode is required")
    private String voucherCode;
    @NotNull(message = "orderId is required")
    private Long orderId;

    public PayWithVoucherUDTO() {
    }

    public String getVoucherCode() {
        return voucherCode;
    }

    public void setVoucherCode(String voucherCode) {
        this.voucherCode = voucherCode;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}
