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
@Table(name = "orders")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Orders.findAll", query = "SELECT o FROM Orders o"),
    @NamedQuery(name = "Orders.findById", query = "SELECT o FROM Orders o WHERE o.id = :id"),
    @NamedQuery(name = "Orders.findByCustomerId", query = "SELECT o FROM Orders o WHERE o.customerId = :customerId"),
    @NamedQuery(name = "Orders.findByStatus", query = "SELECT o FROM Orders o WHERE o.status = :status"),
    @NamedQuery(name = "Orders.findByOrderNumber", query = "SELECT o FROM Orders o WHERE o.orderNumber = :orderNumber")})
public class Orders implements Serializable {
    @Column(name = "customer_id")
    private Long customerId;
    @Size(max = 15)
    @Column(name = "status")
    private String status;
    @OneToMany(mappedBy = "salesOrder")
    private List<OrderDetail> orderDetailList;
    @OneToMany(mappedBy = "salesOrder")
    private List<PendingRegistration> pendingRegistrationList;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Column(name = "order_number")
    private String orderNumber;
    @JoinTable(name = "order_details", joinColumns = {@JoinColumn(name = "product", referencedColumnName = "id")})
    @ManyToMany
    private List<Product> productList;
    @JoinColumn(name = "transaction", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Transaction transaction;

    public Orders() {
    }

    public Orders(Long id) {
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


    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    @XmlTransient
    @JsonIgnore
    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
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
        if (!(object instanceof Orders)) {
            return false;
        }
        Orders other = (Orders) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mungwincore.authresource.domain.models.business.Orders[ id=" + id + " ]";
    }


    @XmlTransient
    @JsonIgnore
    public List<PendingRegistration> getPendingRegistrationList() {
        return pendingRegistrationList;
    }

    public void setPendingRegistrationList(List<PendingRegistration> pendingRegistrationList) {
        this.pendingRegistrationList = pendingRegistrationList;
    }


    @XmlTransient
    @JsonIgnore
    public List<OrderDetail> getOrderDetailList() {
        return orderDetailList;
    }

    public void setOrderDetailList(List<OrderDetail> orderDetailList) {
        this.orderDetailList = orderDetailList;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
}
