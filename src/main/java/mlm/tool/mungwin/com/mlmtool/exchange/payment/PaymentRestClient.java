package mlm.tool.mungwin.com.mlmtool.exchange.payment;

import com.fasterxml.jackson.databind.ObjectMapper;
import mlm.tool.mungwin.com.mlmtool.exceptions.ApiException;
import mlm.tool.mungwin.com.mlmtool.exceptions.ErrorCodes;
import mlm.tool.mungwin.com.mlmtool.exceptions.SecurityErrorCodes;
import mlm.tool.mungwin.com.mlmtool.exchange.payment.dto.request.*;
import mlm.tool.mungwin.com.mlmtool.exchange.payment.dto.response.*;
import mlm.tool.mungwin.com.mlmtool.exchange.payment.props.PaymentProps;
import mlm.tool.mungwin.com.mlmtool.services.utilities.autopay.jwtengine.JWTService;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.*;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;

@Configuration
public class PaymentRestClient {
    private PaymentProps paymentProps;
    private Logger logger = LoggerFactory.getLogger(PaymentRestClient.class);
    private RestTemplate restTemplate;
    private static final String SERVER_RESPONDED = "Server Responded with";
    private JWTService jwtService;
    @Autowired
    public PaymentRestClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Autowired
    @Qualifier(value = "autoBonusJwtautoBonusJwt")
    public void setJwtService(JWTService jwtService) {
        this.jwtService = jwtService;
    }

    @Autowired
    public void setPaymentProps(PaymentProps paymentProps) {
        this.paymentProps = paymentProps;
    }

    public AccountResponseDTO createOwnAccount(CreateOwnAccountRequestDTO ownAccountRequestDTO) {
        HttpHeaders headers = getSystemJwtAuthHeader();
        HttpEntity<CreateOwnAccountRequestDTO> request = new HttpEntity<>(ownAccountRequestDTO, headers);
        try {
            ResponseEntity<AccountResponseDTO> entity = restTemplate.exchange(
                    generateUri(paymentProps.getUSER_CREATE_ACCOUNT_PATH()),
                    HttpMethod.POST, request, AccountResponseDTO.class);
            logger.info("Status Code: {}", entity.getStatusCode().toString());
            if (entity.getStatusCode() == HttpStatus.OK) {
                return entity.getBody();
            }
        } catch (RestClientResponseException httpEx) {
            handleRestClientExceptions(httpEx);
        }
        return null;
    }

    public TransactionResponseDTO requestADepositForCash(DepositRequestDTO requestDTO) {
        HttpHeaders headers = getSystemJwtAuthHeader();
        HttpEntity<DepositRequestDTO> request = new HttpEntity<>(requestDTO, headers);
        return postForTransaction(request, paymentProps.getDEPOSIT_REQUEST_PATH());
    }

    public PaymentTransactionResponseDTO confirmCashTransaction(ConfirmTransactionRequestDTO requestDTO) {
        HttpHeaders headers = getSystemJwtAuthHeader();
        HttpEntity<ConfirmTransactionRequestDTO> request = new HttpEntity<>(requestDTO, headers);
        return postForPaymentTransaction(request, paymentProps.getADMIN_CONFIRM_PAYMENT_PATH());
    }
    public TransactionResponseDTO requestAWithdrawalForCash(WithdrawalRequestDTO requestDTO) {
        HttpHeaders headers = createAuthHeader(requestDTO.getPassword());
        String authHeader = "Bearer " + jwtService.generateSignedToken();
        headers.set("Authorization", authHeader);
        HttpEntity<WithdrawalRequestDTO> request = new HttpEntity<>(requestDTO, headers);
        return postForTransaction(request, paymentProps.getWITHDRAWAL_REQUEST_PATH());

    }

