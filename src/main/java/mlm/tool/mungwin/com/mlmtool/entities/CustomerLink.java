/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mlm.tool.mungwin.com.mlmtool.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 *
 * @author TNC TECH
 */
@Entity
@Table(name = "customer_link")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CustomerLink.findAll", query = "SELECT c FROM CustomerLink c"),
    @NamedQuery(name = "CustomerLink.findById", query = "SELECT c FROM CustomerLink c WHERE c.id = :id"),
    @NamedQuery(name = "CustomerLink.findByPosition", query = "SELECT c FROM CustomerLink c WHERE c.position = :position"),
    @NamedQuery(name = "CustomerLink.findByLevel", query = "SELECT c FROM CustomerLink c WHERE c.level = :level")})
public class CustomerLink implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 8)
    @Column(name = "position")
    private String position;
    @Column(name = "level")
    private Integer level;
    @Column(name = "path")
    private String path;
    @JoinColumn(name = "child_id", referencedColumnName = "id")
    @ManyToOne
    private Customer childId;
    @JoinColumn(name = "parent_id", referencedColumnName = "id")
    @OneToOne
    private Customer parentId;

    public CustomerLink() {
    }

    public CustomerLink(Long id) {
        this.id = id;
    }

    public CustomerLink(Long id, String position) {
        this.id = id;
        this.position = position;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Customer getChildId() {
        return childId;
    }

    public void setChildId(Customer childId) {
        this.childId = childId;
    }

    public Customer getParentId() {
        return parentId;
    }

    public void setParentId(Customer parentId) {
        this.parentId = parentId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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
        if (!(object instanceof CustomerLink)) {
            return false;
        }
        CustomerLink other = (CustomerLink) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mungwincore.authresource.domain.models.customer.CustomerLink[ id=" + id + " ]";
    }
    
}
