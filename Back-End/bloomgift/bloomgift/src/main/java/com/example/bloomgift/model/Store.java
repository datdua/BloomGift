package com.example.bloomgift.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Store")
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "storeID")
    private Integer storeID;

    @OneToMany(mappedBy = "storeID")
    @JsonIgnore
    private Set<Product> products;

    @Column(name = "storeName")
    private String storeName;

    @Column(name = "type")
    private String type;

    @Column(name = "storePhone")
    private String storePhone;

    @Column(name = "storeAddress")
    private String storeAddress;

    @Column(name = "email")
    private String email;

    @Column(name = "bankAccountName")
    private String bankAccountName;

    @Column(name = "bankNumber")
    private String bankNumber;

    @Column(name = "bankAddress")
    private String bankAddress;

    @Column(name = "taxNumber")
    private String taxNumber;

    @Column(name = "storeStatus")
    private String storeStatus;

    @Column(name = "storeAvatar")
    private String storeAvatar;

    @Column(name = "identityCard")
    private String identityCard;

    @Column(name = "identityName")
    private String identityName;

    @Column(name = "password")
    private String password;

    @Column(name = "otp")
    private String otp;

    @Column(name = "acqId")
    private Integer acqId;

    @Column(name = "storeDescription")
    private String storeDescription;

    @Column(name = "otp_generated_time")
    private LocalDateTime otp_generated_time;

    @ManyToOne
    @JoinColumn(name = "roleID", nullable = false)
    private Role role;

    @OneToMany(mappedBy = "storeID", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderDetail> orderDetails = new ArrayList<>();

    @OneToMany(mappedBy = "storeID",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Delivery> deliveries = new ArrayList<>();
    
    @OneToOne(mappedBy = "store", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private DistanceFee distanceFee;  // Thêm trường để liên kết One-to-One

    public Store() {
    }

    @OneToMany(mappedBy = "storeID", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Payment> payments = new ArrayList<>();

    public Store(Integer storeID, Set<Product> products, String storeName, String type, String storePhone,
            String storeAddress, String email, String bankAccountName, String bankNumber, String bankAddress,
            String taxNumber, String storeStatus, String storeAvatar, String identityCard, String identityName,
            String password, String otp, LocalDateTime otp_generated_time, Role role) {
        this.storeID = storeID;
        this.products = products;
        this.storeName = storeName;
        this.type = type;
        this.storePhone = storePhone;
        this.storeAddress = storeAddress;
        this.email = email;
        this.bankAccountName = bankAccountName;
        this.bankNumber = bankNumber;
        this.bankAddress = bankAddress;
        this.taxNumber = taxNumber;
        this.storeStatus = storeStatus;
        this.storeAvatar = storeAvatar;
        this.identityCard = identityCard;
        this.identityName = identityName;
        this.password = password;
        this.otp = otp;
        this.otp_generated_time = otp_generated_time;
        this.role = role;
    }

    public Integer getStoreID() {
        return storeID;
    }

    public void setStoreID(Integer storeID) {
        this.storeID = storeID;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStorePhone() {
        return storePhone;
    }

    public void setStorePhone(String storePhone) {
        this.storePhone = storePhone;
    }

    public String getStoreAddress() {
        return storeAddress;
    }

    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStoreDescription() {
        return storeDescription;
    }

    public void setStoreDescription(String storeDescription) {
        this.storeDescription = storeDescription;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    public String getBankAccountName() {
        return bankAccountName;
    }

    public void setBankAccountName(String bankAccountName) {
        this.bankAccountName = bankAccountName;
    }

    public String getBankNumber() {
        return bankNumber;
    }

    public void setBankNumber(String bankNumber) {
        this.bankNumber = bankNumber;
    }

    public String getBankAddress() {
        return bankAddress;
    }

    public void setBankAddress(String bankAddress) {
        this.bankAddress = bankAddress;
    }

    public String getTaxNumber() {
        return taxNumber;
    }

    public void setTaxNumber(String taxNumber) {
        this.taxNumber = taxNumber;
    }

    public String getStoreStatus() {
        return storeStatus;
    }

    public void setStoreStatus(String storeStatus) {
        this.storeStatus = storeStatus;
    }

    public String getStoreAvatar() {
        return storeAvatar;
    }

    public void setStoreAvatar(String storeAvatar) {
        this.storeAvatar = storeAvatar;
    }

    public String getIdentityCard() {
        return identityCard;
    }

    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard;
    }

    public String getIdentityName() {
        return identityName;
    }

    public void setIdentityName(String identityName) {
        this.identityName = identityName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        this.password = passwordEncoder.encode(password);
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
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

    public Integer getAcqId() {
        return acqId;
    }

    public void setAcqId(Integer acqId) {
        this.acqId = acqId;
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

}