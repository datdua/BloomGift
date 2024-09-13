package com.example.bloomgift.model;

public class CartItem {
    private Integer productId;
    private Integer quantity;
    private String color;
    private String size;
    private Float price;
    private Float totalPrice;
    private String description;
    private String imageUrl;

    public CartItem() {

    }

    public CartItem(Integer productId, Integer quantity, String color, String size, Float price, Float totalPrice,
            String description, String imageUrl) {
        this.productId = productId;
        this.quantity = quantity;
        this.color = color;
        this.size = size;
        this.price = price;
        this.totalPrice = totalPrice;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

}
