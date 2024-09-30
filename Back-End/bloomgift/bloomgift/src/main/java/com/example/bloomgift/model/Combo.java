package com.example.bloomgift.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "Combo")
public class Combo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comboID")
    private Integer comboID;

    @Column(name = "comboName", nullable = false, length = 255)
    private String comboName;

    @Column(name = "comboDescription", columnDefinition = "NVARCHAR(MAX)")
    private String comboDescription;

    @Column(name = "comboPrice", nullable = false)
    private Float comboPrice;

    @Column(name = "createDate", columnDefinition = "DATETIME")
    private LocalDateTime createDate;

    @Column(name = "comboStatus", columnDefinition = "BIT", nullable = false)
    private Boolean comboStatus;

    // One combo can have multiple combo products (ComboProduct entities)
    // , orphanRemoval = true
    @OneToMany(mappedBy = "combo", cascade = CascadeType.ALL)
    private List<ComboProduct> comboProducts;

    public Combo() {
    }

    public Integer getComboID() {
        return comboID;
    }

    public void setComboID(Integer comboID) {
        this.comboID = comboID;
    }

    public String getComboName() {
        return comboName;
    }

    public void setComboName(String comboName) {
        this.comboName = comboName;
    }

    public String getComboDescription() {
        return comboDescription;
    }

    public void setComboDescription(String comboDescription) {
        this.comboDescription = comboDescription;
    }

    public Float getComboPrice() {
        return comboPrice;
    }

    public void setComboPrice(Float comboPrice) {
        this.comboPrice = comboPrice;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public Boolean getComboStatus() {
        return comboStatus;
    }

    public void setComboStatus(Boolean comboStatus) {
        this.comboStatus = comboStatus;
    }

    public List<ComboProduct> getComboProducts() {
        return comboProducts;
    }

    public void setComboProducts(List<ComboProduct> comboProducts) {
        this.comboProducts = comboProducts;
    }

    // Lifecycle callback to set default values before persisting
    @PrePersist
    public void prePersist() {
        this.createDate = (this.createDate == null) ? LocalDateTime.now() : this.createDate;
        this.comboStatus = (this.comboStatus == null) ? true : this.comboStatus;
    }
}
