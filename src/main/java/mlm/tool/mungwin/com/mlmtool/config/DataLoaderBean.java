package mlm.tool.mungwin.com.mlmtool.config;

import io.swagger.models.auth.In;
import mlm.tool.mungwin.com.mlmtool.entities.Customer;
import mlm.tool.mungwin.com.mlmtool.entities.CustomerLink;
import mlm.tool.mungwin.com.mlmtool.entities.relationships.Invitation;
import mlm.tool.mungwin.com.mlmtool.entities.relationships.Referral;
import mlm.tool.mungwin.com.mlmtool.node.NCustomer;
import mlm.tool.mungwin.com.mlmtool.repositories.CustomerLinkRepository;
import mlm.tool.mungwin.com.mlmtool.repositories.CustomerRepository;
import mlm.tool.mungwin.com.mlmtool.repositories.node.InvitationRepository;
import mlm.tool.mungwin.com.mlmtool.repositories.node.NCustomerRepository;
import mlm.tool.mungwin.com.mlmtool.repositories.node.ReferralRepository;
import mlm.tool.mungwin.com.mlmtool.services.BonusCalculationServiceAssync;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class DataLoaderBean implements InitializingBean {

    @Autowired
    NCustomerRepository customerNodeRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ReferralRepository referralRepository;

    @Autowired
    CustomerLinkRepository customerLinkRepository;

    @Autowired
    InvitationRepository invitationRepository;

    @Autowired
    BonusCalculationServiceAssync bonusCalculationServiceAssync;

    @Value("${tool.config.loadGraph}")
    Boolean loadGraph;

    @Override
    public void afterPropertiesSet() throws Exception {

        if(loadGraph != null)
            if(loadGraph) {
                loadCustomerToGraph();
            }
    }

    void loadCustomerToGraph(){

        List<Customer> customers = customerRepository.findAll();

        List<NCustomer> nodes = customers.stream().map(bonusCalculationServiceAssync::getCustomerNode).collect(Collectors.toList());

        for(NCustomer node : nodes){
            Optional<NCustomer> nodeOptional = customerNodeRepository.findByCustomerId(node.getCustomerId());

            if(!nodeOptional.isPresent()) {
                System.out.println(">> Saving CID: " + node.getCustomerId());
                customerNodeRepository.save(node);
            }
        }

        relateCustomerNodes();

    }

    void relateCustomerNodes() {

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

                        List<Referral> optionalReferral = referralRepository.findFirstByUpLineCustomerIdAndDownLineCustomerId(upNodeOptional.get().getCustomerId(), node.getCustomerId());

                        if(optionalReferral.isEmpty()){
                            /*Referral referral = Referral.builder()
                                    .downLine(node)
                                    .upLine(upNodeOptional.get())
                                    .position(customerDownlinkMapData.get(node.getCustomerId()).getPosition())
                                    .build();*/
                            Referral referral = new Referral();
                            referral.setDownLine(node);
                            referral.setUpLine(upNodeOptional.get());
                            referral.setPosition(customerDownlinkMapData.get(node.getCustomerId()).getPosition());

                            referralRepository.save(referral);
                        }

                        node.setLevel(customerDownlinkMapData.get(node.getCustomerId()).getLevel());
                        customerNodeRepository.save(node);
                    }
                }

            }
        }

        relateCustomerNodesDirect();

    }


    void relateCustomerNodesDirect() {

        List<Customer> customerLinkList = customerRepository.findAll();

        for(Customer customer : customerLinkList){
            if(customer.getUpLineId() != null){
                Optional<Customer> upLineCustomerOptional = customerRepository.findById(customer.getUpLineId().longValue());

                if(upLineCustomerOptional.isPresent()){
                    Customer upCustomer = upLineCustomerOptional.get();

                    Optional<Invitation> invitationOptional = invitationRepository.findFirstByMemberCustomerIdAndInviteeCustomerId(upCustomer.getId(), customer.getId());

                    if(!invitationOptional.isPresent()){
                        /*Invitation invitation = Invitation.builder()
                                .member(bonusCalculationServiceAssync.getCustomerNode(upCustomer))
                                .invitee(bonusCalculationServiceAssync.getCustomerNode(customer))
                                .build();*/

                        Optional<NCustomer> memberNodeOptional = customerNodeRepository.findByCustomerId(upCustomer.getId());
                        Optional<NCustomer> inviteeNodeOptional = customerNodeRepository.findByCustomerId(customer.getId());

                        Invitation invitation = new Invitation();

                        invitation.setInvitee(inviteeNodeOptional.orElse(bonusCalculationServiceAssync.getCustomerNode(customer)));
                        invitation.setMember(memberNodeOptional.orElse(bonusCalculationServiceAssync.getCustomerNode(upCustomer)));

                        invitationRepository.save(invitation);

                    }
                }
            }
        }
    }
}
