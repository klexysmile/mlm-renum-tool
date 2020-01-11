package mlm.tool.mungwin.com.mlmtool.exchange.payment.props;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PaymentProps {
    @Value("${payments.accounts.reserve.create}")
    private  String CREATE_RESERVE_PATH;
    @Value("${payments.service.bonus-password}")
    private  String BONUS_PASS;
    @Value("${payments.service.bonus-registration-code}")
    private String BONUS_REG_CODE;
    @Value("${payments.service.sales-password}")
    private  String SALES_PASS;
    @Value("${payments.service.sales-registration-code}")
    private String SALES_REG_CODE;
    @Value("${payments.accounts.by-admin.create}")
    private String BY_ADMIN_CREATE_ACCOUNT_PATH;
    @Value("${payments.accounts.create}")
    private String USER_CREATE_ACCOUNT_PATH;
    @Value("${payments.accounts.cash.deposit-request}")
    private String DEPOSIT_REQUEST_PATH;
    @Value("${payments.accounts.cash.withdrawal-request}")
    private String WITHDRAWAL_REQUEST_PATH;
    @Value("${payments.accounts.transaction-details}")
    private String ACCOUNT_TRANSACTION_DETAILS_PATH;
    @Value("${payments.accounts.transaction.list-pending}")
    private String PENDING_ACCOUNT_TRANSACTION_PATH;
    @Value("${payments.pay.transaction.confirm}")
    private String ADMIN_CONFIRM_PAYMENT_PATH;
    @Value("${payments.pay.transaction.history}")
    private String PAYMENT_HISTORY_PATH;
    @Value("${payments.pay.transfer}")
    private String TRANSFER_PAY_CASH_PATH;
    @Value("${payments.pay.pay-cash}")
    private String PAY_PAYCASH_PATH;
    @Value("${payments.pay.cash}")
    private String PAY_CASH_PATH;
    @Value("${payments.pay.voucher-create}")
    private String CREATE_VOUCHER_PATH;
    @Value("${payments.pay.voucher}")
    private String PAY_VOUCHER_PATH;
    @Value("${payments.accounts.currencies}")
    private String LOAD_CURRENCY_PATH;

    public PaymentProps() {
    }

    public String getCREATE_RESERVE_PATH() {
        return CREATE_RESERVE_PATH;
    }

    public String getBONUS_PASS() {
        return BONUS_PASS;
    }

    public String getBY_ADMIN_CREATE_ACCOUNT_PATH() {
        return BY_ADMIN_CREATE_ACCOUNT_PATH;
    }

    public String getUSER_CREATE_ACCOUNT_PATH() {
        return USER_CREATE_ACCOUNT_PATH;
    }

    public String getDEPOSIT_REQUEST_PATH() {
        return DEPOSIT_REQUEST_PATH;
    }

    public String getWITHDRAWAL_REQUEST_PATH() {
        return WITHDRAWAL_REQUEST_PATH;
    }

    public String getACCOUNT_TRANSACTION_DETAILS_PATH() {
        return ACCOUNT_TRANSACTION_DETAILS_PATH;
    }

    public String getPENDING_ACCOUNT_TRANSACTION_PATH() {
        return PENDING_ACCOUNT_TRANSACTION_PATH;
    }

    public String getADMIN_CONFIRM_PAYMENT_PATH() {
        return ADMIN_CONFIRM_PAYMENT_PATH;
    }

    public String getPAYMENT_HISTORY_PATH() {
        return PAYMENT_HISTORY_PATH;
    }

    public String getTRANSFER_PAY_CASH_PATH() {
        return TRANSFER_PAY_CASH_PATH;
    }

    public String getPAY_PAYCASH_PATH() {
        return PAY_PAYCASH_PATH;
    }

    public String getPAY_CASH_PATH() {
        return PAY_CASH_PATH;
    }

    public String getCREATE_VOUCHER_PATH() {
        return CREATE_VOUCHER_PATH;
    }

    public String getPAY_VOUCHER_PATH() {
        return PAY_VOUCHER_PATH;
    }

    public String getBONUS_REG_CODE() {
        return BONUS_REG_CODE;
    }

    public String getSALES_PASS() {
        return SALES_PASS;
    }

    public String getSALES_REG_CODE() {
        return SALES_REG_CODE;
    }

    public String getLOAD_CURRENCY_PATH() {
        return LOAD_CURRENCY_PATH;
    }
}
