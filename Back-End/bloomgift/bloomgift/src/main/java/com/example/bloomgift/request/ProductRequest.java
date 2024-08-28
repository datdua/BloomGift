package com.example.bloomgift.request;

import java.util.Date;
import java.util.List;

import com.example.bloomgift.reponse.ProductImageReponse;

public class ProductRequest {
    private Float price ;
    private Float discount ;
    private String description ;
    private String colour ;
    private Float size ;
    private String productName;
    private Boolean featured ;
    private Integer quantity ;
    private String categoryName;
    private Integer storeID;
    private Boolean productStatus;
    private List<ProductImageRequest> images;
    public ProductRequest(){

    }
    

    public ProductRequest(Float price, Float discount, String description, String colour, Float size,
            String productName, Boolean featured, Integer quantity, String categoryName, Integer storeID,
            Boolean productStatus, List<ProductImageRequest> images) {
        this.price = price;
        this.discount = discount;
        this.description = description;
        this.colour = colour;
        this.size = size;
        this.productName = productName;
        this.featured = featured;
        this.quantity = quantity;
        this.categoryName = categoryName;
        this.storeID = storeID;
        this.productStatus = productStatus;
        this.images = images;
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

   

  

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }



    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }


   

    public Boolean getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(Boolean productStatus) {
        this.productStatus = productStatus;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }



    public List<ProductImageRequest> getImages() {
        return images;
    }



    public void setImages(List<ProductImageRequest> images) {
        this.images = images;
    }







    public Integer getStoreID() {
        return storeID;
    }







    public void setStoreID(Integer storeID) {
        this.storeID = storeID;
    }
    
}
