package com.example.bloomgift.reponse;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.example.bloomgift.model.Store;
import com.fasterxml.jackson.annotation.JsonFormat;


public class PromotionResponse {
    
    private Integer promotionID;
    private String promotionName;
    private String promotionCode;
    private String promotionDescription;
    private BigDecimal promotionDiscount;
    private Integer quantity;
    private String promotionStatus;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endDate;
    private String storeName;

    public PromotionResponse() {
    }

    public PromotionResponse(Integer promotionID, String promotionName, String promotionCode, String promotionDescription,
            BigDecimal promotionDiscount, Integer quantity, String promotionStatus, LocalDateTime startDate,
            LocalDateTime endDate, String storeName) {
        this.promotionID = promotionID;
        this.promotionName = promotionName;
        this.promotionCode = promotionCode;
        this.promotionDescription = promotionDescription;
        this.promotionDiscount = promotionDiscount;
        this.quantity = quantity;
        this.promotionStatus = promotionStatus;
        this.startDate = startDate;
        this.endDate = endDate;
        this.storeName = storeName;
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

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
}
