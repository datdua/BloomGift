package com.example.bloomgift.request;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

public class AccountRequest {
    private String fullname; 
    private String email ; 
    private String password; 
    private String address; 
    private String gender; 
    private Date birthday ; 
    private int phone ; 
    private Boolean accountStatus; 
    private String roleName; 
    public AccountRequest(){

    }

    public AccountRequest(String fullname, String email, String password, String address, String gender,
            Date birthday, int phone, Boolean accountStatus, String roleName) {
        this.fullname = fullname;
        this.email = email;
        this.password = password;
        this.address = address;
        this.gender = gender;
        this.birthday = birthday;
        this.phone = phone;
        this.accountStatus = accountStatus;
        this.roleName = roleName;
    }
    public String getFullname() {
        return fullname;
    }
    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
 
    public Date getBirthday() {
        return birthday;
    }
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
    public int getPhone() {
        return phone;
    }
    public void setPhone(int phone) {
        this.phone = phone;
    }
    public Boolean getAccountStatus() {
        return accountStatus;
    }
    public void setAccountStatus(Boolean accountStatus) {
        this.accountStatus = accountStatus;
    }
    public String getRoleName() {
        return roleName;
    }
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
    
}
