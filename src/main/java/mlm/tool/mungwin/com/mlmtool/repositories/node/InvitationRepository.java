package mlm.tool.mungwin.com.mlmtool.repositories.node;

import mlm.tool.mungwin.com.mlmtool.entities.relationships.Invitation;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.Optional;

public interface InvitationRepository extends Neo4jRepository<Invitation, Long> {

    Optional<Invitation> findFirstByMemberCustomerIdAndInviteeCustomerId(Long memberId, Long inviteeId);

}
