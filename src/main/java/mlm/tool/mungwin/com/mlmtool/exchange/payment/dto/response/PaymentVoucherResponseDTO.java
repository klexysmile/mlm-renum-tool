package mlm.tool.mungwin.com.mlmtool.exchange.payment.dto.response;


public class PaymentVoucherResponseDTO {
    private Double amount;
    private String code;

    public PaymentVoucherResponseDTO() {
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
