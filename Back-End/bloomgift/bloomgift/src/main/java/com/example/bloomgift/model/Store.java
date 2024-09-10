package com.example.bloomgift.model;

import java.util.Set;

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
@Table(name = "Store")
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "storeID")
    private Integer storeID;

    @OneToMany(mappedBy = "storeID")
    @JsonIgnore // Ignore this field during serialization
    private Set<Product> products;

    @Column(name = "storeName")
    private String storeName;

    @Column(name = "type")
    private String type;

    @Column(name = "storePhone")
    private String storePhone;

    @Column(name = "storeAddress")
    private String storeAddress;

    @Column(name = "storeEmail")
    private String storeEmail;

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

    @ManyToOne
    @JoinColumn(name = "accountID", nullable = true)
    private Account account;

    @ManyToOne
    @JoinColumn(name = "categoryID", nullable = true)
    private Category category;

    public Store() {
    }

    public Store(Integer storeID, Set<Product> products, String storeName, String type, String storePhone,
            String storeAddress, String storeEmail, String bankAccountName, String bankNumber, String bankAddress,
            String taxNumber, String storeStatus, String storeAvatar, String identityCard, String identityName,
            Account account, Category category) {
        this.storeID = storeID;
        this.products = products;
        this.storeName = storeName;
        this.type = type;
        this.storePhone = storePhone;
        this.storeAddress = storeAddress;
        this.storeEmail = storeEmail;
        this.bankAccountName = bankAccountName;
        this.bankNumber = bankNumber;
        this.bankAddress = bankAddress;
        this.taxNumber = taxNumber;
        this.storeStatus = storeStatus;
        this.storeAvatar = storeAvatar;
        this.identityCard = identityCard;
        this.identityName = identityName;
        this.account = account;
        this.category = category;
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

    public String getStoreEmail() {
        return storeEmail;
    }

    public void setStoreEmail(String storeEmail) {
        this.storeEmail = storeEmail;
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

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getIdentityName() {
        return identityName;
    }

    public void setIdentityName(String identityName) {
        this.identityName = identityName;
    }

}