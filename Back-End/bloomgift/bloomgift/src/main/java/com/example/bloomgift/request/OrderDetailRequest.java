package com.example.bloomgift.request;

import com.example.bloomgift.model.OrderDetail;

public class OrderDetailRequest {
    private Integer productID ;
    private Integer quantity ;
    private Integer sizeID;
    public OrderDetailRequest(){
        
    }
    public Integer getProductID() {
        return productID;
    }
    public void setProductID(Integer productID) {
        this.productID = productID;
    }
 
    public Integer getQuantity() {
        return quantity;
    }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    public Integer getSizeID() {
        return sizeID;
    }
    public void setSizeID(Integer sizeID) {
        this.sizeID = sizeID;
    }
}
