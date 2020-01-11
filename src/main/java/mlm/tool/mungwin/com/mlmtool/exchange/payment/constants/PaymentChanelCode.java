package mlm.tool.mungwin.com.mlmtool.exchange.payment.constants;

public enum PaymentChanelCode {
    CASH ("Pay with cash"),
    VISA("Pay with Visa."),
    MASTERCARD("Pay with master card."),
    MTN_MOMO("Pay with MTN mobile money."),
    ORANGE_MONEY("Pay with Orange mobile money."),
    PAYPAL("Pay with Paypal."),
    PAYMENT_VOUCHER("Pay with payment voucher"),
    PAYCASH("Pay from your payCash account.");
    private String description;
    PaymentChanelCode(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }
}
