package com.example.bloomgift.reponse;

public class ProductImageReponse {
    private Integer imageID; 
    private String productImage ;
    public ProductImageReponse(){

    }
    
  

    public ProductImageReponse(Integer imageID, String productImage) {
        this.imageID = imageID;
        this.productImage = productImage;
    }
    public Integer getImageID() {
        return imageID;
    }
    public void setImageID(Integer imageID) {
        this.imageID = imageID;
    }
    public String getProductImage() {
        return productImage;
    }
    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }
    

}
