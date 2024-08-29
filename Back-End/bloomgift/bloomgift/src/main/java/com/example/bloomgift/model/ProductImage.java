package com.example.bloomgift.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@Table(name="ProductImage")
public class ProductImage {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "imageID") 
    private Integer imageID; 

    @Column(name = "productImage")
    private String productImage ;

    @ManyToOne
    @JoinColumn(name = "productID")
    private Product productID;

    public ProductImage(){

    }
    
    public ProductImage(Integer imageID, String productImage, Product productID) {
        this.imageID = imageID;
        this.productImage = productImage;
        this.productID = productID;
    }

    public ProductImage(String productImage, Product product) {
        this.productImage = productImage;
        this.productID = product;
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

    public Product getProductID() {
        return productID;
    }

    public void setProductID(Product productID) {
        this.productID = productID;
    }
    
    


    
}
