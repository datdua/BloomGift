package com.example.bloomgift.request;

public class SizeRequest {
    private Float price; 
    private String text;
    private Integer sizeQuantity;
    public SizeRequest(){

    }
    public SizeRequest(Float price, String text, Integer sizeQuantity) {
        this.price = price;
        this.text = text;
        this.sizeQuantity = sizeQuantity;
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
