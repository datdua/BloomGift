package com.example.bloomgift.request;

import java.util.Date;

import com.example.bloomgift.model.Role;

public class AccountRequest {
    private String fullname; 
    private String email  ; 
    private String password ; 
    private int phone ; 
    private String address; 
    private Date birthday; 
    private String roleName; 
     private boolean isActive;

    public AccountRequest(){

    }


    public AccountRequest(String fullname, String email, String password, int phone, String address, Date birthday,
    String roleName,boolean isActive) {
        this.fullname = fullname;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.address = address;
        this.birthday = birthday;
        this.roleName = roleName;
        this.isActive = isActive;
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


    public int getPhone() {
        return phone;
    }


    public void setPhone(int phone) {
        this.phone = phone;
    }


    public String getAddress() {
        return address;
    }


    public void setAddress(String address) {
        this.address = address;
    }


    public Date getBirthday() {
        return birthday;
    }


    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }


  


    public boolean isActive() {
        return isActive;
    }


    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }


    public String getRoleName() {
        return roleName;
    }


    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    
}
