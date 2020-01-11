/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mlm.tool.mungwin.com.mlmtool.entities;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author TNC TECH
 */
@Entity
@Table(name = "process_position")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProcessPosition.findAll", query = "SELECT p FROM ProcessPosition p"),
    @NamedQuery(name = "ProcessPosition.findById", query = "SELECT p FROM ProcessPosition p WHERE p.id = :id"),
    @NamedQuery(name = "ProcessPosition.findByCursorIndex", query = "SELECT p FROM ProcessPosition p WHERE p.cursorIndex = :cursorIndex")})
public class ProcessPosition implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "cursor_index")
    private Long cursorIndex;
    @JoinColumn(name = "message_id", referencedColumnName = "id")
    @ManyToOne
    private Messages messageId;

    public ProcessPosition() {
        this.cursorIndex = 0L;
    }

    public ProcessPosition(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getCursorIndex() {
        return cursorIndex;
    }

    public void setCursorIndex(Long cursorIndex) {
        this.cursorIndex = cursorIndex;
    }

    public Messages getMessageId() {
        return messageId;
    }

    public void setMessageId(Messages messageId) {
        this.messageId = messageId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProcessPosition)) {
            return false;
        }
        ProcessPosition other = (ProcessPosition) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mlm.tool.mungwin.com.mlmtool.entities.ProcessPosition[ id=" + id + " ]";
    }
    
}
