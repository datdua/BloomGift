package com.example.bloomgift.DTO;



public class StaffPublicInfoDTO {
    private Integer phoneShop;
    private String addressShop;
    private String emailShop;
    private java.util.Date dateStartShop;
    private String ShopName;
    private Boolean isActive;

    public StaffPublicInfoDTO(){

    }
    public StaffPublicInfoDTO(Integer phoneShop, String addressShop, String emailShop, java.util.Date dateStartShop, String ShopName,
            boolean isActive) {
                this.phoneShop = phoneShop;
                this.addressShop = addressShop;
                this.emailShop = emailShop;
                this.dateStartShop = dateStartShop;
                this.ShopName = ShopName;
                this.isActive = isActive;
    
    }
    public Integer getPhoneShop() {
        return phoneShop;
    }
    public void setPhoneShop(Integer phoneShop) {
        this.phoneShop = phoneShop;
    }
    public String getAddressShop() {
        return addressShop;
    }
    public void setAddressShop(String addressShop) {
        this.addressShop = addressShop;
    }
    public String getEmailShop() {
        return emailShop;
    }
    public void setEmailShop(String emailShop) {
        this.emailShop = emailShop;
    }
    public java.util.Date getDateStartShop() {
        return dateStartShop;
    }
    public void setDateStartShop(java.util.Date dateStartShop) {
        this.dateStartShop = dateStartShop;
    }
    public String getShopName() {
        return ShopName;
    }
    public void setShopName(String shopName) {
        ShopName = shopName;
    }
    public Boolean getIsActive() {
        return isActive;
    }
    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
   
    

}
