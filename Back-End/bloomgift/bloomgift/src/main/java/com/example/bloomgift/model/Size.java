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
@Table(name = "Size")
public class Size {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sizeID") 
    private Integer sizeID; 

    @Column(name = "price")
    private Float price; 

    @Column(name = "text")
    private String text;
    
    @Column(name = "sizeFloat")
    private Float sizeFloat;

    @ManyToOne
    @JoinColumn(name = "productID")
    private Product productID;

    public Size(){

    }
    

    public Size(Integer sizeID, Float price, String text, Float sizeFloat, Product productID) {
        this.sizeID = sizeID;
        this.price = price;
        this.text = text;
        this.sizeFloat = sizeFloat;
        this.productID = productID;
    }


    public Integer getSizeID() {
        return sizeID;
    }

    public void setSizeID(Integer sizeID) {
        this.sizeID = sizeID;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Float getSizeFloat() {
        return sizeFloat;
    }

    public void setSizeFloat(Float sizeFloat) {
        this.sizeFloat = sizeFloat;
    }

    public Product getProductID() {
        return productID;
    }

    public void setProductID(Product productID) {
        this.productID = productID;
    }
    
}
