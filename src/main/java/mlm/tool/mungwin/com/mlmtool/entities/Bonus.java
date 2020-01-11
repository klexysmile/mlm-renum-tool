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
import java.util.Date;
import java.util.List;

/**
 *
 * @author TNC TECH
 */
@Entity
@Table(name = "bonus")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Bonus.findAll", query = "SELECT b FROM Bonus b"),
    @NamedQuery(name = "Bonus.findById", query = "SELECT b FROM Bonus b WHERE b.id = :id"),
    @NamedQuery(name = "Bonus.findByName", query = "SELECT b FROM Bonus b WHERE b.name = :name"),
    @NamedQuery(name = "Bonus.findByDescription", query = "SELECT b FROM Bonus b WHERE b.description = :description"),
    @NamedQuery(name = "Bonus.findByAmount", query = "SELECT b FROM Bonus b WHERE b.amount = :amount"),
    @NamedQuery(name = "Bonus.findByQualificationPoints", query = "SELECT b FROM Bonus b WHERE b.qualificationPoints = :qualificationPoints"),
    @NamedQuery(name = "Bonus.findByPackageId", query = "SELECT b FROM Bonus b WHERE b.packageId = :packageId"),
    @NamedQuery(name = "Bonus.findByCreatedAt", query = "SELECT b FROM Bonus b WHERE b.createdAt = :createdAt"),
    @NamedQuery(name = "Bonus.findByUpdatedAt", query = "SELECT b FROM Bonus b WHERE b.updatedAt = :updatedAt")})
public class Bonus implements Serializable {
    @Size(max = 50)
    @Column(name = "name")
    private String name;
    @Size(max = 256)
    @Column(name = "description")
    private String description;
    @Size(max = 30)
    @Column(name = "created_by")
    private String createdBy;
    @Size(max = 30)
    @Column(name = "updated_by")
    private String updatedBy;
    @Column(name = "status")
    private Boolean status;
    @JoinColumn(name = "transaction_type", referencedColumnName = "name")
    @ManyToOne
    private TransactionType transactionType;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "amount")
    private Double amount;
    @Column(name = "qualification_points")
    private Integer qualificationPoints;
    @Column(name = "package_id")
    private Integer packageId;
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bonus")
    private List<ProductSalesBonus> productSalesBonusList;

    public Bonus() {
    }

    public Bonus(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getQualificationPoints() {
        return qualificationPoints;
    }

    public void setQualificationPoints(Integer qualificationPoints) {
        this.qualificationPoints = qualificationPoints;
    }

    public Integer getPackageId() {
        return packageId;
    }

    public void setPackageId(Integer packageId) {
        this.packageId = packageId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @XmlTransient
    @JsonIgnore
    public List<ProductSalesBonus> getProductSalesBonusList() {
        return productSalesBonusList;
    }

    public void setProductSalesBonusList(List<ProductSalesBonus> productSalesBonusList) {
        this.productSalesBonusList = productSalesBonusList;
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
        if (!(object instanceof Bonus)) {
            return false;
        }
        Bonus other = (Bonus) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mungwincore.authresource.domain.models.business.Bonus[ id=" + id + " ]";
    }


    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }


    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    
}
