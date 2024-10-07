package com.example.bloomgift.request;

public class DeliveryRequest {
    private String specificAddress;
    private String deliveryProvince = "TP.Hồ Chí Minh";
    private String deliveryDistrict;
    private String deliveryWard;
    
    public DeliveryRequest(){}
    public String getSpecificAddress() {
        return specificAddress;
    }
    public void setSpecificAddress(String specificAddress) {
        this.specificAddress = specificAddress;
    }
    public String getDeliveryProvince() {
        return deliveryProvince;
    }
    public void setDeliveryProvince(String deliveryProvince) {
        this.deliveryProvince = deliveryProvince;
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
    
    
    
}
