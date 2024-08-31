package com.example.bloomgift.reponse;

import java.util.Date;
import java.util.List;


public class ProductReponse {
    
    private Integer productID;
    private Float discount;
    private String description;
    private String colour;
    private Float price;
    private Boolean featured;
    private Boolean productStatus;
    private Date createDate;
    private Integer quantity;
    private Integer sold;
    private String productName;
    private String categoryName;
    private String storeName;
    private List<SizeReponse> sizes;
    private List<ProductImageReponse> images;
        public ProductReponse(){

    }


 

    public ProductReponse(Integer productID, Float discount, String description, String colour, Boolean featured,
                Boolean productStatus, Date createDate, Integer quantity, Integer sold, String productName,
                String categoryName, String storeName, List<SizeReponse> sizes, List<ProductImageReponse> images) {
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
            this.categoryName = categoryName;
            this.storeName = storeName;
            this.sizes = sizes;
            this.images = images;
        }






    public ProductReponse(Integer productID, Float discount, String description, String colour, Float price,
            Boolean featured, Boolean productStatus, Date createDate, Integer quantity, Integer sold,
            String productName, String categoryName, String storeName, List<SizeReponse> sizes,
            List<ProductImageReponse> images) {
        this.productID = productID;
        this.discount = discount;
        this.description = description;
        this.colour = colour;
        this.price = price;
        this.featured = featured;
        this.productStatus = productStatus;
        this.createDate = createDate;
        this.quantity = quantity;
        this.sold = sold;
        this.productName = productName;
        this.categoryName = categoryName;
        this.storeName = storeName;
        this.sizes = sizes;
        this.images = images;
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

















    public String getProductName() {
        return productName;
    }


    public void setProductName(String productName) {
        this.productName = productName;
    }




    public List<ProductImageReponse> getImages() {
        return images;
    }




    public void setImages(List<ProductImageReponse> images) {
        this.images = images;
    }







    public List<SizeReponse> getSizes() {
        return sizes;
    }







    public void setSizes(List<SizeReponse> sizes) {
        this.sizes = sizes;
    }




    public Float getPrice() {
        return price;
    }




    public void setPrice(Float price) {
        this.price = price;
    }









  





    
}
