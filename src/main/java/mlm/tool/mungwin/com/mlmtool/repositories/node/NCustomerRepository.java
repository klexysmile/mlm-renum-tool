package mlm.tool.mungwin.com.mlmtool.repositories.node;

import mlm.tool.mungwin.com.mlmtool.node.NCustomer;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.Collection;
import java.util.Optional;

public interface NCustomerRepository extends Neo4jRepository<NCustomer, String> {

    Optional<NCustomer> findByCustomerId(Long id);

    @Query("MATCH p=(d:NCustomer)<-[REFERRED*]-(u:NCustomer) WITH nodes(p) AS ns WHERE d.customerId = $id AND u.firstName<>'root'  RETURN ns")
    Collection<NCustomer> getUpLineCustomers(Long id);
}
