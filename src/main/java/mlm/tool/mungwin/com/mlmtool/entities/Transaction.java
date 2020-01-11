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
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

/**
 *
 * @author TNC TECH
 */
@Entity
@Table(name = "transaction")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Transaction.findAll", query = "SELECT t FROM Transaction t"),
    @NamedQuery(name = "Transaction.findById", query = "SELECT t FROM Transaction t WHERE t.id = :id"),
    @NamedQuery(name = "Transaction.findByCustomerId", query = "SELECT t FROM Transaction t WHERE t.customerId = :customerId"),
    @NamedQuery(name = "Transaction.findByType", query = "SELECT t FROM Transaction t WHERE t.type = :type"),
    @NamedQuery(name = "Transaction.findByCode", query = "SELECT t FROM Transaction t WHERE t.code = :code"),
    @NamedQuery(name = "Transaction.findByAmount", query = "SELECT t FROM Transaction t WHERE t.amount = :amount"),
    @NamedQuery(name = "Transaction.findByStatus", query = "SELECT t FROM Transaction t WHERE t.status = :status"),
    @NamedQuery(name = "Transaction.findByCreatedAt", query = "SELECT t FROM Transaction t WHERE t.createdAt = :createdAt"),
    @NamedQuery(name = "Transaction.findByPaymentToken", query = "SELECT t FROM Transaction t WHERE t.paymentToken = :paymentToken"),
    @NamedQuery(name = "Transaction.findByPaidAt", query = "SELECT t FROM Transaction t WHERE t.paidAt = :paidAt")})
public class Transaction implements Serializable {
    @Column(name = "customer_id")
    private Long customerId;
    @Size(max = 25)
    @Column(name = "code")
    private String code;
    @Size(max = 20)
    @Column(name = "status")
    private String status;
    @Size(max = 50)
    @Column(name = "paid_at")
    private String paidAt;
    @Size(max = 256)
    @Column(name = "payment_token")
    private String paymentToken;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "transaction")
    private List<AccountMovements> accountMovementsList;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Size(max = 20)
    @Column(name = "type")
    private String type;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "amount")
    private Double amount;
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "transaction")
    private List<Orders> ordersList;

    public Transaction() {
    }

    public Transaction(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }


    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @XmlTransient
    @JsonIgnore
    public List<Orders> getOrdersList() {
        return ordersList;
    }

    public void setOrdersList(List<Orders> ordersList) {
        this.ordersList = ordersList;
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
        if (!(object instanceof Transaction)) {
            return false;
        }
        Transaction other = (Transaction) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mungwincore.authresource.domain.models.business.Transaction[ id=" + id + " ]";
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }


    public String getPaidAt() {
        return paidAt;
    }

    public void setPaidAt(String paidAt) {
        this.paidAt = paidAt;
    }

    public String getPaymentToken() {
        return paymentToken;
    }

    public void setPaymentToken(String paymentToken) {
        this.paymentToken = paymentToken;
    }

    @XmlTransient
    public List<AccountMovements> getAccountMovementsList() {
        return accountMovementsList;
    }

    public void setAccountMovementsList(List<AccountMovements> accountMovementsList) {
        this.accountMovementsList = accountMovementsList;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
}
