package mlm.tool.mungwin.com.mlmtool.repositories;

import mlm.tool.mungwin.com.mlmtool.entities.Customer;
import mlm.tool.mungwin.com.mlmtool.entities.CustomerLink;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerLinkRepository extends JpaRepository<CustomerLink, Long> {

    Integer countAllByParentId(Customer customerId);

}
