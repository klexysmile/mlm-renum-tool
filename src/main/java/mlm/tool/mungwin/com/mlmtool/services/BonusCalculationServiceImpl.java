package mlm.tool.mungwin.com.mlmtool.services;

import mlm.tool.mungwin.com.mlmtool.entities.*;
import mlm.tool.mungwin.com.mlmtool.exceptions.ApiException;
import mlm.tool.mungwin.com.mlmtool.exceptions.ErrorCodes;
import mlm.tool.mungwin.com.mlmtool.exchange.payment.PaymentRestClient;
import mlm.tool.mungwin.com.mlmtool.exchange.payment.dto.request.DepositListing;
import mlm.tool.mungwin.com.mlmtool.exchange.payment.dto.request.TransferRequestDTO;
import mlm.tool.mungwin.com.mlmtool.exchange.payment.props.PaymentProps;
import mlm.tool.mungwin.com.mlmtool.repositories.*;
import mlm.tool.mungwin.com.mlmtool.services.contract.BonusCalculationService;
import mlm.tool.mungwin.com.mlmtool.tasks.MessageProcessorTask;
import mlm.tool.mungwin.com.mlmtool.utils.Parameters;
import org.apache.logging.log4j.LogManager;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.plugin2.message.Message;

import javax.swing.text.html.Option;
import java.lang.reflect.Parameter;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class BonusCalculationServiceImpl implements BonusCalculationService {

    //<editor-fold desc="FIELDS">
    private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger(BonusCalculationServiceImpl.class);

    @Autowired
    PaymentRestClient paymentRestClient;

    @Autowired
    private PaymentProps paymentProps;

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    SettingsRepository settingsRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ProcessPositionRepository processPositionRepository;

    @Autowired
    CustomerAccountRepository customerAccountRepository;

    @Autowired
    AccountMovementsRepository accountMovementsRepository;

    @Autowired
    BonusRepository bonusRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    CustomerLinkRepository customerLinkRepository;
    //</editor-fold>

    public void processMessages() {

        List<Messages> messagesList = messageRepository.findMessagesByStatus(Parameters.TRANSACTION_STATUS_PENDING);

        List<Messages> initiatedMessages = messageRepository.findMessagesByStatus(Parameters.TRANSACTION_STATUS_INITIATED);
        if(initiatedMessages.size() > 0){
            logger.info(" ==> SOME {} MESSAGES FORM LAST OPERATION ARE STILL PENDING. EXITING <== ", initiatedMessages.size());
            return;
        }
        logger.info(" ==> PROCESSING -- {} -- MESSAGES <== ", messagesList.size());
        messagesList.forEach((messages -> {
            JSONObject messageJSON = new JSONObject(messages.getMessage());
            switch (messageJSON.getString("type")) {
                case Parameters.MESSAGE_TYPE_REGISTRATION:
                    processNewRegistration(messageJSON, messages);
                    break;
                default:

                    break;
            }
        }));

    }


    public boolean processNewRegistration(JSONObject message, Messages messageEntity){

        //FOR NEW REGISTRATION THE FOLLOWING WILL BE DONE
        //=> Direct referral bonus given to referrer
        //=> Two points accorded to each customer that is not yet at the top
        //=> Qualification bonuses calculator for each user that gets a point increment
        JSONObject downLineJSON = message.getJSONObject("down_line");
        JSONObject upLineJSON = message.getJSONObject("up_line");
        JSONObject referrerJSON = message.getJSONObject("referrer");

        //Add two points to down-line customer
        Optional<Customer> downCustomerOptional = customerRepository.findById(downLineJSON.getLong("customer_id"));
        if(!downCustomerOptional.isPresent()){
            throw new ApiException("No down-line customer", HttpStatus.FAILED_DEPENDENCY, ErrorCodes.CUSTOMER_NOT_FOUND.name(), "Manually check message details against customers table");
        } else{
            Optional<CustomerAccount> downLineAccountOptional = customerAccountRepository.findCustomerAccountByCustomerId(downLineJSON.getLong("customer_id"));
            if(downLineAccountOptional.isPresent()){
                CustomerAccount downLineAccount = downLineAccountOptional.get();
                downLineAccount.setPoints(downLineAccount.getPoints() + Parameters.VALUE_REGISTRATION_POINTS);
                int networkSize = downLineAccount.getNetworkSize() == null ? 0 : downLineAccount.getNetworkSize();
                downLineAccount.setNetworkSize(networkSize + 1);
                customerAccountRepository.save(downLineAccount);
            }else{
                CustomerAccount downLineAccount = new CustomerAccount();
                downLineAccount.setPoints(Parameters.VALUE_REGISTRATION_POINTS);
                downLineAccount.setCustomerId(downCustomerOptional.get().getId());
                downLineAccount.setTotalBalance(0.0);
                downLineAccount.setAvailableBalance(0.0);
                downLineAccount.setNetworkSize(0);
                customerAccountRepository.save(downLineAccount);
            }
        }

        //Issue referral bonus if available
        Optional<Customer> refCustomerOptional = customerRepository.findById(referrerJSON.getLong("customer_id"));
        if(!refCustomerOptional.isPresent()) {
            throw new ApiException("No referrer customer", HttpStatus.FAILED_DEPENDENCY, ErrorCodes.CUSTOMER_NOT_FOUND.name(), "Manually check message details against customers table");
        }
        boolean hasIssuedDRBonus = issueDirectReferralBonus(refCustomerOptional.get(), messageEntity);

        if(!hasIssuedDRBonus) {
            throw new ApiException("An error occurred while issues DRB", HttpStatus.FAILED_DEPENDENCY, ErrorCodes.CUSTOMER_NOT_FOUND.name(), "Check application logs");
        }

        //Backtrack and process customer tree
        Optional<Customer> upCustomerOptional = customerRepository.findById(upLineJSON.getLong("customer_id"));
        if(!upCustomerOptional.isPresent()) {
            throw new ApiException("No up-line customer", HttpStatus.FAILED_DEPENDENCY, ErrorCodes.CUSTOMER_NOT_FOUND.name(), "Manually check message details against customers table");
        }
        processCustomerUpLinePath(upCustomerOptional.get(), messageEntity);

        return true;
    }

    private boolean issueDirectReferralBonus(Customer customer, Messages message){

        if(message.getStatus().equals(Parameters.TRANSACTION_STATUS_INITIATED)){
            return true;
        }

        if(customer.getPackageId().getDirectReferralPercentage() == null){
            message.setStatus(Parameters.TRANSACTION_STATUS_INITIATED);
            messageRepository.save(message);
            return true;
        }

        double directReferralBonusPercentage = customer.getPackageId().getDirectReferralPercentage();

        Optional<Settings> registrationFeesSettings = settingsRepository.findByName(Parameters.SETTINGS_KEY_REGISTRATION_FEE);
        double registrationFees = Parameters.VALUE_REGISTRATION_FEES;
        if(registrationFeesSettings.isPresent())
            registrationFees = Double.valueOf(registrationFeesSettings.get().getValue());

        double amount = (directReferralBonusPercentage/100.0) * registrationFees;

        //Credit customer account with amount
        Optional<CustomerAccount> customerAccountOptional = customerAccountRepository.findCustomerAccountByCustomerId(customer.getId());
        CustomerAccount customerAccount;
        if(customerAccountOptional.isPresent()){
            customerAccount = customerAccountOptional.get();
        }else{
            customerAccount = new CustomerAccount();
            customerAccount.setCustomerId(customer.getId());
            customerAccount.setNetworkSize(0);
            customerAccount.setPoints(0);
            customerAccount.setAvailableBalance(0.0);
            customerAccount.setTotalBalance(0.0);
        }
        //Customer account
        try {
            logger.info("TRANSFERRING {} AS DIRECT REFERRAL BONUS TO CUSTOMER {} - {} {}", amount, customer.getId(), customer.getFirstName(), customer.getLastName());
            creditCustomerAccount(customerAccount, amount, Parameters.PAYMENT_LOCATION_AUTOMATIC, Parameters.BONUS_TYPE_REGISTRATION);
            transferBonusPaycashToCustomerAccount(customer, amount);
            message.setStatus(Parameters.TRANSACTION_STATUS_INITIATED);
            messageRepository.save(message);
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private boolean processCustomerUpLinePath(Customer customer, Messages messageEntity){
        //Mark index of where processing ended
        Optional<ProcessPosition> optionalProcessPosition = processPositionRepository.findProcessPositionByMessageIdId(messageEntity.getId());

        ProcessPosition processPosition = optionalProcessPosition.orElse(new ProcessPosition());
        processPosition.setCursorIndex(customer.getId());
        processPosition.setMessageId(messageEntity);
        processPositionRepository.save(processPosition);

        //Now from current customer, add two points until we get to level 2 since 1 is at the root
        Optional<CustomerAccount> customerAccountOptional = customerAccountRepository.findCustomerAccountByCustomerId(customer.getId());
        CustomerAccount customerAccount;
        if(customerAccountOptional.isPresent()){
            customerAccount = customerAccountOptional.get();
        }else{
            customerAccount = new CustomerAccount();
            customerAccount.setCustomerId(customer.getId());
        }

        Integer currentPoints = customerAccountRepository.sumCustomerAccountPoints(customerAccount.getId()) != null ? customerAccountRepository.sumCustomerAccountPoints(customerAccount.getId()).intValue() : 0;
        customerAccount.setPoints(currentPoints + Parameters.VALUE_REGISTRATION_POINTS);
        customerAccount.setNetworkSize((customerAccount.getNetworkSize() == null ? 0 : customerAccount.getNetworkSize()) + 1);
        customerAccount.setLastUpdate(new Date());
        customerAccountRepository.save(customerAccount);

        verifyBonusQualification(customerAccount, customer);

        if(customer.getUpLink() != null){
            processCustomerUpLinePath(customer.getUpLink().getParentId(), messageEntity);
        }else{
            //All have been processed
            messageEntity.setStatus(Parameters.TRANSACTION_STATUS_COMPLETED);
            messageEntity.setUpdatedAt(new Date());
            messageRepository.save(messageEntity);
        }

        return true;
    }

    private void verifyBonusQualification(CustomerAccount customerAccount, Customer customer) {

        Integer currentPoints = customerAccount.getPoints();
        if (customer.getPackageId().getNextLevel() == null){
            return;
//            throw new ApiException("Customer doesn't have a package or package doesn't have next level", HttpStatus.FAILED_DEPENDENCY, ErrorCodes.CUSTOMER_NOT_FOUND.name(), "Check customer registration");
        }
        Integer nextLevelPoints = customer.getPackageId().getNextLevel().getQualificationPoints();

        if (currentPoints >= nextLevelPoints) {
            //Issue qualification bonus and change customers package
            customer.setPackageId(customer.getPackageId().getNextLevel());
            customerRepository.save(customer);

            Optional<Bonus> optionalBonus = bonusRepository.findBonusByTransactionTypeNameAndPackageId(Parameters.TRANSACTION_TYPE_PRODUCT_BONUS, customer.getPackageId().getId());
            if (!optionalBonus.isPresent())
                throw new ApiException("Customer doesn't have a package or package doesn't have next level", HttpStatus.FAILED_DEPENDENCY, ErrorCodes.CUSTOMER_NOT_FOUND.name(), "Check customer registration");

            Bonus bonus = optionalBonus.get();
            logger.info("AWARDING QUALIFICATION BONUS OF {} TO ACCOUNT {} BELONGING TO {} {}", bonus.getAmount(), customerAccount.getId(), customer.getFirstName(), customer.getLastName());

            //Credit customer account with bonus
            Integer teamSize = customerLinkRepository.countAllByParentId(customer);
            if(teamSize > 2){
                creditCustomerAccount(customerAccount, bonus.getAmount(), Parameters.PAYMENT_LOCATION_AUTOMATIC, Parameters.TRANSACTION_TYPE_PRODUCT_BONUS);
                transferBonusPaycashToCustomerAccount(customer, bonus.getAmount());
            }else{
                //Program for bonus to be paid when appropriate number of referrals have been made
                registerPendingBonus(customerAccount.getId(), customer.getId(), bonus.getAmount(), Parameters.TRANSACTION_TYPE_PRODUCT_BONUS, Parameters.BONUS_CONDITION_REGISTER_THREE);
            }
        }

    }

    @Transactional
    public void creditCustomerAccount(CustomerAccount customerAccount, Double amount, String paidAt, String type){
        //Record account movements
        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setCreatedAt(new Date());
        transaction.setStatus(Parameters.TRANSACTION_STATUS_INITIATED);
        transaction.setCustomerId(customerAccount.getId());
        transaction.setPaidAt(paidAt);
        transaction.setPaymentToken("");
        String transactionCode = new Date().getTime() + "X" + type.substring(0, 2);
        transaction.setCode(transactionCode);
        transactionRepository.save(transaction);

        AccountMovements accountMovements = new AccountMovements();
        accountMovements.setCustomerAccount(customerAccount);
        accountMovements.setTransaction(transaction);
        accountMovements.setMotive(type);
        accountMovements.setDescription("");
        accountMovements.setType(Parameters.ACCOUNT_MOVEMENT_CREDIT);
        accountMovementsRepository.save(accountMovements);

        customerAccount.setTotalBalance((customerAccount.getTotalBalance() == null ? 0 : customerAccount.getTotalBalance()) + amount);
        customerAccount.setAvailableBalance((customerAccount.getAvailableBalance() == null ? 0 : customerAccount.getAvailableBalance()) + amount);
        customerAccountRepository.save(customerAccount);

        //Update transaction status
        transaction.setStatus(Parameters.TRANSACTION_STATUS_PAID);
        transactionRepository.save(transaction);

    }

    public void transferBonusPaycashToCustomerAccount(Customer recipientCustomer, Double amount){

        TransferRequestDTO transferRequestDTO = new TransferRequestDTO();
        transferRequestDTO.setInitiatedBy(Parameters.PAYMENT_LOCATION_AUTOMATIC);
        transferRequestDTO.setSenderRegCode(paymentProps.getBONUS_REG_CODE());
        transferRequestDTO.setPassword(paymentProps.getBONUS_PASS());
        transferRequestDTO.setCurrency("PCH");
        transferRequestDTO.setComment("AUTOMATIC-PAYMENT");

        DepositListing dep = new DepositListing(recipientCustomer.getRegistrationCode(), amount);
        List<DepositListing> depositListings = new ArrayList<>();
        depositListings.add(dep);
        transferRequestDTO.setReceivers(depositListings);

        paymentRestClient.transferPayCash(transferRequestDTO);

    }

    private void registerPendingBonus(Long accountId, Long customerId, Double amount, String type, String condition){
        JSONObject pendingBonusJSON = new JSONObject();
        pendingBonusJSON.put("account_id", accountId);
        pendingBonusJSON.put("customer_id", customerId);
        pendingBonusJSON.put("amount", amount);
        pendingBonusJSON.put("type", type);
        pendingBonusJSON.put("condition",condition);

        try {
            Messages message = new Messages();
            message.setCreatedAt(new Date());
            message.setStatus(Parameters.TRANSACTION_STATUS_PENDING);
            message.setMessage(pendingBonusJSON.toString());
            message.setType(type);

            messageRepository.save(message);
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }


    public void processBonusMessages() {
        List<Messages> messagesList = messageRepository.findMessagesByStatusAndType(Parameters.TRANSACTION_STATUS_PENDING, Parameters.MESSAGE_TYPE_BONUS);

        List<Messages> initiatedMessages = messageRepository.findMessagesByStatusAndType(Parameters.TRANSACTION_STATUS_INITIATED, Parameters.MESSAGE_TYPE_BONUS);
        if(initiatedMessages.size() > 0){
            logger.info(" ==> SOME {} MESSAGES FORM LAST OPERATION ARE STILL PENDING. EXITING <== ", initiatedMessages.size());
            return;
        }

        logger.info(" ==> PROCESSING -- {} -- MESSAGES <== ", messagesList.size());
        messagesList.forEach((messages -> {
            JSONObject messageJSON = new JSONObject(messages.getMessage());
            switch (messageJSON.getString("condition")) {
                case Parameters.BONUS_CONDITION_REGISTER_THREE:
                    processThreeDownLinesBonus(messageJSON, messages);
                    break;
                default:

                    break;
            }
        }));
    }


    private void processThreeDownLinesBonus(JSONObject messageJSON, Messages messages) {

        Optional<Customer> customerOptional = customerRepository.findById(messageJSON.getLong("customer_id"));
        if(!customerOptional.isPresent())
            return;

        Customer customer = customerOptional.get();
        Integer teamSize = customerLinkRepository.countAllByParentId(customer);
        if(teamSize > 2){
            messages.setStatus(Parameters.TRANSACTION_STATUS_INITIATED);
            messageRepository.save(messages);
            Optional<CustomerAccount> customerAccountOptional = customerAccountRepository.findById(messageJSON.getLong("account_id"));
            if(!customerAccountOptional.isPresent())
                return;
            CustomerAccount account = customerAccountOptional.get();

            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Double amount = messageJSON.getDouble("amount");
                creditCustomerAccount(account, amount, sdf.format(new Date()), Parameters.TRANSACTION_TYPE_PRODUCT_BONUS);
                transferBonusPaycashToCustomerAccount(customer, amount);

                messages.setStatus(Parameters.TRANSACTION_STATUS_COMPLETED);
                messageRepository.save(messages);
            } catch (JSONException e) {
                logger.error(e.getMessage());
                e.printStackTrace();
            }
        }

    }
}