    public PaymentTransactionHistoryResponse getPaymentHistory(PaymentHistoryDTO historyDTO) {
        HttpHeaders headers = getSystemJwtAuthHeader();
        HttpEntity<PaymentHistoryDTO> request = new HttpEntity<>(historyDTO, headers);
        try {
            ResponseEntity<PaymentTransactionHistoryResponse> entity = restTemplate.exchange(
                    generateUri(paymentProps.getPAYMENT_HISTORY_PATH()),
                    HttpMethod.POST,
                    request, PaymentTransactionHistoryResponse.class);
            logger.info("Status Code: {}", entity.getStatusCode().toString());
            if (entity.getStatusCode() == HttpStatus.OK) {
                return entity.getBody();
            }
        } catch (RestClientResponseException httpEx) {
            handleRestClientExceptions(httpEx);
        }
        return null;
    }

    public TransferResponseDTO transferPayCash(TransferRequestDTO requestDTO) {
        HttpHeaders headers = createAuthHeader(requestDTO.getPassword());
        String authHeader = "Bearer " + jwtService.generateSignedToken();
        headers.set("Authorization", authHeader);
        HttpEntity<TransferRequestDTO> request = new HttpEntity<>(requestDTO, headers);
        try {
            ResponseEntity<TransferResponseDTO> entity = restTemplate.exchange(
                    generateUri(paymentProps.getTRANSFER_PAY_CASH_PATH()),
                    HttpMethod.POST,
                    request, TransferResponseDTO.class);
            logger.info("Status Code: {}", entity.getStatusCode().toString());
            if (entity.getStatusCode() == HttpStatus.OK) {
                return entity.getBody();
            }
        } catch (RestClientResponseException httpEx) {
            handleRestClientExceptions(httpEx);
        }
        return null;
    }

    public TransferResponseDTO payWithPayCash(PayWithPayCashDTO requestDTO, String password) {
        HttpHeaders headers = createAuthHeader(password);
        String authHeader = "Bearer " + jwtService.generateSignedToken();
        headers.set("Authorization", authHeader);
        HttpEntity<PayWithPayCashDTO> request = new HttpEntity<>(requestDTO, headers);
        return postForTransferResponse(request, paymentProps.getPAY_PAYCASH_PATH());
    }

    public TransferResponseDTO payWithCash(PayWithCashDTO cashDTO) {
        HttpHeaders headers = getSystemJwtAuthHeader();
        HttpEntity<PayWithCashDTO> request = new HttpEntity<>(cashDTO, headers);
        return postForTransferResponse(request, paymentProps.getPAY_CASH_PATH());
    }

    public PaymentVoucherResponseDTO createVoucher(VoucherRequestDTO requestDTO){
        HttpHeaders headers = createAuthHeader(paymentProps.getSALES_PASS());
        String jwt = jwtService.generateSignedToken();
        headers.setBearerAuth(jwt);
        HttpEntity<VoucherRequestDTO> request = new HttpEntity<>(requestDTO, headers);
        try {
            ResponseEntity<PaymentVoucherResponseDTO> entity = restTemplate.exchange(
                    generateUri(paymentProps.getCREATE_VOUCHER_PATH()),
                    HttpMethod.POST,
                    request,
                    PaymentVoucherResponseDTO.class
            );logger.info("Status Code: {}", entity.getStatusCode().toString());
            if (entity.getStatusCode() == HttpStatus.OK) {
                return entity.getBody();
            }
        } catch (RestClientResponseException httpEx) {
            handleRestClientExceptions(httpEx);
        }
        return null;
    }

    public TransferResponseDTO payWithCashVoucher(PayWithVoucherDTO voucherDTO) {
        HttpHeaders headers = getSystemJwtAuthHeader();
        HttpEntity<PayWithVoucherDTO> request = new HttpEntity<>(voucherDTO, headers);
        return postForTransferResponse(request, paymentProps.getPAY_VOUCHER_PATH());

    }
    public CurrencyResponseDTO getPaymentCurrencies() {
        HttpHeaders headers = getSystemJwtAuthHeader();
        HttpEntity request = new HttpEntity<>(null, headers);
        try {
            ResponseEntity<CurrencyResponseDTO> entity = restTemplate.exchange(
                    generateUri(paymentProps.getLOAD_CURRENCY_PATH()),
                    HttpMethod.GET,
                    request,
                    CurrencyResponseDTO.class
            );
            logger.info("Status Code: {}", entity.getStatusCode().toString());
            if (entity.getStatusCode() == HttpStatus.OK) {
                return entity.getBody();
            }
        } catch (RestClientResponseException httpEx) {
            handleRestClientExceptions(httpEx);
        }
        return null;
    }
    private TransferResponseDTO postForTransferResponse(HttpEntity request, String uri) {
        try {
            ResponseEntity<TransferResponseDTO> entity = restTemplate.exchange(
                    generateUri(uri),
                    HttpMethod.POST,
                    request, TransferResponseDTO.class);
            logger.info("Status Code: {}", entity.getStatusCode().toString());
            if (entity.getStatusCode() == HttpStatus.OK) {
                return entity.getBody();
            }
        } catch (RestClientResponseException httpEx) {
            handleRestClientExceptions(httpEx);
        }
        return null;
    }

