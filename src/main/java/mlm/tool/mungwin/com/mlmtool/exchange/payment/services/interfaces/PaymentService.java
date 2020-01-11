package mlm.tool.mungwin.com.mlmtool.exchange.payment.services.interfaces;

import mlm.tool.mungwin.com.mlmtool.exchange.payment.dto.DepositDTO;
import mlm.tool.mungwin.com.mlmtool.exchange.payment.dto.request.*;
import mlm.tool.mungwin.com.mlmtool.exchange.payment.dto.response.*;

import java.util.List;

public interface PaymentService {
    Boolean autoPay(List<DepositDTO> depositList);
    AccountResponseDTO createOwnAccount(CreateOwnAccountRequestDTO accountRequestDTO);
    TransactionResponseDTO requestADepositForCash(DepositRequestDTO requestDTO);
    PaymentTransactionResponseDTO confirmCashTransaction(ConfirmTransactionRequestDTO requestDTO);
    TransactionResponseDTO requestAWithdrawalForCash(WithdrawalRequestDTO requestDTO);
    PaymentTransactionHistoryResponse getPaymentHistory(PaymentHistoryDTO historyDTO);
    TransferResponseDTO transferPayCash(TransferRequestDTO requestDTO);
    TransferResponseDTO payWithPayCash(PayWithPayCashDTO requestDTO, String password);
    TransferResponseDTO payWithCash(PayWithCashDTO cashDTO);
    PaymentVoucherResponseDTO createVoucher(VoucherRequestDTO requestDTO);
    TransferResponseDTO payWithCashVoucher(PayWithVoucherDTO voucherDTO);
    CurrencyResponseDTO getPaymentsCurrencies();
}
