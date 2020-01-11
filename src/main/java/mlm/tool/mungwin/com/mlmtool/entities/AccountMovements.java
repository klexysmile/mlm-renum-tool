/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mlm.tool.mungwin.com.mlmtool.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author TNC TECH
 */
@Entity
@Table(name = "account_movements")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "AccountMovements.findAll", query = "SELECT a FROM AccountMovements a"),
        @NamedQuery(name = "AccountMovements.findById", query = "SELECT a FROM AccountMovements a WHERE a.id = :id"),
        @NamedQuery(name = "AccountMovements.findByType", query = "SELECT a FROM AccountMovements a WHERE a.type = :type"),
        @NamedQuery(name = "AccountMovements.findByMotive", query = "SELECT a FROM AccountMovements a WHERE a.motive = :motive"),
        @NamedQuery(name = "AccountMovements.findByDescription", query = "SELECT a FROM AccountMovements a WHERE a.description = :description")})
public class AccountMovements implements Serializable {
    @Size(max = 10)
    @Column(name = "type")
    private String type;
    @Size(max = 45)
    @Column(name = "motive")
    private String motive;
    @Size(max = 50)
    @Column(name = "description")
    private String description;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @JoinColumn(name = "customer_account", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private CustomerAccount customerAccount;
    @JoinColumn(name = "transaction", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Transaction transaction;

    public AccountMovements() {
    }

    public AccountMovements(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public CustomerAccount getCustomerAccount() {
        return customerAccount;
    }

    public void setCustomerAccount(CustomerAccount customerAccount) {
        this.customerAccount = customerAccount;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
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
        if (!(object instanceof AccountMovements)) {
            return false;
        }
        AccountMovements other = (AccountMovements) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mlm.tool.mungwin.com.mlmtool.entities.AccountMovements[ id=" + id + " ]";
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMotive() {
        return motive;
    }

    public void setMotive(String motive) {
        this.motive = motive;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
