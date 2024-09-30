package com.example.bloomgift.DTO;

import java.time.LocalDateTime;

public class ComboDTO {

    private Integer comboID;
    private String comboName;
    private String comboDescription;
    private Float comboPrice;
    private LocalDateTime createDate;
    private Boolean comboStatus;

    public ComboDTO() {
    }

    // Getters and Setters
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
}
