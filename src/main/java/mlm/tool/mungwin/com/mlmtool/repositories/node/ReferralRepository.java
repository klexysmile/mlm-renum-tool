package mlm.tool.mungwin.com.mlmtool.repositories.node;

import mlm.tool.mungwin.com.mlmtool.entities.relationships.Referral;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.List;
import java.util.Optional;

public interface ReferralRepository extends Neo4jRepository<Referral, Long> {

    List<Referral> findFirstByUpLineCustomerIdAndDownLineCustomerId(Long upId, Long downId);

}
