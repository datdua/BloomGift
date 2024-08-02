package com.example.bloomgift.request;

import java.util.Date;

public class RegisterRequest {

    private String fullname; 
    private String email  ; 
    private String password ; 
    private int phone ; 
    private String address; 
    private Date birthday; 
   
    

    public RegisterRequest(){

    }
    public RegisterRequest(String fullname, String email, String password, int  phone, String address, Date birthday, Boolean active) {
        this.fullname  = fullname;
        this.email = email; 
        this.password = password;
        this.phone = phone;
        this.email = email;
        this.address = address;
       
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

   
}
