package mlm.tool.mungwin.com.mlmtool.services;

import mlm.tool.mungwin.com.mlmtool.bean.CustomerAccountBean;
import mlm.tool.mungwin.com.mlmtool.entities.Customer;
import mlm.tool.mungwin.com.mlmtool.entities.CustomerAccount;
import mlm.tool.mungwin.com.mlmtool.entities.CustomerLink;
import mlm.tool.mungwin.com.mlmtool.entities.relationships.Referral;
import mlm.tool.mungwin.com.mlmtool.node.NCustomer;
import mlm.tool.mungwin.com.mlmtool.repositories.CustomerAccountRepository;
import mlm.tool.mungwin.com.mlmtool.repositories.CustomerLinkRepository;
import mlm.tool.mungwin.com.mlmtool.repositories.CustomerRepository;
import mlm.tool.mungwin.com.mlmtool.repositories.node.NCustomerRepository;
import mlm.tool.mungwin.com.mlmtool.repositories.node.ReferralRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
class BonusCalculationServiceImplTest {

    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    CustomerLinkRepository customerLinkRepository;
    @Autowired
    CustomerAccountRepository customerAccountRepository;
    @Autowired
    NCustomerRepository customerNodeRepository;
    @Autowired
    ReferralRepository referralRepository;

    @Autowired
    BonusCalculationServiceImpl bonusCalculationService;

    @Test
    void transferBonusPaycashToCustomerAccount() {

        Optional<Customer> customerOptional = customerRepository.findById(41L);
        if(customerOptional.isPresent()){
            Customer customer = customerOptional.get();
            Double amount = 126.0;

//            bonusCalculationService.transferBonusPaycashToCustomerAccount(customer, amount);

        }

    }

    @Test
    void loadCustomerToGraph(){

        List<Customer> customers = customerRepository.findAll();

        List<NCustomer> nodes = customers.stream().map(this::getCustomerNode).collect(Collectors.toList());

        for(NCustomer node : nodes){
            Optional<NCustomer> nodeOptional = customerNodeRepository.findByCustomerId(node.getCustomerId());

            if(!nodeOptional.isPresent()) {
                System.out.println(">> Saving CID: " + node.getCustomerId());
                customerNodeRepository.save(node);
            }
        }

    }


    @Test
    void testGetUpLineToRoot(){
        Collection<NCustomer> customers = customerNodeRepository.getUpLineCustomers(71L);
        for (NCustomer customer : customers) {
            System.out.println(customer.getFirstName() + " " + customer.getLastName());
        }
    }


    @Test
    void testRelateCustomerNodes() {

        List<CustomerLink> customerLinkList = customerLinkRepository.findAll();
        Map<Long, Long> customerDownlinkMap = new HashMap<>();
        Map<Long, CustomerLink> customerDownlinkMapData = new HashMap<>();

        for (CustomerLink customerLink : customerLinkList) {
            customerDownlinkMap.put(customerLink.getChildId().getId(), customerLink.getParentId().getId());
            customerDownlinkMapData.put(customerLink.getChildId().getId(), customerLink);
        }

        Iterable<NCustomer> nodes = customerNodeRepository.findAll();

        for(NCustomer node : nodes){

            if(customerDownlinkMap.containsKey(node.getCustomerId())){

                if(customerDownlinkMap.get(node.getCustomerId()) != null){
                    Optional<NCustomer> upNodeOptional = customerNodeRepository.findByCustomerId(customerDownlinkMap.get(node.getCustomerId()));

                    if(upNodeOptional.isPresent()){
                        System.out.println("Updating node: " + node.getAuthUuid());

                        /*Referral referral = Referral.builder()
                                .downLine(node)
                                .upLine(upNodeOptional.get())
                                .position(customerDownlinkMapData.get(node.getCustomerId()).getPosition())
                                .build();

                        referralRepository.save(referral);*/

                        node.setLevel(customerDownlinkMapData.get(node.getCustomerId()).getLevel());
                        customerNodeRepository.save(node);
                    }
                }

            }
        }

    }

    private NCustomer getCustomerNode(Customer customer) {

//        NCustomer node = NCustomer.builder()
//                .authUuid(customer.getAuthUuid())
//                .address(customer.getAddress())
//                .birthDate(customer.getBirthDate() == null ? 0L : customer.getBirthDate().getTime())
//                .birthPlace(customer.getBirthPlace())
//                .city(customer.getCity())
//                .country(customer.getCountry())
//                .email(customer.getEmail())
//                .firstName(customer.getFirstName())
//                .gender(customer.getGender())
//                .customerId(customer.getId())
//                .idNumber(customer.getIdNumber())
//                .lastName(customer.getLastName())
//                .nationality(customer.getNationality())
//                .registrationCode(customer.getRegistrationCode())
//                .profilePicture(customer.getProfilePicture())
//                .state(customer.getState())
//                .zipCode(customer.getZipCode())
//                .level(customer.getLineLevel())
////                .account(getCustomerAccountBean(customer))
//                .build();
//
//        return node;
        return null;
    }

    private CustomerAccountBean getCustomerAccountBean(Customer customer) {
        CustomerAccountBean customerAccountBean = new CustomerAccountBean();

        Optional<CustomerAccount> customerAccountOptional = customerAccountRepository.findCustomerAccountByCustomerId(customer.getId());

        if(customerAccountOptional.isPresent()){
            CustomerAccount customerAccount = customerAccountOptional.get();
            customerAccountBean.setAvailableBalance(customerAccount.getAvailableBalance() == null ? 0 : customerAccount.getAvailableBalance());
            customerAccountBean.setId(customerAccount.getId() ==  null ? 0 : customerAccount.getId());
            customerAccountBean.setNetworkSize(customerAccount.getNetworkSize() == null ? 0 : customerAccount.getNetworkSize());
            customerAccountBean.setPoints(customerAccount.getPoints() == null ? 0 : customerAccount.getPoints());
            customerAccountBean.setTotalBalance(customerAccount.getTotalBalance() == null ? 0 : customerAccount.getTotalBalance());
        }

        return customerAccountBean;
    }
}