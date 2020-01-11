/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mlm.tool.mungwin.com.mlmtool.entities;


import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author TNC TECH
 */
@Entity
@Table(name = "pending_registration")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "PendingRegistration.findAll", query = "SELECT p FROM PendingRegistration p"),
        @NamedQuery(name = "PendingRegistration.findById", query = "SELECT p FROM PendingRegistration p WHERE p.id = :id"),
        @NamedQuery(name = "PendingRegistration.findByStatus", query = "SELECT p FROM PendingRegistration p WHERE p.status = :status"),
        @NamedQuery(name = "PendingRegistration.findByCreatedAt", query = "SELECT p FROM PendingRegistration p WHERE p.createdAt = :createdAt")})
public class PendingRegistration implements Serializable {
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    @ManyToOne
    private Orders salesOrder;
//    @Size(max = 45)
    @Column(name = "status")
    private String status;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "transaction_id")
    private Long transactionId;
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @JoinColumn(name = "customers_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Customer customer;

    public PendingRegistration() {
    }

    public PendingRegistration(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
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
        if (!(object instanceof PendingRegistration)) {
            return false;
        }
        PendingRegistration other = (PendingRegistration) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mungwincore.authresource.domain.models.business.PendingRegistration[ id=" + id + " ]";
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Orders getOrder() {
        return salesOrder;
    }

    public void setOrder(Orders order) {
        this.salesOrder = order;
    }

}
