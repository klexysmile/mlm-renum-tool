/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mlm.tool.mungwin.com.mlmtool.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author TNC TECH
 */
@Entity
@Table(name = "customers")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Customers.findAll", query = "SELECT c FROM Customer c"),
    @NamedQuery(name = "Customers.findById", query = "SELECT c FROM Customer c WHERE c.id = :id"),
    @NamedQuery(name = "Customers.findByAuthUuid", query = "SELECT c FROM Customer c WHERE c.authUuid = :authUuid"),
    @NamedQuery(name = "Customers.findByEmail", query = "SELECT c FROM Customer c WHERE c.email = :email"),
    @NamedQuery(name = "Customers.findByBirthDate", query = "SELECT c FROM Customer c WHERE c.birthDate = :birthDate"),
    @NamedQuery(name = "Customers.findByBirthPlace", query = "SELECT c FROM Customer c WHERE c.birthPlace = :birthPlace"),
    @NamedQuery(name = "Customers.findByIdNumber", query = "SELECT c FROM Customer c WHERE c.idNumber = :idNumber"),
    @NamedQuery(name = "Customers.findByFirstName", query = "SELECT c FROM Customer c WHERE c.firstName = :firstName"),
    @NamedQuery(name = "Customers.findByLastName", query = "SELECT c FROM Customer c WHERE c.lastName = :lastName"),
    @NamedQuery(name = "Customers.findByGender", query = "SELECT c FROM Customer c WHERE c.gender = :gender"),
    @NamedQuery(name = "Customers.findByAddress", query = "SELECT c FROM Customer c WHERE c.address = :address"),
    @NamedQuery(name = "Customers.findByCity", query = "SELECT c FROM Customer c WHERE c.city = :city"),
    @NamedQuery(name = "Customers.findByCountry", query = "SELECT c FROM Customer c WHERE c.country = :country"),
    @NamedQuery(name = "Customers.findByState", query = "SELECT c FROM Customer c WHERE c.state = :state"),
    @NamedQuery(name = "Customers.findByZipCode", query = "SELECT c FROM Customer c WHERE c.zipCode = :zipCode"),
    @NamedQuery(name = "Customers.findByNationality", query = "SELECT c FROM Customer c WHERE c.nationality = :nationality"),
    @NamedQuery(name = "Customers.findByActivated", query = "SELECT c FROM Customer c WHERE c.activated = :activated"),
    @NamedQuery(name = "Customers.findByPhone", query = "SELECT c FROM Customer c WHERE c.phone = :phone"),
    @NamedQuery(name = "Customers.findByRegistrationCode", query = "SELECT c FROM Customer c WHERE c.registrationCode = :registrationCode"),
    @NamedQuery(name = "Customers.findByProfilePicture", query = "SELECT c FROM Customer c WHERE c.profilePicture = :profilePicture"),
    @NamedQuery(name = "Customers.findByLineLevel", query = "SELECT c FROM Customer c WHERE c.lineLevel = :lineLevel"),
    @NamedQuery(name = "Customers.findByUpLineId", query = "SELECT c FROM Customer c WHERE c.upLineId = :upLineId"),
    @NamedQuery(name = "Customers.findByCreatedAt", query = "SELECT c FROM Customer c WHERE c.createdAt = :createdAt"),
    @NamedQuery(name = "Customers.findByUpdatedAt", query = "SELECT c FROM Customer c WHERE c.updatedAt = :updatedAt"),
    @NamedQuery(name = "Customers.findByUpdatedBy", query = "SELECT c FROM Customer c WHERE c.updatedBy = :updatedBy")})
public class Customer implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "auth_uuid")
    private String authUuid;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 100)
    @Column(name = "email")
    private String email;
    @Column(name = "birth_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date birthDate;
    @Size(max = 50)
    @Column(name = "birth_place")
    private String birthPlace;
    @Size(max = 12)
    @Column(name = "id_number")
    private String idNumber;
    @Size(max = 256)
    @Column(name = "first_name")
    private String firstName;
    @Size(max = 256)
    @Column(name = "last_name")
    private String lastName;
    @Size(max = 45)
    @Column(name = "gender")
    private String gender;
    @Size(max = 256)
    @Column(name = "address")
    private String address;
    @Size(max = 256)
    @Column(name = "city")
    private String city;
    @Column(name = "country")
    private Integer country;
    @Size(max = 256)
    @Column(name = "state")
    private String state;
    @Size(max = 45)
    @Column(name = "zip_code")
    private String zipCode;
    @Size(max = 256)
    @Column(name = "nationality")
    private String nationality;
    @Column(name = "activated")
    private Boolean activated;
    // @Pattern(regexp="^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$", message="Invalid phone/fax format, should be as xxx-xxx-xxxx")//if the field contains phone or fax number consider using this annotation to enforce field validation
    @Size(max = 256)
    @Column(name = "phone")
    private String phone;
    @Size(max = 256)
    @Column(name = "registration_code")
    private String registrationCode;
    @Size(max = 255)
    @Column(name = "profile_picture")
    private String profilePicture;
    @Column(name = "line_level")
    private Integer lineLevel;
    @Column(name = "up_line_id")
    private BigInteger upLineId;
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    @Column(name = "updated_by")
    private BigInteger updatedBy;
    @JoinColumn(name = "package_id", referencedColumnName = "id")
    @ManyToOne
    private Packages packageId;
    @OneToMany(mappedBy = "parentId")
    private List<CustomerLink> downLinks;
    @OneToOne(mappedBy = "childId")
    private CustomerLink upLink;

    public Customer() {
    }

    public Customer(Long id) {
        this.id = id;
    }

    public Customer(Long id, String authUuid) {
        this.id = id;
        this.authUuid = authUuid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthUuid() {
        return authUuid;
    }

    public void setAuthUuid(String authUuid) {
        this.authUuid = authUuid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getCountry() {
        return country;
    }

    public void setCountry(Integer country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public Boolean getActivated() {
        return activated;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRegistrationCode() {
        return registrationCode;
    }

    public void setRegistrationCode(String registrationCode) {
        this.registrationCode = registrationCode;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public Integer getLineLevel() {
        return lineLevel;
    }

    public void setLineLevel(Integer lineLevel) {
        this.lineLevel = lineLevel;
    }

    public BigInteger getUpLineId() {
        return upLineId;
    }

    public void setUpLineId(BigInteger upLineId) {
        this.upLineId = upLineId;
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

    public BigInteger getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(BigInteger updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Packages getPackageId() {
        return packageId;
    }

    public void setPackageId(Packages packageId) {
        this.packageId = packageId;
    }

    @XmlTransient
    @JsonIgnore
    public CustomerLink getUpLink() {
        return upLink;
    }

    public void setUpLink(CustomerLink customerLinkCollection1) {
        this.upLink = customerLinkCollection1;
    }

    @XmlTransient
    @JsonIgnore
    public List<CustomerLink> getDownLinks() {
        return downLinks;
    }

    public void setDownLinks(List<CustomerLink> customerDownLines) {
        this.downLinks = customerDownLines;
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
        if (!(object instanceof Customer)) {
            return false;
        }
        Customer other = (Customer) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mlm.tool.mungwin.com.mlmtool.entities.Customer[ id=" + id + " ]";
    }
    
}
