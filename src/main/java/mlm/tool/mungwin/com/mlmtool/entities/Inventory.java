/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mlm.tool.mungwin.com.mlmtool.entities;

import javax.persistence.*;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 *
 * @author TNC TECH
 */
@Entity
@Table(name = "inventory")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Inventory.findAll", query = "SELECT i FROM Inventory i"),
    @NamedQuery(name = "Inventory.findById", query = "SELECT i FROM Inventory i WHERE i.id = :id"),
    @NamedQuery(name = "Inventory.findByQuantity", query = "SELECT i FROM Inventory i WHERE i.quantity = :quantity")})
public class Inventory implements Serializable {
    @Size(max = 11)
    @Column(name = "quantity")
    private String quantity;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "branch", referencedColumnName = "id")
    @ManyToOne
    private Office branch;
    @JoinColumn(name = "product", referencedColumnName = "id")
    @ManyToOne
    private Product product;

    public Inventory() {
    }

    public Inventory(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Office getBranch() {
        return branch;
    }

    public void setBranch(Office branch) {
        this.branch = branch;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
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
        if (!(object instanceof Inventory)) {
            return false;
        }
        Inventory other = (Inventory) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mungwincore.authresource.domain.models.business.Inventory[ id=" + id + " ]";
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
    
}
