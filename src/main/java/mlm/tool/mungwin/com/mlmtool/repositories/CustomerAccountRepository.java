package mlm.tool.mungwin.com.mlmtool.repositories;

import jdk.nashorn.internal.runtime.options.Option;
import mlm.tool.mungwin.com.mlmtool.entities.CustomerAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CustomerAccountRepository extends JpaRepository<CustomerAccount, Long> {

    Optional<CustomerAccount> findCustomerAccountByCustomerId(Long customerId);

    @Query("SELECT SUM (a.points) FROM CustomerAccount a WHERE a.id = ?1 ")
    Double sumCustomerAccountPoints(Long customerAccountId);

}
