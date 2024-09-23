package com.example.bloomgift.request;

public class SizeRequest {
    private Float price; 
    private String text;
    private Integer sizeQuanity;
    public SizeRequest(){

    }
    public SizeRequest(Float price, String text, Integer sizeQuanity) {
        this.price = price;
        this.text = text;
        this.sizeQuanity = sizeQuanity;
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
    public Integer getSizeQuanity() {
        return sizeQuanity;
    }
    public void setSizeQuanity(Integer sizeQuanity) {
        this.sizeQuanity = sizeQuanity;
    }
    
    

    
}
