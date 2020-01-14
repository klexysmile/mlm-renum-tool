/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mlm.tool.mungwin.com.mlmtool.entities;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author TNC TECH
 */
@Entity
@Table(name = "customer_account")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CustomerAccount.findAll", query = "SELECT c FROM CustomerAccount c"),
    @NamedQuery(name = "CustomerAccount.findById", query = "SELECT c FROM CustomerAccount c WHERE c.id = :id"),
    @NamedQuery(name = "CustomerAccount.findByCustomerId", query = "SELECT c FROM CustomerAccount c WHERE c.customerId = :customerId"),
    @NamedQuery(name = "CustomerAccount.findByTotalBalance", query = "SELECT c FROM CustomerAccount c WHERE c.totalBalance = :totalBalance"),
    @NamedQuery(name = "CustomerAccount.findByAvailableBalance", query = "SELECT c FROM CustomerAccount c WHERE c.availableBalance = :availableBalance"),
    @NamedQuery(name = "CustomerAccount.findByPoints", query = "SELECT c FROM CustomerAccount c WHERE c.points = :points"),
    @NamedQuery(name = "CustomerAccount.findByLastUpdate", query = "SELECT c FROM CustomerAccount c WHERE c.lastUpdate = :lastUpdate"),
    @NamedQuery(name = "CustomerAccount.findByUpdatedBy", query = "SELECT c FROM CustomerAccount c WHERE c.updatedBy = :updatedBy")})
public class CustomerAccount implements Serializable {
    @Column(name = "customer_id")
    private Long customerId;
    @Size(max = 30)
    @Column(name = "updated_by")
    private String updatedBy;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "total_balance")
    private Double totalBalance;
    @Column(name = "available_balance")
    private Double availableBalance;
    @Column(name = "points")
    private Integer points;
    @Column(name = "network_size")
    private Integer networkSize;
    @Column(name = "last_update")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdate;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customerAccount")
    private List<AccountMovements> accountMovementsList;

    public CustomerAccount() {
    }

    public CustomerAccount(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Double getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(Double totalBalance) {
        this.totalBalance = totalBalance;
    }

    public Double getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(Double availableBalance) {
        this.availableBalance = availableBalance;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Integer getNetworkSize() {
        return networkSize;
    }

    public void setNetworkSize(Integer networkSize) {
        this.networkSize = networkSize;
    }

    @XmlTransient
    public List<AccountMovements> getAccountMovementsList() {
        return accountMovementsList;
    }

    public void setAccountMovementsList(List<AccountMovements> accountMovementsList) {
        this.accountMovementsList = accountMovementsList;
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
        if (!(object instanceof CustomerAccount)) {
            return false;
        }
        CustomerAccount other = (CustomerAccount) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mlm.tool.mungwin.com.mlmtool.entities.CustomerAccount[ id=" + id + " ]";
    }
    
}
