package com.example.bloomgift.request;

public class SizeRequest {
    private Float price; 
    private String text;
    private Float sizeFloat;
    public SizeRequest(){

    }
    
    public SizeRequest(Float price, String text, Float sizeFloat) {
        this.price = price;
        this.text = text;
        this.sizeFloat = sizeFloat;
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
