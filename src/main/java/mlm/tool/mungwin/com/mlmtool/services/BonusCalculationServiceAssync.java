package mlm.tool.mungwin.com.mlmtool.services;

import io.swagger.models.auth.In;
import mlm.tool.mungwin.com.mlmtool.entities.*;
import mlm.tool.mungwin.com.mlmtool.entities.relationships.Invitation;
import mlm.tool.mungwin.com.mlmtool.entities.relationships.Referral;
import mlm.tool.mungwin.com.mlmtool.exceptions.ApiException;
import mlm.tool.mungwin.com.mlmtool.exceptions.ErrorCodes;
import mlm.tool.mungwin.com.mlmtool.exchange.payment.PaymentRestClient;
import mlm.tool.mungwin.com.mlmtool.exchange.payment.dto.request.DepositListing;
import mlm.tool.mungwin.com.mlmtool.exchange.payment.dto.request.TransferRequestDTO;
import mlm.tool.mungwin.com.mlmtool.exchange.payment.props.PaymentProps;
import mlm.tool.mungwin.com.mlmtool.node.NCustomer;
import mlm.tool.mungwin.com.mlmtool.repositories.*;
import mlm.tool.mungwin.com.mlmtool.repositories.node.InvitationRepository;
import mlm.tool.mungwin.com.mlmtool.repositories.node.NCustomerRepository;
import mlm.tool.mungwin.com.mlmtool.repositories.node.ReferralRepository;
import mlm.tool.mungwin.com.mlmtool.services.contract.BonusCalculationService;
import mlm.tool.mungwin.com.mlmtool.utils.Parameters;
import org.apache.logging.log4j.LogManager;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Qualifier("assync")
public class BonusCalculationServiceAssync implements BonusCalculationService {

    //<editor-fold desc="FIELDS">
    private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger(BonusCalculationServiceAssync.class);

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

    @Autowired
    NCustomerRepository customerNodeRepository;

    @Autowired
    ReferralRepository referralRepository;

    @Autowired
    InvitationRepository invitationRepository;

    @Autowired
    PackagesRepository packageRepository;
    //</editor-fold>

