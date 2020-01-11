package mlm.tool.mungwin.com.mlmtool.exchange.payment.dto.request.users;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class PayWithPayCashUDTO {
    @NotNull(message = "orderId is required")
    private Long orderId;
    @NotBlank(message = "password is required")
    private String password;

    public PayWithPayCashUDTO() {
    }


    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
