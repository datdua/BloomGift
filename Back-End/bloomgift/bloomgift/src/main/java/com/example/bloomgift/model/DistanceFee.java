package com.example.bloomgift.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "DistanceFee")
public class DistanceFee {
  @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "distanceFeeID")
    private Integer distanceFeeID;

    @Column(name = "fee")
    private float fee;

    @Column(name = "range")
    private float range;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "storeID", referencedColumnName = "storeID")  // Khóa ngoại tham chiếu đến Store
    private Store store;  
    public DistanceFee(){
        
    }

    public Integer getDistanceFeeID() {
        return distanceFeeID;
    }

    public void setDistanceFeeID(Integer distanceFeeID) {
        this.distanceFeeID = distanceFeeID;
    }

    public float getFee() {
        return fee;
    }

    public void setFee(float fee) {
        this.fee = fee;
    }

    public float getRange() {
        return range;
    }

    public void setRange(float range) {
        this.range = range;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }



  




}
