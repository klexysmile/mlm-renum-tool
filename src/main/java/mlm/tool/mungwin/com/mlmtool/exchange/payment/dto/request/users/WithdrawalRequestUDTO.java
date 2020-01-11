package mlm.tool.mungwin.com.mlmtool.exchange.payment.dto.request.users;

public class WithdrawalRequestUDTO extends DepositRequestUDTO {
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
