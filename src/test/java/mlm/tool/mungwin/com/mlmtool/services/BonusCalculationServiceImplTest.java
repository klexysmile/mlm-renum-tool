package mlm.tool.mungwin.com.mlmtool.services;

import mlm.tool.mungwin.com.mlmtool.entities.Customer;
import mlm.tool.mungwin.com.mlmtool.repositories.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class BonusCalculationServiceImplTest {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    BonusCalculationServiceImpl bonusCalculationService;

    @Test
    void transferBonusPaycashToCustomerAccount() {

        Optional<Customer> customerOptional = customerRepository.findById(41L);
        if(customerOptional.isPresent()){
            Customer customer = customerOptional.get();
            Double amount = 126.0;

            bonusCalculationService.transferBonusPaycashToCustomerAccount(customer, amount);

        }

    }
}