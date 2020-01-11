/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mlm.tool.mungwin.com.mlmtool.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author TNC TECH
 */
@Entity
@Table(name = "office")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Office.findAll", query = "SELECT o FROM Office o"),
    @NamedQuery(name = "Office.findById", query = "SELECT o FROM Office o WHERE o.id = :id"),
    @NamedQuery(name = "Office.findByTown", query = "SELECT o FROM Office o WHERE o.town = :town"),
    @NamedQuery(name = "Office.findByAddress", query = "SELECT o FROM Office o WHERE o.address = :address"),
    @NamedQuery(name = "Office.findByManager", query = "SELECT o FROM Office o WHERE o.manager = :manager")})
public class Office implements Serializable {
    @Size(max = 45)
    @Column(name = "town")
    private String town;
    @Size(max = 80)
    @Column(name = "address")
    private String address;
    @Size(max = 60)
    @Column(name = "manager")
    private String manager;
    @OneToMany(mappedBy = "branch")
    private List<Inventory> inventoryList;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinTable(name = "product_inventory", joinColumns = {@JoinColumn(name = "product_id", referencedColumnName = "id")})
    @ManyToMany
    private List<Product> productList;

    public Office() {
    }

    public Office(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    @XmlTransient
    @JsonIgnore
    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
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
        if (!(object instanceof Office)) {
            return false;
        }
        Office other = (Office) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mungwincore.authresource.domain.models.business.Office[ id=" + id + " ]";
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    @XmlTransient
    @JsonIgnore
    public List<Inventory> getInventoryList() {
        return inventoryList;
    }

    public void setInventoryList(List<Inventory> inventoryList) {
        this.inventoryList = inventoryList;
    }
    
}