    @Transactional
    public boolean processNewRegistration(JSONObject message, Map<Customer, Double> customerPayableMap) {

        //FOR NEW REGISTRATION THE FOLLOWING WILL BE DONE
        //=> Direct referral bonus given to referrer
        //=> Two points accorded to each customer that is not yet at the top
        //=> Qualification bonuses calculator for each user that gets a point increment
        JSONObject downLineJSON = message.getJSONObject("down_line");
        JSONObject upLineJSON = message.getJSONObject("up_line");
        JSONObject referrerJSON = message.getJSONObject("referrer");

        //Add two points to down-line customer
        Optional<Customer> downCustomerOptional = customerRepository.findById(downLineJSON.getLong("customer_id"));
        if (!downCustomerOptional.isPresent()) {
            throw new ApiException("No down-line customer", HttpStatus.FAILED_DEPENDENCY, ErrorCodes.CUSTOMER_NOT_FOUND.name(), "Manually check message details against customers table");
        } else {
            Optional<CustomerAccount> downLineAccountOptional = customerAccountRepository.findCustomerAccountByCustomerId(downLineJSON.getLong("customer_id"));
            if (downLineAccountOptional.isPresent()) {
                CustomerAccount downLineAccount = downLineAccountOptional.get();
                downLineAccount.setPoints(downLineAccount.getPoints() + Parameters.VALUE_REGISTRATION_POINTS);
                int networkSize = downLineAccount.getNetworkSize() == null ? 0 : downLineAccount.getNetworkSize();
                downLineAccount.setNetworkSize(networkSize + 1);
                customerAccountRepository.save(downLineAccount);
            } else {
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
        if (!refCustomerOptional.isPresent()) {
            throw new ApiException("No referrer customer", HttpStatus.FAILED_DEPENDENCY, ErrorCodes.CUSTOMER_NOT_FOUND.name(), "Manually check message details against customers table");
        }

        saveCustomerInvitation(refCustomerOptional.get(), downCustomerOptional.get());
        boolean hasIssuedDRBonus = issueDirectReferralBonus(refCustomerOptional.get(), customerPayableMap);

        if (!hasIssuedDRBonus) {
            throw new ApiException("An error occurred while issues DRB", HttpStatus.FAILED_DEPENDENCY, ErrorCodes.CUSTOMER_NOT_FOUND.name(), "Check application logs");
        }

        //Backtrack and process customer tree
        Optional<Customer> upCustomerOptional = customerRepository.findById(upLineJSON.getLong("customer_id"));
        if (!upCustomerOptional.isPresent()) {
            throw new ApiException("No up-line customer", HttpStatus.FAILED_DEPENDENCY, ErrorCodes.CUSTOMER_NOT_FOUND.name(), "Manually check message details against customers table");
        }

        if (saveCustomerRelation(upCustomerOptional.get(), downCustomerOptional.get()))
            processCustomerUpLinePath(upCustomerOptional.get(), customerPayableMap);

        return true;
    }

    private boolean saveCustomerRelation(Customer upCustomer, Customer downCustomer) {

        NCustomer upNode = getOrCreateCustomerNode(upCustomer);
        NCustomer downNode = getOrCreateCustomerNode(downCustomer);

        List<CustomerLink> customerLinks = customerLinkRepository.getByChildIdAndParentId(downCustomer, upCustomer);
        if (customerLinks.isEmpty())
            throw new ApiException("Failed to add customer-link record, can't continue with bonus calculation",
                    HttpStatus.FAILED_DEPENDENCY, ErrorCodes.MISSING_LINK.name(), "Check customer links table for to verify it");

        CustomerLink customerLink = customerLinks.get(0);

        /*Referral referral = Referral.builder()
                .downLine(downNode)
                .upLine(upNode)
                .position(customerLink.getPosition())
                .build();*/
        List<Referral> optionalReferral = referralRepository.findFirstByUpLineCustomerIdAndDownLineCustomerId(upNode.getCustomerId(), downNode.getCustomerId());
        if(optionalReferral.isEmpty()){
            Referral referral = new Referral();
            referral.setDownLine(downNode);
            referral.setUpLine(upNode);
            referral.setPosition(customerLink.getPosition());

            referralRepository.save(referral);
        }

        return true;
    }

    private boolean saveCustomerInvitation(Customer member, Customer invitee) {

        NCustomer memberNode = getOrCreateCustomerNode(member);
        NCustomer inviteeNode = getOrCreateCustomerNode(invitee);

        /*Invitation invitation = Invitation.builder().build().builder()
                .invitee(inviteeNode)
                .member(memberNode)
                .build();*/
        Optional<NCustomer> memberNodeOptional = customerNodeRepository.findByCustomerId(member.getId());
        Optional<NCustomer> inviteeNodeOptional = customerNodeRepository.findByCustomerId(invitee.getId());

        Invitation invitation = new Invitation();
        invitation.setInvitee(inviteeNodeOptional.orElse(getCustomerNode(invitee)));
        invitation.setMember(memberNodeOptional.orElse(getCustomerNode(member)));

        invitationRepository.save(invitation);

        return true;
    }

    private NCustomer getOrCreateCustomerNode(Customer upCustomer) {
        NCustomer upNode;
        Optional<NCustomer> upNodeOptional = customerNodeRepository.findByCustomerId(upCustomer.getId());

        if (!upNodeOptional.isPresent()) {
            //Create node
            upNode = getCustomerNode(upCustomer);
            customerNodeRepository.save(upNode);
        } else {
            upNode = upNodeOptional.get();
        }
        return upNode;
    }

    public NCustomer getCustomerNode(Customer customer) {

        NCustomer node = new NCustomer();
        node.setAuthUuid(customer.getAuthUuid());
        node.setAddress(customer.getAddress());
        node.setBirthDate(customer.getBirthDate() == null ? 0L : customer.getBirthDate().getTime());
        node.setBirthPlace(customer.getBirthPlace());
        node.setCity(customer.getCity());
        node.setCountry(customer.getCountry());
        node.setEmail(customer.getEmail());
        node.setFirstName(customer.getFirstName());
        node.setGender(customer.getGender());
        node.setCustomerId(customer.getId());
        node.setIdNumber(customer.getIdNumber());
        node.setLastName(customer.getLastName());
        node.setNationality(customer.getNationality());
        node.setRegistrationCode(customer.getRegistrationCode());
        node.setProfilePicture(customer.getProfilePicture());
        node.setState(customer.getState());
        node.setZipCode(customer.getZipCode());
        node.setLevel(customer.getLineLevel() == null ? 0 : customer.getLineLevel());

        Optional<CustomerAccount> customerAccountOptional = customerAccountRepository.findCustomerAccountByCustomerId(customer.getId());
        customerAccountOptional.ifPresent(customerAccount -> updateCustomerNodeWithAccountData(node, customerAccount));

        return node;
    }

    private void updateCustomerNodeWithAccountData(NCustomer node, CustomerAccount customerAccount) {

//        node.set);
    }

    private boolean issueDirectReferralBonus(Customer customer, Map<Customer, Double> mapOfCustomersToBePaid) {

        double directReferralBonusPercentage = customer.getPackageId().getDirectReferralPercentage() == null ?
                0D : customer.getPackageId().getDirectReferralPercentage();

        if (directReferralBonusPercentage == 0D)
            return true;

        Optional<Settings> registrationFeesSettings = settingsRepository.findByName(Parameters.SETTINGS_KEY_REGISTRATION_FEE);
        double registrationFees = Parameters.VALUE_REGISTRATION_FEES;
        if (registrationFeesSettings.isPresent())
            registrationFees = Double.valueOf(registrationFeesSettings.get().getValue());

        double amount = (directReferralBonusPercentage / 100.0) * registrationFees;

        //Credit customer account with amount
        Optional<CustomerAccount> customerAccountOptional = customerAccountRepository.findCustomerAccountByCustomerId(customer.getId());
        CustomerAccount customerAccount;
        if (customerAccountOptional.isPresent()) {
            customerAccount = customerAccountOptional.get();
        } else {
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
            creditCustomerAccount(customerAccount, amount, Parameters.sdfMysql.format(new Date()), Parameters.BONUS_TYPE_REGISTRATION);

            updatePayableMap(mapOfCustomersToBePaid, customer, amount);
//            transferBonusPaycashToCustomerAccount(customer, amount);
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private void updatePayableMap(Map<Customer, Double> mapOfCustomersToBePaid, Customer customer, double amount) {

        if (!mapOfCustomersToBePaid.containsKey(customer)) {
            mapOfCustomersToBePaid.put(customer, amount);
        } else {
            Double oldAmount = mapOfCustomersToBePaid.get(customer);
            mapOfCustomersToBePaid.put(customer, oldAmount + amount);
        }
    }

    boolean processCustomerUpLinePath(Customer customer, Map<Customer, Double> customerPaymentMap) {

        //Now from current customer, add two points until we get to level 2 since 1 is at the root
        //Here, we'll use the graph to quickly isolate the users that are ear-marked and we'll load and update their accounts all at once
        Collection<NCustomer> customerNodeCollection = customerNodeRepository.getUpLineCustomers(customer.getId());

        //Extract customer IDs
        List<Long> customerIds = customerNodeCollection.stream().map(NCustomer::getCustomerId).collect(Collectors.toList());

        //Add points to customer accounts
        //Todo: change source of points to be coming from the DB or config file
        customerAccountRepository.updateCustomerAccountPoints(Parameters.VALUE_REGISTRATION_POINTS, customerIds, new Date());

        verifyBonusQualification(customerIds, customerPaymentMap);

        return true;
    }

    private void verifyBonusQualification(List<Long> customerIds, Map<Customer, Double> customerPaymentMap) {

        if (customerIds.isEmpty())
            return;

        List<Customer> customers = customerRepository.findAllById(customerIds);

        List<CustomerAccount> accounts = customerAccountRepository.findAllByCustomerIdIn(customerIds);

        Map<Long, CustomerAccount> customerAccountMap = new HashMap<>();
        accounts.forEach(ca -> customerAccountMap.put(ca.getCustomerId(), ca));

        for (Customer customer : customers) {
            if (customer.getPackageId().getNextLevel() == null)
                continue;

            if (!customerAccountMap.containsKey(customer.getId())) {
                logger.error("CUSTOMER'S ACCOUNT NOT FOUND FOR CUSTOMER ID: {}", customer.getId());
            }

            CustomerAccount customerAccount = customerAccountMap.get(customer.getId());

            Integer currentPoints = customerAccount.getPoints();

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
                if (teamSize > 2) {
                    creditCustomerAccount(customerAccount, bonus.getAmount(), Parameters.sdfMysql.format(new Date()), Parameters.TRANSACTION_TYPE_PRODUCT_BONUS);
                    //Todo only perform this action at the end of the operation
                    updatePayableMap(customerPaymentMap, customer, bonus.getAmount());
//                    transferBonusPaycashToCustomerAccount(customer, bonus.getAmount());
                } else {
                    //Program for bonus to be paid when appropriate number of referrals have been made
                    registerPendingBonus(customerAccount.getId(), customer.getId(), bonus.getAmount(), Parameters.TRANSACTION_TYPE_PRODUCT_BONUS, Parameters.BONUS_CONDITION_REGISTER_THREE);
                }
            }

        }

    }


    public void creditCustomerAccount(CustomerAccount customerAccount, Double amount, String paidAt, String type) {
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

    public void transferBonusPaycashToCustomerAccount(Customer recipientCustomer, Double amount) {

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

    private void registerPendingBonus(Long accountId, Long customerId, Double amount, String type, String condition) {
        JSONObject pendingBonusJSON = new JSONObject();
        pendingBonusJSON.put("account_id", accountId);
        pendingBonusJSON.put("customer_id", customerId);
        pendingBonusJSON.put("amount", amount);
        pendingBonusJSON.put("type", type);
        pendingBonusJSON.put("condition", condition);

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
        if (initiatedMessages.size() > 0) {
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
        if (!customerOptional.isPresent())
            return;

        Customer customer = customerOptional.get();
        Integer teamSize = customerLinkRepository.countAllByParentId(customer);
        if (teamSize > 2) {
            messages.setStatus(Parameters.TRANSACTION_STATUS_INITIATED);
            messageRepository.save(messages);
            Optional<CustomerAccount> customerAccountOptional = customerAccountRepository.findById(messageJSON.getLong("account_id"));
            if (!customerAccountOptional.isPresent())
                return;
            CustomerAccount account = customerAccountOptional.get();

            try {

                Double amount = messageJSON.getDouble("amount");
                creditCustomerAccount(account, amount, Parameters.sdfMysql.format(new Date()), Parameters.TRANSACTION_TYPE_PRODUCT_BONUS);
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
