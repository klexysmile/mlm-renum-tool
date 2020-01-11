package mlm.tool.mungwin.com.mlmtool.repositories;

import mlm.tool.mungwin.com.mlmtool.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * @author nouks
 */
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByRegistrationCode(String registrationCode);
    Optional<Customer> findByEmail(String email);
}
