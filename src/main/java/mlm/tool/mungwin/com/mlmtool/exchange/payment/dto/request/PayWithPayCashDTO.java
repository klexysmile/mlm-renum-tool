package mlm.tool.mungwin.com.mlmtool.exchange.payment.dto.request;

public class PayWithPayCashDTO {
    private String registrationCode;
    private Double amount;

    public PayWithPayCashDTO() {
    }

    public String getRegistrationCode() {
        return registrationCode;
    }

    public void setRegistrationCode(String registrationCode) {
        this.registrationCode = registrationCode;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
