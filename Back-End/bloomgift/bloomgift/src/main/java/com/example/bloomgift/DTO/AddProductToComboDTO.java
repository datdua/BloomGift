package com.example.bloomgift.DTO;

public class AddProductToComboDTO {
    private Integer comboID;
    private Integer productID;
    private Integer quantity;

    // Getters and Setters
    public Integer getComboID() {
        return comboID;
    }

    public void setComboID(Integer comboID) {
        this.comboID = comboID;
    }

    public Integer getProductID() {
        return productID;
    }

    public void setProductID(Integer productID) {
        this.productID = productID;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
