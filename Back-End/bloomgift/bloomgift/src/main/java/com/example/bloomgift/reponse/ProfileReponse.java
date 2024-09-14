package com.example.bloomgift.reponse;

import java.util.Date;

public class ProfileReponse {
    private int accountID;
    private String fullname; 
    private String email ; 
    private String password; 
    private String address; 
    private String gender; 
    private String avatar ; 
    private Date birthday ; 
    private int phone ; 
    
    public ProfileReponse(){

    }

    public ProfileReponse(int accountID, String fullname, String email, String password, String address, String gender,
            String avatar, Date birthday, int phone) {
        this.accountID = accountID;
        this.fullname = fullname;
        this.email = email;
        this.password = password;
        this.address = address;
        this.gender = gender;
        this.avatar = avatar;
        this.birthday = birthday;
        this.phone = phone;
    }

    public int getAccountID() {
        return accountID;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
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
    
}

