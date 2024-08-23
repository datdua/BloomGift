package com.example.bloomgift.request;

import java.util.Date;

public class RegisterRequest {
    private String email;
    private Integer phone ; 
    private String address ; 
    private String fullname ;
    private Boolean gender ; 
    private Date birthday ; 
    private String password;
    public RegisterRequest(){

    }

 

    public RegisterRequest(String email, Integer phone, String address, String fullname, Boolean gender, Date birthday,
            String password) {
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.fullname = fullname;
        this.gender = gender;
        this.birthday = birthday;
        this.password = password;
    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getPhone() {
        return phone;
    }

    public void setPhone(Integer phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getPassword() {
        return password;
    }



    public void setPassword(String password) {
        this.password = password;
    }
    
}
