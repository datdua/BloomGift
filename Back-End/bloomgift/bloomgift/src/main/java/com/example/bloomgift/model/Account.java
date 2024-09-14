package com.example.bloomgift.model;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "accountID")
    private Integer accountID;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private Integer phone;

    @Column(name = "address")
    private String address;

    @Column(name = "fullname")
    private String fullname;

    @Column(name = "gender")
    private String gender;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "point")
    private Integer point;

    @Column(name = "birthday")
    private Date birthday;

    @Column(name = "accountStatus")
    private Boolean accountStatus;

    @Column(name = "password")
    private String password;

    @Column(name = "otp")
    private String otp;

    @Column(name = "otp_generated_time")
    private LocalDateTime otp_generated_time;

    @ManyToOne
    @JoinColumn(name = "roleID")
    private Role roleID;

    public Account() {

    }

    public Account(Integer accountID, String email, Integer phone, String address, String fullname, String gender,
            String avatar, Integer point, Date birthday, Boolean accountStatus, String password, String otp,
            LocalDateTime otp_generated_time, Role roleID) {
        this.accountID = accountID;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.fullname = fullname;
        this.gender = gender;
        this.avatar = avatar;
        this.point = point;
        this.birthday = birthday;
        this.accountStatus = accountStatus;
        this.password = password;
        this.otp = otp;
        this.otp_generated_time = otp_generated_time;
        this.roleID = roleID;
    }

    public Integer getAccountID() {
        return accountID;
    }

    public void setAccountID(Integer accountID) {
        this.accountID = accountID;
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

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Boolean getAccountStatus(boolean par) {
        return accountStatus;
    }

    public void setAccountStatus(Boolean accountStatus) {
        this.accountStatus = accountStatus;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public LocalDateTime getOtp_generated_time() {
        return otp_generated_time;
    }

    public void setOtp_generated_time(LocalDateTime otp_generated_time) {
        this.otp_generated_time = otp_generated_time;
    }

    public Role getRoleID() {
        return roleID;
    }

    public void setRoleID(Role roleID) {
        this.roleID = roleID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        this.password = passwordEncoder.encode(password);
    }

    public String getRoleName() {
        return roleID != null ? roleID.getRoleName() : null;
    }

}