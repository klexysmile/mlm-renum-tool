package mlm.tool.mungwin.com.mlmtool.entities.relationships;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import mlm.tool.mungwin.com.mlmtool.node.NCustomer;
import org.neo4j.ogm.annotation.*;

@AllArgsConstructor
@NoArgsConstructor
@RelationshipEntity(type = "REFERRED")
public class Referral {

    @Id
    @GeneratedValue
    private Long referralId;

    String position;

    @StartNode
    NCustomer upLine;

    @EndNode
    NCustomer downLine;

    public Long getReferralId() {
        return referralId;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public NCustomer getUpLine() {
        return upLine;
    }

    public void setUpLine(NCustomer upLine) {
        this.upLine = upLine;
    }

    public NCustomer getDownLine() {
        return downLine;
    }

    public void setDownLine(NCustomer downLine) {
        this.downLine = downLine;
    }
}