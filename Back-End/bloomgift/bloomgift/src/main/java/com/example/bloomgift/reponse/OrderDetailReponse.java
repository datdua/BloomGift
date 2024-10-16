package com.example.bloomgift.reponse;

import com.example.bloomgift.model.Product;
import com.example.bloomgift.model.Store;

public class OrderDetailReponse {
    private Integer orderDetailID;
    private Float productTotalPrice;
    private Integer quantity;
    private String productName;
    private String storeName;
    private String  sizeText;
    private Integer productID;
    private Integer storeID;

    public OrderDetailReponse() {
    }

    public Integer getOrderDetailID() {
        return orderDetailID;
    }

    public void setOrderDetailID(Integer orderDetailID) {
        this.orderDetailID = orderDetailID;
    }

    public Float getProductTotalPrice() {
        return productTotalPrice;
    }

    public void setProductTotalPrice(Float productTotalPrice) {
        this.productTotalPrice = productTotalPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getSizeText() {
        return sizeText;
    }

    public void setSizeText(String sizeText) {
        this.sizeText = sizeText;
    }

    public Integer getProductID() {
        return productID;
    }

    public void setProductID(Integer productID) {
        this.productID = productID;
    }

    public Integer getStoreID() {
        return storeID;
    }

    public void setStoreID(Integer storeID) {
        this.storeID = storeID;
    }

    public OrderDetailReponse(Integer orderDetailID, Float productTotalPrice, Integer quantity, String productName,
            String storeName, String sizeText, Integer productID, Integer storeID) {
        this.orderDetailID = orderDetailID;
        this.productTotalPrice = productTotalPrice;
        this.quantity = quantity;
        this.productName = productName;
        this.storeName = storeName;
        this.sizeText = sizeText;
        this.productID = productID;
        this.storeID = storeID;
    }

    

  
   

}
