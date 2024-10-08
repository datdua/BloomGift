package com.example.bloomgift.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name ="Delivery")
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "deliveryID")
    private Integer deliveryID;

    @Column(name = "ship")
    private float ship;

    @Column(name = "orderCode")
    private String orderCode;
    
    @Column(name = "codShip")
    private float codShip;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "freeShip")
    private Boolean freeShip;

    @ManyToOne
    @JoinColumn(name = "orderID")  // Khóa ngoại tham chiếu đến Store
    private Order orderID;  

    @ManyToOne
    @JoinColumn(name = "storeID")
    private Store storeID; 

    public Delivery(){

    }

    public Integer getDeliveryID() {
        return deliveryID;
    }

    public void setDeliveryID(Integer deliveryID) {
        this.deliveryID = deliveryID;
    }

 


    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public float getCodShip() {
        return codShip;
    }

    public void setCodShip(float codShip) {
        this.codShip = codShip;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Boolean getFreeShip() {
        return freeShip;
    }

    public void setFreeShip(Boolean freeShip) {
        this.freeShip = freeShip;
    }

    public Order getOrderID() {
        return orderID;
    }

    public Store getStoreID() {
        return storeID;
    }

    public void setOrderID(Order orderID) {
        this.orderID = orderID;
    }

    public void setStoreID(Store storeID) {
        this.storeID = storeID;
    }

    public float getShip() {
        return ship;
    }

    public void setShip(float ship) {
        this.ship = ship;
    }




}
