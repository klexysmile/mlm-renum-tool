package mlm.tool.mungwin.com.mlmtool.exchange.payment.services;

import mlm.tool.mungwin.com.mlmtool.exchange.payment.PaymentRestClient;
import mlm.tool.mungwin.com.mlmtool.exchange.payment.dto.DepositDTO;
import mlm.tool.mungwin.com.mlmtool.exchange.payment.dto.request.*;
import mlm.tool.mungwin.com.mlmtool.exchange.payment.dto.response.*;
import mlm.tool.mungwin.com.mlmtool.exchange.payment.services.interfaces.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("payCash")
public class PaymentServiceImpl implements PaymentService {
    private PaymentRestClient restClient;

    @Autowired
    public void setRestClient(PaymentRestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    public Boolean autoPay(List<DepositDTO> depositList) {
        return null;
    }

    @Override
    public AccountResponseDTO createOwnAccount(CreateOwnAccountRequestDTO accountRequestDTO) {
        return restClient.createOwnAccount(accountRequestDTO);
    }

    @Override
    public TransactionResponseDTO requestADepositForCash(DepositRequestDTO requestDTO) {
        return restClient.requestADepositForCash(requestDTO);
    }

    @Override
    public PaymentTransactionResponseDTO confirmCashTransaction(ConfirmTransactionRequestDTO requestDTO) {
        return restClient.confirmCashTransaction(requestDTO);
    }

    @Override
    public TransactionResponseDTO requestAWithdrawalForCash(WithdrawalRequestDTO requestDTO) {
        return restClient.requestAWithdrawalForCash(requestDTO);
    }

    @Override
    public PaymentTransactionHistoryResponse getPaymentHistory(PaymentHistoryDTO historyDTO) {
        return restClient.getPaymentHistory(historyDTO);
    }

    @Override
    public TransferResponseDTO transferPayCash(TransferRequestDTO requestDTO) {
        return restClient.transferPayCash(requestDTO);
    }

    @Override
    public TransferResponseDTO payWithPayCash(PayWithPayCashDTO requestDTO, String password) {
        return restClient.payWithPayCash(requestDTO, password);
    }

    @Override
    public TransferResponseDTO payWithCash(PayWithCashDTO cashDTO) {
        return restClient.payWithCash(cashDTO);
    }

    @Override
    public PaymentVoucherResponseDTO createVoucher(VoucherRequestDTO requestDTO) {
        return restClient.createVoucher(requestDTO);
    }

    @Override
    public TransferResponseDTO payWithCashVoucher(PayWithVoucherDTO voucherDTO) {
        return restClient.payWithCashVoucher(voucherDTO);
    }

    @Override
    public CurrencyResponseDTO getPaymentsCurrencies() {
        return restClient.getPaymentCurrencies();
    }
}
