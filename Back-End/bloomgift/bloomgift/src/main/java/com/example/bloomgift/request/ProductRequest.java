package com.example.bloomgift.request;

import java.util.List;


public class ProductRequest {
    private Float discount ;
    private String description ;
    private String colour ;
    private String productName;
    private Boolean featured ;
    private Integer quantity ;
    private String categoryName;
    private Integer storeID;
    private Boolean productStatus;
    private Float price;
    private List<SizeRequest> sizes;
    public ProductRequest(){

    }
    
    








    public ProductRequest(Float discount, String description, String colour, String productName, Boolean featured,
            Integer quantity, String categoryName, Integer storeID, Boolean productStatus, Float price,
            List<SizeRequest> sizes) {
        this.discount = discount;
        this.description = description;
        this.colour = colour;
        this.productName = productName;
        this.featured = featured;
        this.quantity = quantity;
        this.categoryName = categoryName;
        this.storeID = storeID;
        this.productStatus = productStatus;
        this.price = price;
        this.sizes = sizes;
    }










    public ProductRequest(Float discount, String description, String colour, String productName, Boolean featured,
            Integer quantity, String categoryName, Integer storeID, Boolean productStatus, List<SizeRequest> sizes) {
        this.discount = discount;
        this.description = description;
        this.colour = colour;
        this.productName = productName;
        this.featured = featured;
        this.quantity = quantity;
        this.categoryName = categoryName;
        this.storeID = storeID;
        this.productStatus = productStatus;
        this.sizes = sizes;

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




    public Integer getStoreID() {
        return storeID;
    }







    public void setStoreID(Integer storeID) {
        this.storeID = storeID;
    }










    public List<SizeRequest> getSizes() {
        return sizes;
    }










    public void setSizes(List<SizeRequest> sizes) {
        this.sizes = sizes;
    }










    public Float getPrice() {
        return price;
    }










    public void setPrice(Float price) {
        this.price = price;
    }
    
}
