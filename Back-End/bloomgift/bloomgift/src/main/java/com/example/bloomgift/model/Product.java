package com.example.bloomgift.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Product")
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "productID")
    private Integer productID;

    @Column(name = "discount")
    private Float discount;

    @Column(name = "description")
    private String description;

    @Column(name = "colour")
    private String colour;

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

    @Column(name = "price")
    private Float price;

    @ManyToOne
    @JoinColumn(name = "categoryID")
    @JsonBackReference
    private Category categoryID;

    @ManyToOne
    @JoinColumn(name = "storeID")
    @JsonBackReference
    private Store storeID;

    @OneToMany(mappedBy = "productID", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Size> sizes = new ArrayList<>();

    @OneToMany(mappedBy = "productID", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProductImage> productImages = new ArrayList<>();


    @OneToMany(mappedBy = "productID", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderDetail> orderDetails = new ArrayList<>();

    public Product() {

    }

    public Product(Integer productID, Float discount, String description, String colour, Boolean featured,
            Boolean productStatus, Date createDate, Integer quantity, Integer sold, String productName, Float price,
            Category categoryID, Store storeID, List<Size> sizes, List<ProductImage> productImages) {
        this.productID = productID;
        this.discount = discount;
        this.description = description;
        this.colour = colour;
        this.featured = featured;
        this.productStatus = productStatus;
        this.createDate = createDate;
        this.quantity = quantity;
        this.sold = sold;
        this.productName = productName;
        this.price = price;
        this.categoryID = categoryID;
        this.storeID = storeID;
        this.sizes = sizes;
        this.productImages = productImages;
    }

    public Integer getProductID() {
        return productID;
    }

    public void setProductID(Integer productID) {
        this.productID = productID;
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

    public List<Size> getSizes() {
        return sizes;
    }

    public void setSizes(List<Size> sizes) {
        this.sizes = sizes;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

}