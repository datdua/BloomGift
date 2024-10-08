package com.example.bloomgift.request;
import java.util.Date;
import java.util.List;


public class OrderRequestNew{
    private DeliveryRequest deliveryRequest;
    private Integer phone;
    private String note;
    private String banner;
    private Date deliveryDateTime;
    private Integer promotionID;
    private int point;
    private Boolean transfer;
    private List<OrderDetailRequest> orderDetailRequests;
    public OrderRequestNew(){
        
    }
    public DeliveryRequest getDeliveryRequest() {
        return deliveryRequest;
    }
    public void setDeliveryRequest(DeliveryRequest deliveryRequest) {
        this.deliveryRequest = deliveryRequest;
    }
    public Integer getPhone() {
        return phone;
    }
    public void setPhone(Integer phone) {
        this.phone = phone;
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
    public Integer getPromotionID() {
        return promotionID;
    }
    public void setPromotionID(Integer promotionID) {
        this.promotionID = promotionID;
    }
    public int getPoint() {
        return point;
    }
    public void setPoint(int point) {
        this.point = point;
    }
    public Boolean getTransfer() {
        return transfer;
    }
    public void setTransfer(Boolean transfer) {
        this.transfer = transfer;
    }
    public List<OrderDetailRequest> getOrderDetailRequests() {
        return orderDetailRequests;
    }
    public void setOrderDetailRequests(List<OrderDetailRequest> orderDetailRequests) {
        this.orderDetailRequests = orderDetailRequests;
    }
}