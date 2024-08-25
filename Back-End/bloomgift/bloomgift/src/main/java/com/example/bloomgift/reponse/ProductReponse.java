package com.example.bloomgift.reponse;

import java.util.Date;


public class ProductReponse {
    
    private Integer productID; 
    private Float price ;
    private Float discount ;
    private String description ;
    private String color ;
    private Float size ;
    private Boolean featured ;
    private Float productStatus ;
    private Date createDate ;
    private Integer quantity ;
    private Integer sold ;
    private String categoryName;
    private String storeName;






    public Integer getProductID() {
        return productID;
    }
    public void setProductID(Integer productID) {
        this.productID = productID;
    }
    public Float getPrice() {
        return price;
    }
    public void setPrice(Float price) {
        this.price = price;
    }
    public Float getDiscount() {
        return discount;
    }
    public void setDiscount(Float discount) {
        this.discount = discount;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }
    public Float getSize() {
        return size;
    }
    public void setSize(Float size) {
        this.size = size;
    }
    public Boolean getFeatured() {
        return featured;
    }
    public void setFeatured(Boolean featured) {
        this.featured = featured;
    }
    public Float getProductStatus() {
        return productStatus;
    }
    public void setProductStatus(Float productStatus) {
        this.productStatus = productStatus;
    }
    public Date getCreateDate() {
        return createDate;
    }
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    public Integer getQuantity() {
        return quantity;
    }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    public Integer getSold() {
        return sold;
    }
    public void setSold(Integer sold) {
        this.sold = sold;
    }
    public String getCategoryName() {
        return categoryName;
    }
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    public String getStoreName() {
        return storeName;
    }
    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    
}
