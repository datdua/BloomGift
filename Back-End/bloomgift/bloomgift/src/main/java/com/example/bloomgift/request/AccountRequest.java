package com.example.bloomgift.request;

import java.util.Date;

public class AccountRequest {
    private String fullname; 
    private String email ; 
    private String password; 
    private String address; 
    private Boolean gender; 
    private String avatar ; 
    private Date birthday ; 
    private int phone ; 
    private Boolean accountStatus; 
    private String roleName; 
    public AccountRequest(){

    }
    public AccountRequest(String fullname, String email, String password, String address, Boolean gender, String avatar,
            Date birthday, int phone, Boolean accountStatus, String roleName) {
        this.fullname = fullname;
        this.email = email;
        this.password = password;
        this.address = address;
        this.gender = gender;
        this.avatar = avatar;
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
    public Boolean getGender() {
        return gender;
    }
    public void setGender(Boolean gender) {
        this.gender = gender;
    }
    public String getAvatar() {
        return avatar;
    }
    public void setAvatar(String avatar) {
        this.avatar = avatar;
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
