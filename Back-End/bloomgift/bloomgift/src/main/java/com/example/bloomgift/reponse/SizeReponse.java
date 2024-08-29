package com.example.bloomgift.reponse;

import com.example.bloomgift.model.Product;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class SizeReponse {
    private Integer sizeID; 
    private Float price; 
    private String text;
    private Float sizeFloat;
    public SizeReponse(){

    }
    public SizeReponse(Integer sizeID, Float price, String text, Float sizeFloat) {
        this.sizeID = sizeID;
        this.price = price;
        this.text = text;
        this.sizeFloat = sizeFloat;
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
    

}
