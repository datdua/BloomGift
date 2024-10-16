package com.example.bloomgift.reponse;

public class PaymentReponse {
    private Integer paymentID;
    private Integer accountID;
    private String method;
    private String bankName;
    private Float totalPrice;
    private String bankNumber;
    private String momoNumber;
    private String paymentCode;
    private String bankAccountName;
    
    private Integer orderID;
    private Boolean paymentStatus;
    public PaymentReponse(){
        
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
    public String getPaymentCode() {
        return paymentCode;
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
    public Integer getOrderID() {
        return orderID;
    }
    public void setOrderID(Integer orderID) {
        this.orderID = orderID;
    }
    public Boolean getPaymentStatus() {
        return paymentStatus;
    }
    public void setPaymentStatus(Boolean paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
    public PaymentReponse(Integer paymentID, Integer accountID, String method, String bankName, Float totalPrice,
            String bankNumber, String momoNumber, String paymentCode, String bankAccountName, Integer orderID,
            Boolean paymentStatus) {
        this.paymentID = paymentID;
        this.accountID = accountID;
        this.method = method;
        this.bankName = bankName;
        this.totalPrice = totalPrice;
        this.bankNumber = bankNumber;
        this.momoNumber = momoNumber;
        this.paymentCode = paymentCode;
        this.bankAccountName = bankAccountName;
        this.orderID = orderID;
        this.paymentStatus = paymentStatus;
    }
}
