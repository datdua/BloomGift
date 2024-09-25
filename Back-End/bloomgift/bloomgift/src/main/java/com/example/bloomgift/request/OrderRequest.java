package com.example.bloomgift.request;

import java.util.Date;
import java.util.List;

public class OrderRequest {
    private String deliveryAddress;
    private String note ; 
    private String banner;
    private Date deliveryDateTime;
    private int promotionID ; 
    private int point ; 
    private List<OrderDetailRequest> orderDetailRequests;
    public OrderRequest(){
        
    }
    public String getDeliveryAddress() {
        return deliveryAddress;
    }
    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }
    public String getNote() {
        return note;
    }
    public void setNote(String note) {
        this.note = note;
    }
    public String getBanner() {
        return banner;
    }
    public void setBanner(String banner) {
        this.banner = banner;
    }
    public Date getDeliveryDateTime() {
        return deliveryDateTime;
    }
    public void setDeliveryDateTime(Date deliveryDateTime) {
        this.deliveryDateTime = deliveryDateTime;
    }
    public int getPromotionID() {
        return promotionID;
    }
    public void setPromotionID(int promotionID) {
        this.promotionID = promotionID;
    }
    public int getPoint() {
        return point;
    }
    public void setPoint(int point) {
        this.point = point;
    }
    public List<OrderDetailRequest> getOrderDetailRequests() {
        return orderDetailRequests;
    }
    public void setOrderDetailRequests(List<OrderDetailRequest> orderDetailRequests) {
        this.orderDetailRequests = orderDetailRequests;
    }
}
