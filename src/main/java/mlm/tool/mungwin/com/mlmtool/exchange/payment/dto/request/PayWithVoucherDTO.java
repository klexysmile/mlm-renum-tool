package mlm.tool.mungwin.com.mlmtool.exchange.payment.dto.request;

public class PayWithVoucherDTO {
    private String registrationCode;
    private String voucherCode;
    private Double payingAmount;
    private String usedBy;

    public PayWithVoucherDTO() {
    }

    public String getVoucherCode() {
        return voucherCode;
    }

    public void setVoucherCode(String voucherCode) {
        this.voucherCode = voucherCode;
    }

    public String getUsedBy() {
        return usedBy;
    }

    public void setUsedBy(String usedBy) {
        this.usedBy = usedBy;
    }

    public Double getPayingAmount() {
        return payingAmount;
    }

    public void setPayingAmount(Double payingAmount) {
        this.payingAmount = payingAmount;
    }

    public String getRegistrationCode() {
        return registrationCode;
    }

    public void setRegistrationCode(String registrationCode) {
        this.registrationCode = registrationCode;
    }
}
