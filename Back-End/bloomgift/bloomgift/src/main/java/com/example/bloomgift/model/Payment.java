package com.example.bloomgift.model;

import java.security.SecureRandom;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "paymentID")
    private Integer paymentID;

    @Column(name = "accountID")
    private Integer accountID;

    @Column(name = "method")
    private String method;

    @Column(name = "bankName")
    private String bankName;

    @Column(name = "paymentStatus")
    private Boolean paymentStatus;

    @Column(name = "totalPrice")
    private Float totalPrice;

    @Column(name = "bankNumber")
    private String bankNumber;

    @Column(name = "momoNumber")
    private String momoNumber;

    @Column(name = "paymentCode")
    private String paymentCode;

    @Column(name = "bankAccountName")
    private String bankAccountName;

    @Column(name = "format")
    private String format;

    @Column(name = "template")
    private String template;

    @Column(name = "acqId")
    private Integer acqId;

    @ManyToOne
    @JoinColumn(name = "storeID")
    private Store storeID;

    @ManyToOne
    @JoinColumn(name = "orderID")
    private Order orderID;

    public Payment(){
        
    }

    public Payment(Integer paymentID, Integer accountID, String method, String bankName, Boolean paymentStatus,
            Float totalPrice, String bankNumber, String momoNumber, String paymentCode, String bankAccountName,
            String format, String template, Integer acqId, Store storeID, Order orderID) {
        this.paymentID = paymentID;
        this.accountID = accountID;
        this.method = method;
        this.bankName = bankName;
        this.paymentStatus = paymentStatus;
        this.totalPrice = totalPrice;
        this.bankNumber = bankNumber;
        this.momoNumber = momoNumber;
        this.paymentCode = paymentCode;
        this.bankAccountName = bankAccountName;
        this.format = format;
        this.template = template;
        this.acqId = acqId;
        this.storeID = storeID;
        this.orderID = orderID;
    }

    public Integer getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(Integer paymentID) {
        this.paymentID = paymentID;
    }

    public Integer getAccountID() {
        return accountID;
    }

    public void setAccountID(Integer accountID) {
        this.accountID = accountID;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public Boolean getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(Boolean paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getBankNumber() {
        return bankNumber;
    }

    public void setBankNumber(String bankNumber) {
        this.bankNumber = bankNumber;
    }

    public String getMomoNumber() {
        return momoNumber;
    }

    public void setMomoNumber(String momoNumber) {
        this.momoNumber = momoNumber;
    }

    public void setPaymentCode() {
        String paymentCode = generateRandomCode(10); 
        this.paymentCode = paymentCode;
    }
      private String generateRandomCode(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder code = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            code.append(characters.charAt(index));
        }

        return code.toString();
    }


    public void setPaymentCode(String paymentCode) {
        this.paymentCode = paymentCode;
    }

    public String getBankAccountName() {
        return bankAccountName;
    }

    public void setBankAccountName(String bankAccountName) {
        this.bankAccountName = bankAccountName;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public Integer getAcqId() {
        return acqId;
    }

    public void setAcqId(Integer acqId) {
        this.acqId = acqId;
    }

    public Store getStoreID() {
        return storeID;
    }

    public void setStoreID(Store storeID) {
        this.storeID = storeID;
    }

    public Order getOrderID() {
        return orderID;
    }

    public void setOrderID(Order orderID) {
        this.orderID = orderID;
    }

    public String getPaymentCode() {
        return paymentCode;
    }



}
