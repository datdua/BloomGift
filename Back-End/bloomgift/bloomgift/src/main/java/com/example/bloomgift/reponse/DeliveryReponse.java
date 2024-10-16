package com.example.bloomgift.reponse;

public class DeliveryReponse {
    private Integer deliveryID;
    private float ship;
    private String orderCode;
    private float codShip;
    private  Boolean status;
    private Boolean freeShip;
    public DeliveryReponse(){
        
    }
    public Integer getDeliveryID() {
        return deliveryID;
    }
    public void setDeliveryID(Integer deliveryID) {
        this.deliveryID = deliveryID;
    }
    public float getShip() {
        return ship;
    }
    public void setShip(float ship) {
        this.ship = ship;
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
    public DeliveryReponse(Integer deliveryID, float ship, String orderCode, float codShip, Boolean status,
            Boolean freeShip) {
        this.deliveryID = deliveryID;
        this.ship = ship;
        this.orderCode = orderCode;
        this.codShip = codShip;
        this.status = status;
        this.freeShip = freeShip;
    }
}
