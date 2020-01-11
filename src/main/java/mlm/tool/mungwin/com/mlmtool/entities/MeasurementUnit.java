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
@Table(name = "measurement_unit")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MeasurementUnit.findAll", query = "SELECT m FROM MeasurementUnit m"),
    @NamedQuery(name = "MeasurementUnit.findById", query = "SELECT m FROM MeasurementUnit m WHERE m.id = :id"),
    @NamedQuery(name = "MeasurementUnit.findByName", query = "SELECT m FROM MeasurementUnit m WHERE m.name = :name"),
    @NamedQuery(name = "MeasurementUnit.findByCreatedAt", query = "SELECT m FROM MeasurementUnit m WHERE m.createdAt = :createdAt"),
    @NamedQuery(name = "MeasurementUnit.findByUpdatedAt", query = "SELECT m FROM MeasurementUnit m WHERE m.updatedAt = :updatedAt")})
public class MeasurementUnit implements Serializable {
    @Size(max = 45)
    @Column(name = "name")
    private String name;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "measurementUnit")
    private List<Product> productList;

    public MeasurementUnit() {
    }

    public MeasurementUnit(Integer id) {
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

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
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
        if (!(object instanceof MeasurementUnit)) {
            return false;
        }
        MeasurementUnit other = (MeasurementUnit) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mungwincore.authresource.domain.models.business.MeasurementUnit[ id=" + id + " ]";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
