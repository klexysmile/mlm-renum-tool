package mlm.tool.mungwin.com.mlmtool.exchange.payment.dto.request;


import mlm.tool.mungwin.com.mlmtool.exchange.payment.constants.PaymentChanelCode;

public class PayWithCashDTO {
    private String registrationCode;
    private Double amount;
    private String currencySymbol;
    private String channel;

    public PayWithCashDTO() {
        this.channel = PaymentChanelCode.CASH.toString();
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

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }
}