    private PaymentTransactionResponseDTO postForPaymentTransaction(HttpEntity request, String uri) {
        try {
            ResponseEntity<PaymentTransactionResponseDTO> entity = restTemplate.exchange(
                    generateUri(uri),
                    HttpMethod.POST,
                    request, PaymentTransactionResponseDTO.class);
            logger.info("Status Code: {}", entity.getStatusCode().toString());
            if (entity.getStatusCode() == HttpStatus.OK) {
                return entity.getBody();
            }
        } catch (RestClientResponseException httpEx) {
            handleRestClientExceptions(httpEx);
        }
        return null;
    }
    private TransactionResponseDTO postForTransaction(HttpEntity request, String uri) {
        try {
            ResponseEntity<TransactionResponseDTO> entity = restTemplate.exchange(
                    generateUri(uri),
                    HttpMethod.POST,
                    request, TransactionResponseDTO.class);
            logger.info("Status Code: {}", entity.getStatusCode().toString());
            if (entity.getStatusCode() == HttpStatus.OK) {
                return entity.getBody();
            }
        } catch (RestClientResponseException httpEx) {
            handleRestClientExceptions(httpEx);
        }
        return null;
    }

    private HttpHeaders createAuthHeader(String password) {
        return new HttpHeaders(){{
            String auth = password.toUpperCase() + ":" + password;
            byte[] encodeAuth = Base64.encodeBase64(
                    auth.getBytes(StandardCharsets.US_ASCII)
            );
            String authHeader = "Basic " + new String(encodeAuth);
            set("Customer-Authorization", authHeader);
        }};
    }
    private HttpHeaders getSystemJwtAuthHeader() {
        logger.info("------<Generating system token>-----");
        return new HttpHeaders(){{
            String authHeader = "Bearer " + jwtService.generateSignedToken();
            set("Authorization", authHeader);
        }};
    }

    private HashMap getRestClientError(String restClientResponseBodyAsString) {
        HashMap restClientErrorMap;
        try {
            restClientErrorMap = new ObjectMapper().readValue(restClientResponseBodyAsString, HashMap.class);
        } catch (Exception ex){
            throw new ApiException(SecurityErrorCodes.FAILED_TO_DECODE_HTTPCLIENT_EXCEPTION.toString(), HttpStatus.INTERNAL_SERVER_ERROR, ErrorCodes.ERROR.name(), "");
        }
        return restClientErrorMap;
    }

    private String getRestClientErrorFieldValue(String fieldName, String restClientResponseBodyAsString) {
        Object fieldObj = getRestClientError(restClientResponseBodyAsString).get(fieldName);
        return fieldObj instanceof String ? (String) fieldObj : null;
    }

    private void handleRestClientExceptions(RestClientResponseException httpEx) {
        throw new ApiException(
                getRestClientErrorFieldValue("message", httpEx.getResponseBodyAsString()),
                HttpStatus.valueOf(httpEx.getRawStatusCode()),
                getRestClientErrorFieldValue("errorCode", httpEx.getResponseBodyAsString()),
                getRestClientErrorFieldValue("devHint", httpEx.getResponseBodyAsString())
        );
    }

    private String generateUri(String uri) {
        logger.info("Contacting server: {}", uri);
        return uri;
    }
}
