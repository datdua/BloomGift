package com.example.bloomgift.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Orderr")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderID")
    private Integer orderID;

    @Column(name = "orderPrice")
    private Float orderPrice;

    @Column(name = "orderStatus")
    private String orderStatus;

    @Column(name = "point")
    private int point;

    @Column(name = "deliveryAddress")
    private String deliveryAddress;

    @Column(name = "note")
    private String note;

    @Column(name = "banner")
    private String banner;

    @Column(name = "startDate")
    private Date startDate;

    @Column(name = "deliveryDateTime")
    private Date deliveryDateTime;

    @ManyToOne
    @JoinColumn(name = "accountID")
    @JsonBackReference
    private Account accountID;

    @ManyToOne
    @JoinColumn(name = "promotionID")
    @JsonBackReference
    private Promotion promotionID;



    @OneToMany(mappedBy = "orderID", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderDetail> orderDetail = new ArrayList<>();

    public Order() {

    }

    public Order(Integer orderID, Float orderPrice, String orderStatus, int point, String deliveryAddress, String note,
            String banner, Date startDate, Date deliveryDateTime, Account accountID, Promotion promotionID,
            List<OrderDetail> orderDetail) {
        this.orderID = orderID;
        this.orderPrice = orderPrice;
        this.orderStatus = orderStatus;
        this.point = point;
        this.deliveryAddress = deliveryAddress;
        this.note = note;
        this.banner = banner;
        this.startDate = startDate;
        this.deliveryDateTime = deliveryDateTime;
        this.accountID = accountID;
        this.promotionID = promotionID;
        this.orderDetail = orderDetail;
    }

    public Integer getOrderID() {
        return orderID;
    }

    public void setOrderID(Integer orderID) {
        this.orderID = orderID;
    }

    public Float getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(Float orderPrice) {
        this.orderPrice = orderPrice;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
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

    public Account getAccountID() {
        return accountID;
    }

    public void setAccountID(Account accountID) {
        this.accountID = accountID;
    }

    public Promotion getPromotionID() {
        return promotionID;
    }

    public void setPromotionID(Promotion promotionID) {
        this.promotionID = promotionID;
    }

    public List<OrderDetail> getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(List<OrderDetail> orderDetail) {
        this.orderDetail = orderDetail;
    }

}
