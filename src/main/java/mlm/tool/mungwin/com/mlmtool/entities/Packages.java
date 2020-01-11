/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mlm.tool.mungwin.com.mlmtool.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 *
 * @author TNC TECH
 */
@Entity
@Table(name = "packages")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "Packages.findAll", query = "SELECT p FROM Packages p"),
        @NamedQuery(name = "Packages.findById", query = "SELECT p FROM Packages p WHERE p.id = :id"),
        @NamedQuery(name = "Packages.findByName", query = "SELECT p FROM Packages p WHERE p.name = :name"),
        @NamedQuery(name = "Packages.findByDescription", query = "SELECT p FROM Packages p WHERE p.description = :description"),
        @NamedQuery(name = "Packages.findByQualificationPoints", query = "SELECT p FROM Packages p WHERE p.qualificationPoints = :qualificationPoints"),
        @NamedQuery(name = "Packages.findByLogo", query = "SELECT p FROM Packages p WHERE p.logo = :logo"),
        @NamedQuery(name = "Packages.findByNextLevel", query = "SELECT p FROM Packages p WHERE p.nextLevel = :nextLevel"),
        @NamedQuery(name = "Packages.findByCreatedAt", query = "SELECT p FROM Packages p WHERE p.createdAt = :createdAt"),
        @NamedQuery(name = "Packages.findByCreatedBy", query = "SELECT p FROM Packages p WHERE p.createdBy = :createdBy"),
        @NamedQuery(name = "Packages.findByUpdated", query = "SELECT p FROM Packages p WHERE p.updated = :updated")})
public class Packages implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @OneToMany(mappedBy = "packageId")
    private List<Customer> customersList;
    private static final long serialVersionUID = 1L;
    //@Size(max = 50)
    @Column(name = "name")
    private String name;
    //@Size(max = 256)
    @Column(name = "description")
    private String description;
    @Column(name = "qualification_points")
    private Integer qualificationPoints;
    @Column(name = "logo")
    private String logo;
    @Column(name = "status")
    private Boolean status;
    @OneToMany(mappedBy = "nextLevel")
    private Collection<Packages> previousLevels;
    @JoinColumn(name = "next_level", referencedColumnName = "id")
    @ManyToOne
    private Packages nextLevel;
    @Column(name = "direct_referral_percentage")
    private Double directReferralPercentage;
    @Column(name = "registration_points")
    private Integer registrationPoints;
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    //@Size(max = 45)
    @Column(name = "created_by")
    private String createdBy;
    @Column(name = "updated")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updated;

    public Packages() {
    }

    public Packages(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getQualificationPoints() {
        return qualificationPoints;
    }

    public void setQualificationPoints(Integer qualificationPoints) {
        this.qualificationPoints = qualificationPoints;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Collection<Packages> getPreviousLevels() {
        return previousLevels;
    }

    public void setPreviousLevels(Collection<Packages> previousLevels) {
        this.previousLevels = previousLevels;
    }

    public Packages getNextLevel() {
        return nextLevel;
    }

    public void setNextLevel(Packages nextLevel) {
        this.nextLevel = nextLevel;
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

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Double getDirectReferralPercentage() {
        return directReferralPercentage;
    }

    public void setDirectReferralPercentage(Double directReferralPercentage) {
        this.directReferralPercentage = directReferralPercentage;
    }

    public Integer getRegistrationPoints() {
        return registrationPoints;
    }

    public void setRegistrationPoints(Integer registrationPoints) {
        this.registrationPoints = registrationPoints;
    }

}
