package com.example.bloomgift.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "promotion")
public class Promotion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "promotionID")
    private Integer promotionID;

    @Column(name = "promotionName")
    private String promotionName;

    @Column(name = "promotionCode")
    private String promotionCode;

    @Column(name = "promotionDescription")
    private String promotionDescription;

    @Column(name = "promotionDiscount", precision = 10, scale = 2)
    private BigDecimal promotionDiscount;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "promotionStatus")
    private String promotionStatus;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "startDate")
    private LocalDateTime startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "endDate")
    private LocalDateTime endDate;

    @ManyToOne
    @JoinColumn(name = "storeID", nullable = true)
    private Store storeID;

    public Promotion() {
    }

    public Promotion(Integer promotionID, String promotionName, String promotionCode, String promotionDescription,
            BigDecimal promotionDiscount, Integer quantity, String promotionStatus, LocalDateTime startDate,
            LocalDateTime endDate, Store storeID) {
        this.promotionID = promotionID;
        this.promotionName = promotionName;
        this.promotionCode = promotionCode;
        this.promotionDescription = promotionDescription;
        this.promotionDiscount = promotionDiscount;
        this.quantity = quantity;
        this.promotionStatus = promotionStatus;
        this.startDate = startDate;
        this.endDate = endDate;
        this.storeID = storeID;
    }

    public Integer getPromotionID() {
        return promotionID;
    }

    public void setPromotionID(Integer promotionID) {
        this.promotionID = promotionID;
    }

    public String getPromotionName() {
        return promotionName;
    }


    public void setPromotionName(String promotionName) {
        this.promotionName = promotionName;
    }

    public String getPromotionCode() {
        return promotionCode;
    }

    public void setPromotionCode(String promotionCode) {
        this.promotionCode = promotionCode;
    }

    public String getPromotionDescription() {
        return promotionDescription;
    }

    public void setPromotionDescription(String promotionDescription) {
        this.promotionDescription = promotionDescription;
    }

    public BigDecimal getPromotionDiscount() {
        return promotionDiscount;
    }

    public void setPromotionDiscount(BigDecimal promotionDiscount) {
        this.promotionDiscount = promotionDiscount;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getPromotionStatus() {
        return promotionStatus;
    }

    public void setPromotionStatus(String promotionStatus) {
        this.promotionStatus = promotionStatus;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public Store getStoreID() {
        return storeID;
    }

    public void setStoreID(Store storeID) {
        this.storeID = storeID;
    }
}
