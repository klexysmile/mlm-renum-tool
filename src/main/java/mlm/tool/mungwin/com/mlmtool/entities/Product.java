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
@Table(name = "product")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Product.findAll", query = "SELECT p FROM Product p"),
    @NamedQuery(name = "Product.findById", query = "SELECT p FROM Product p WHERE p.id = :id"),
    @NamedQuery(name = "Product.findByName", query = "SELECT p FROM Product p WHERE p.name = :name"),
    @NamedQuery(name = "Product.findByCode", query = "SELECT p FROM Product p WHERE p.code = :code"),
    @NamedQuery(name = "Product.findByPrice", query = "SELECT p FROM Product p WHERE p.price = :price"),
    @NamedQuery(name = "Product.findByTags", query = "SELECT p FROM Product p WHERE p.tags = :tags"),
    @NamedQuery(name = "Product.findByStatus", query = "SELECT p FROM Product p WHERE p.status = :status"),
    @NamedQuery(name = "Product.findByWeight", query = "SELECT p FROM Product p WHERE p.weight = :weight"),
    @NamedQuery(name = "Product.findByVolume", query = "SELECT p FROM Product p WHERE p.volume = :volume"),
    @NamedQuery(name = "Product.findByCreatedAt", query = "SELECT p FROM Product p WHERE p.createdAt = :createdAt"),
    @NamedQuery(name = "Product.findByCreatedBy", query = "SELECT p FROM Product p WHERE p.createdBy = :createdBy"),
    @NamedQuery(name = "Product.findByUpdatedAt", query = "SELECT p FROM Product p WHERE p.updatedAt = :updatedAt"),
    @NamedQuery(name = "Product.findByUpdatedBy", query = "SELECT p FROM Product p WHERE p.updatedBy = :updatedBy")})
public class Product implements Serializable {
    @Size(max = 100)
    @Column(name = "name")
    private String name;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "description")
    private String description;
    @Size(max = 20)
    @Column(name = "code")
    private String code;
    @Size(max = 256)
    @Column(name = "tags")
    private String tags;
    @Size(max = 45)
    @Column(name = "created_by")
    private String createdBy;
    @Size(max = 45)
    @Column(name = "updated_by")
    private String updatedBy;
    @OneToMany(mappedBy = "product")
    private List<OrderDetail> orderDetailList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
    private List<Comments> commentsList;
    @Column(name = "is_registration_product")
    private Boolean isRegistrationProduct;
    @Column(name = "in_stock")
    private Boolean inStock;
    @OneToMany(mappedBy = "product")
    private List<Inventory> inventoryList;
    @Column(name = "is_combo")
    private Boolean isCombo;
    @JoinColumn(name = "measurement_unit", referencedColumnName = "id")
    @ManyToOne
    private MeasurementUnit measurementUnit;
    @OneToMany(mappedBy = "parent")
    private List<ProductCombo> productComboList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "price")
    private Double price;
    @Column(name = "status")
    private Boolean status;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "weight")
    private Double weight;
    @Column(name = "volume")
    private Double volume;
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    @ManyToMany(mappedBy = "productList")
    private List<Orders> ordersList;
    @ManyToMany(mappedBy = "productList")
    private List<Office> officeList;
    @JoinColumn(name = "category", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Category category;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
    private List<ProductImage> productImageList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
    private List<ProductSalesBonus> productSalesBonusList;

    public Product() {
    }

    public Product(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }


    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getVolume() {
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    @XmlTransient
    @JsonIgnore
    public List<Orders> getOrdersList() {
        return ordersList;
    }

    public void setOrdersList(List<Orders> ordersList) {
        this.ordersList = ordersList;
    }

    @XmlTransient
    @JsonIgnore
    public List<Office> getOfficeList() {
        return officeList;
    }

    public void setOfficeList(List<Office> officeList) {
        this.officeList = officeList;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public MeasurementUnit getMeasurementUnit() {
        return measurementUnit;
    }

    public void setMeasurementUnit(MeasurementUnit measurementUnit) {
        this.measurementUnit = measurementUnit;
    }

    @XmlTransient
    @JsonIgnore
    public List<ProductImage> getProductImageList() {
        return productImageList;
    }

    public void setProductImageList(List<ProductImage> productImageList) {
        this.productImageList = productImageList;
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
        if (!(object instanceof Product)) {
            return false;
        }
        Product other = (Product) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mungwincore.authresource.domain.models.business.Product[ id=" + id + " ]";
    }


    public Boolean getIsCombo() {
        return isCombo;
    }

    public void setIsCombo(Boolean isCombo) {
        this.isCombo = isCombo;
    }


    @XmlTransient
    @JsonIgnore
    public List<ProductCombo> getProductComboList() {
        return productComboList;
    }

    public void setProductComboList(List<ProductCombo> productComboList) {
        this.productComboList = productComboList;
    }

    @XmlTransient
    @JsonIgnore
    public List<Inventory> getInventoryList() {
        return inventoryList;
    }
    public void setInventoryList(List<Inventory> inventoryList) {
        this.inventoryList = inventoryList;
    }


    public Boolean getIsRegistrationProduct() {
        return isRegistrationProduct;
    }

    public void setIsRegistrationProduct(Boolean isRegistrationProduct) {
        this.isRegistrationProduct = isRegistrationProduct;
    }

    public Boolean getInStock() {
        return inStock;
    }

    public void setInStock(Boolean inStock) {
        this.inStock = inStock;
    }
    @XmlTransient
    @JsonIgnore
    public List<Comments> getCommentsList() {
        return commentsList;
    }
    public void setCommentsList(List<Comments> commentsList) {
        this.commentsList = commentsList;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }


    @XmlTransient
    @JsonIgnore
    public List<OrderDetail> getOrderDetailList() {
        return orderDetailList;
    }

    public void setOrderDetailList(List<OrderDetail> orderDetailList) {
        this.orderDetailList = orderDetailList;
    }

    
}
