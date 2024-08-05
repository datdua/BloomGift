package com.example.bloomgift.DTO;

public class AdminPublicInfoDTO {
  
    private String emailAdmin;
    private String adminName;
    private Boolean isActive;


    public AdminPublicInfoDTO(){

    }
    public AdminPublicInfoDTO(String emailAdmin, String adminName, Boolean isActive) {
        this.emailAdmin = emailAdmin;
        this.adminName = adminName;
        this.isActive = isActive;
    }
    public String getEmailAdmin() {
        return emailAdmin;
    }
    public void setEmailAdmin(String emailAdmin) {
        this.emailAdmin = emailAdmin;
    }
    public String getAdminName() {
        return adminName;
    }
    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }
    public Boolean getIsActive() {
        return isActive;
    }
    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
    
    
}
