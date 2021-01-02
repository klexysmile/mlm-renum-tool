package mlm.tool.mungwin.com.mlmtool.repositories;

import mlm.tool.mungwin.com.mlmtool.entities.Customer;
import mlm.tool.mungwin.com.mlmtool.entities.CustomerLink;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerLinkRepository extends JpaRepository<CustomerLink, Long> {

    Integer countAllByParentId(Customer customerId);

    List<CustomerLink> getByChildIdAndParentId(Customer downLine, Customer upLine);

}
