package com.example.bloomgift.reponse;

import java.util.Date;

public class AccountReponse {
    private int accountID;
    private String fullname; 
    private String email ; 
    private String password; 
    private String address; 
    private String gender; 
    private String avatar ; 
    private Date birthday ; 
    private Integer phone ; 
    private Boolean accountStatus; 
    private String roleName; 
    public AccountReponse(){

    }
    public AccountReponse(int accountID ,String fullname, String email, String password, String address, String gender, String avatar,
            Date birthday, Integer phone, Boolean accountStatus, String roleName) {
        this.accountID = accountID;
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
    public Integer getPhone() {
        return phone;
    }
    public void setPhone(Integer phone) {
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
    public int getAccountID() {
        return accountID;
    }
    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }
    

}
