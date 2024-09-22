package com.example.bloomgift.reponse;

import java.util.Date;
import java.util.List;

import com.example.bloomgift.model.Account;
import com.example.bloomgift.model.Promotion;

public class OrderReponse {
    private Integer orderID;
    private Float oderPrice;
    private String orderStatus;
    private Integer point;
    private String banner;
    private String note;
    private Date startDate;
    private Date deliveryDateTime;
    private String deliveryAddress;
    private String accountName;
    private String promotionCode;
    private List<OrderDetailReponse> orderDetails;

  
    public OrderReponse(){}


    public Integer getOrderID() {
        return orderID;
    }


    public void setOrderID(Integer orderID) {
        this.orderID = orderID;
    }


    public Float getOderPrice() {
        return oderPrice;
    }


    public void setOderPrice(Float oderPrice) {
        this.oderPrice = oderPrice;
    }


    public String getOrderStatus() {
        return orderStatus;
    }


    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }


    public Integer getPoint() {
        return point;
    }


    public void setPoint(Integer point) {
        this.point = point;
    }


    public String getBanner() {
        return banner;
    }


    public void setBanner(String banner) {
        this.banner = banner;
    }


    public String getNote() {
        return note;
    }


    public void setNote(String note) {
        this.note = note;
    }


    public Date getStartDate() {
        return startDate;
    }


    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }


    public Date getDeliveryDateTime() {
        return deliveryDateTime;
    }


    public void setDeliveryDateTime(Date deliveryDateTime) {
        this.deliveryDateTime = deliveryDateTime;
    }


    public String getDeliveryAddress() {
        return deliveryAddress;
    }


    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }


    public String getAccountName() {
        return accountName;
    }


    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }


    public String getPromotionCode() {
        return promotionCode;
    }


    public void setPromotionCode(String promotionCode) {
        this.promotionCode = promotionCode;
    }


    public List<OrderDetailReponse> getOrderDetails() {
        return orderDetails;
    }


    public void setOrderDetails(List<OrderDetailReponse> orderDetails) {
        this.orderDetails = orderDetails;
    }


    public OrderReponse(Integer orderID, Float oderPrice, String orderStatus, Integer point, String banner, String note,
            Date startDate, Date deliveryDateTime, String deliveryAddress, String accountName, String promotionCode,
            List<OrderDetailReponse> orderDetails) {
        this.orderID = orderID;
        this.oderPrice = oderPrice;
        this.orderStatus = orderStatus;
        this.point = point;
        this.banner = banner;
        this.note = note;
        this.startDate = startDate;
        this.deliveryDateTime = deliveryDateTime;
        this.deliveryAddress = deliveryAddress;
        this.accountName = accountName;
        this.promotionCode = promotionCode;
        this.orderDetails = orderDetails;
    }


  
}
