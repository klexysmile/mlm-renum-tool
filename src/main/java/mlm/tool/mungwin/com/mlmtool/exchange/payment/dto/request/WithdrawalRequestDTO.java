package mlm.tool.mungwin.com.mlmtool.exchange.payment.dto.request;

public class WithdrawalRequestDTO extends DepositRequestDTO {
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
