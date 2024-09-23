package com.example.bloomgift.reponse;

import com.example.bloomgift.model.Product;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class SizeReponse {
    private Integer sizeID; 
    private Float price; 
    private String text;
    private Integer sizeQuantity;
    public SizeReponse(){

    }
    public SizeReponse(Integer sizeID, Float price, String text, Integer sizeQuantity) {
        this.sizeID = sizeID;
        this.price = price;
        this.text = text;
        this.sizeQuantity = sizeQuantity;
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
    public Integer getSizeQuantity() {
        return sizeQuantity;
    }
    public void setSizeQuantity(Integer sizeQuantity) {
        this.sizeQuantity = sizeQuantity;
    }
   

}
