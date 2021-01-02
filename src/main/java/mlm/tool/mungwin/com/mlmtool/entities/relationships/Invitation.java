package mlm.tool.mungwin.com.mlmtool.entities.relationships;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import mlm.tool.mungwin.com.mlmtool.node.NCustomer;
import org.neo4j.ogm.annotation.*;

@AllArgsConstructor
@NoArgsConstructor
@RelationshipEntity(type = "INVITED")
public class Invitation {

    @Id
    @GeneratedValue
    private Long referralId;

    @StartNode
    NCustomer member;

    @EndNode
    NCustomer invitee;

    public Long getReferralId() {
        return referralId;
    }

    public NCustomer getMember() {
        return member;
    }

    public void setMember(NCustomer member) {
        this.member = member;
    }

    public NCustomer getInvitee() {
        return invitee;
    }

    public void setInvitee(NCustomer invitee) {
        this.invitee = invitee;
    }
}