package com.example.bloomgift.model;

import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="Account")
public class Account {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "email", nullable = false)
    private String email;
    
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "fullname", nullable = false)
    private String fullname;

    @Column(name = "birthday")
    private Date birthday;


    @ManyToOne
    @JoinColumn(name = "roleid", nullable = false)
    private Role roleid;

    @Column(name = "address")
    private String address;

    @Column(name = "isActive", nullable = false)
    private boolean isActive;

    @Column(name = "phone", nullable = false)
    private Integer phone;

    @Column(name = "otp")
    private String otp;

    @Column(name = "otp_generated_time")
    private LocalDateTime otp_generated_time;
    public Account() {
    }
     public Account(Integer id, String email, String password,String fullname,Date birthday,
                        String address,boolean isActive,Integer phone,Role roleid
                        ,String otp, LocalDateTime otp_generated_time ){
                            this.id = id ; 
                            this.email = email; 
                            setPassword(password);
                            this.birthday = birthday;
                            this.address = address;
                            this.isActive = isActive;
                            this.phone = phone;
                            this.fullname = fullname;
                            this.roleid = roleid;
                            this.otp = this.otp;
                            this.otp_generated_time = otp_generated_time;
                        }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        this.password = passwordEncoder.encode(password);
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public Integer getPhone() {
        return phone;
    }

    public void setPhone(Integer phone) {
        this.phone = phone;
    }
   
    public Role getRoleid() {
        return roleid;
    }

    public void setRoleid(Role roleid) {
        this.roleid = roleid;
    }


    public String getRoleName() {
        return roleid != null ? roleid.getName() : null;
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
}
