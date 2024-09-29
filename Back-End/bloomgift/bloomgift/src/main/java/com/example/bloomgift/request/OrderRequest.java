package com.example.bloomgift.request;

import java.util.Date;
import java.util.List;

public class OrderRequest {
    private String specificAddress;
    private String deliveryProvince = "TP.Hồ Chí Minh";
    
    private String deliveryDistrict;
    private String deliveryWard;
    private Integer phone;
    private String note ; 
    private String banner;
    private Date deliveryDateTime;
    private int promotionID ; 
    private int point ; 
    private Boolean transfer;
    private List<OrderDetailRequest> orderDetailRequests;
    public OrderRequest(){
        
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
    public String getDeliveryProvince() {
        return deliveryProvince;
    }
    public void setDeliveryProvince(String deliveryProvince) {
        this.deliveryProvince = deliveryProvince = "TP.Hồ Chí Minh" ;
    }
    public String getDeliveryDistrict() {
        return deliveryDistrict;
    }
    public void setDeliveryDistrict(String deliveryDistrict) {
        this.deliveryDistrict = deliveryDistrict;
    }
    public String getDeliveryWard() {
        return deliveryWard;
    }
    public void setDeliveryWard(String deliveryWard) {
        this.deliveryWard = deliveryWard;
    }
    public Integer getPhone() {
        return phone;
    }
    public void setPhone(Integer phone) {
        this.phone = phone;
    }
    public String getSpecificAddress() {
        return specificAddress;
    }
    public void setSpecificAddress(String specificAddress) {
        this.specificAddress = specificAddress;
    }
    public Boolean getTransfer() {
        return transfer;
    }
    public void setTransfer(Boolean transfer) {
        this.transfer = transfer;
    }
}
