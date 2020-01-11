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
@Table(name = "product_combo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProductCombo.findAll", query = "SELECT p FROM ProductCombo p"),
    @NamedQuery(name = "ProductCombo.findById", query = "SELECT p FROM ProductCombo p WHERE p.id = :id"),
    @NamedQuery(name = "ProductCombo.findByCreatedAt", query = "SELECT p FROM ProductCombo p WHERE p.createdAt = :createdAt")})
public class ProductCombo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @JoinColumn(name = "parent", referencedColumnName = "id")
    @ManyToOne
    private Product parent;
    @JoinColumn(name = "product", referencedColumnName = "id")
    @ManyToOne
    private Product product;

    public ProductCombo() {
    }

    public ProductCombo(Integer id) {
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

    public Product getParent() {
        return parent;
    }

    public void setParent(Product parent) {
        this.parent = parent;
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
        if (!(object instanceof ProductCombo)) {
            return false;
        }
        ProductCombo other = (ProductCombo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mungwincore.authresource.domain.models.business.ProductCombo[ id=" + id + " ]";
    }
    
}
