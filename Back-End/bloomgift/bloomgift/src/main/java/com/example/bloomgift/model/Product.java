package com.example.bloomgift.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="Product")
public class Product {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "productID") 
    private Integer productID; 
    

    @Column(name = "price")
    private Float price ;
    
    @Column(name = "discount")
    private Float discount ;

    @Column(name = "description")
    private String description ;

    @Column(name = "color")
    private String color ;

    @Column(name = "size")
    private Float size ;

    @Column(name = "featured")
    private Boolean featured ;

    @Column(name = "productStatus")
    private Float productStatus ;

    @Column(name = "createDate")
    private Date createDate ;

    @Column(name = "quantity")
    private Integer quantity ;

    @Column(name = "sold")
    private Integer sold ;

    @ManyToOne
    @JoinColumn(name = "categoryID")
    private Category categoryID;

    @ManyToOne
    @JoinColumn(name = "storeID")
    private Store storeID;

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

    public Category getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(Category categoryID) {
        this.categoryID = categoryID;
    }

    public Store getStoreID() {
        return storeID;
    }

    public void setStoreID(Store storeID) {
        this.storeID = storeID;
    }

    
}
