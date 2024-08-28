package com.example.bloomgift.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import jakarta.persistence.*;

@Entity
@Table(name = "Product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "productID")
    private Integer productID;

    @Column(name = "price")
    private Float price;

    @Column(name = "discount")
    private Float discount;

    @Column(name = "description")
    private String description;

    @Column(name = "colour")
    private String colour;

    @Column(name = "size")
    private Float size;

    @Column(name = "featured")
    private Boolean featured;

    @Column(name = "productStatus")
    private Boolean productStatus;

    @Column(name = "createDate")
    private Date createDate;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "sold")
    private Integer sold;

    @Column(name = "productName")
    private String productName;

    @ManyToOne
    @JoinColumn(name = "categoryID")
    private Category categoryID;

    @ManyToOne
    @JoinColumn(name = "storeID")
    private Store storeID;


    @OneToMany(mappedBy = "productID", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProductImage> productImages;

    public Product() {

    }

    public Product(Integer productID, Float price, Float discount, String description, String colour, Float size,
            Boolean featured, Boolean productStatus, Date createDate, Integer quantity, Integer sold,
            String productName, Category categoryID, Store storeID, List<ProductImage> productImages) {
        this.productID = productID;
        this.price = price;
        this.discount = discount;
        this.description = description;
        this.colour = colour;
        this.size = size;
        this.featured = featured;
        this.productStatus = productStatus;
        this.createDate = createDate;
        this.quantity = quantity;
        this.sold = sold;
        this.productName = productName;
        this.categoryID = categoryID;
        this.storeID = storeID;
        this.productImages = productImages;
    }

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

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
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

    public Boolean getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(Boolean productStatus) {
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

    public void setCategoryID(Category category) {
        this.categoryID = category;
    }

    public Store getStoreID() {
        return storeID;
    }

    public void setStoreID(Store storeID) {
        this.storeID = storeID;
    }

    public String getCategoryName() {
        return categoryID != null ? categoryID.getCategoryName() : null;
    }

    public String getStoreName() {
        return storeID != null ? storeID.getStoreName() : null;
    }

    public List<ProductImage> getProductImages() {
        return productImages;
    }

    public void setProductImages(List<ProductImage> productImages) {
        this.productImages = productImages;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

}
