/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mlm.tool.mungwin.com.mlmtool.entities;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 *
 * @author TNC TECH
 */
@Entity
@Table(name = "product_sales_bonus")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProductSalesBonus.findAll", query = "SELECT p FROM ProductSalesBonus p"),
    @NamedQuery(name = "ProductSalesBonus.findById", query = "SELECT p FROM ProductSalesBonus p WHERE p.id = :id"),
    @NamedQuery(name = "ProductSalesBonus.findByPercentage", query = "SELECT p FROM ProductSalesBonus p WHERE p.percentage = :percentage"),
    @NamedQuery(name = "ProductSalesBonus.findByPackageId", query = "SELECT p FROM ProductSalesBonus p WHERE p.packageId = :packageId")})
public class ProductSalesBonus implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "percentage")
    private Double percentage;
    @Column(name = "package_id")
    private Integer packageId;
    @JoinColumn(name = "bonus", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Bonus bonus;
    @JoinColumn(name = "product", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Product product;

    public ProductSalesBonus() {
    }

    public ProductSalesBonus(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getPercentage() {
        return percentage;
    }

    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }

    public Integer getPackageId() {
        return packageId;
    }

    public void setPackageId(Integer packageId) {
        this.packageId = packageId;
    }

    public Bonus getBonus() {
        return bonus;
    }

    public void setBonus(Bonus bonus) {
        this.bonus = bonus;
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
        if (!(object instanceof ProductSalesBonus)) {
            return false;
        }
        ProductSalesBonus other = (ProductSalesBonus) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mungwincore.authresource.domain.models.business.ProductSalesBonus[ id=" + id + " ]";
    }
    
}
