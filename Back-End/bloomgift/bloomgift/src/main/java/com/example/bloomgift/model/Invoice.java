package com.example.bloomgift.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "Invoice")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "invoiceID")
    private Integer invoiceID;

    @Column(name = "transactionNo")
    private String transactionNo;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "invoiceDate")
    private LocalDateTime invoiceDate;

    @Column(name = "invoiceTotalPrice")
    private Float invoiceTotalPrice;

    @Column(name = "invoiceStatus")
    private String invoiceStatus;

    @Column(name = "invoiceImage")
    private String invoiceImage;

    @Column(name = "commissionPrice")
    private Float commissionPrice;

    @ManyToOne
    @JoinColumn(name = "storeID", nullable = false)
    private Store store;

    public Invoice() {
    }

    public Invoice(Integer invoiceID, String transactionNo, LocalDateTime invoiceDate, Float invoiceTotalPrice,
            String invoiceStatus, String invoiceImage, Float commissionPrice, Store store) {
        this.invoiceID = invoiceID;
        this.transactionNo = transactionNo;
        this.invoiceDate = invoiceDate;
        this.invoiceTotalPrice = invoiceTotalPrice;
        this.invoiceStatus = invoiceStatus;
        this.invoiceImage = invoiceImage;
        this.commissionPrice = commissionPrice;
        this.store = store;
    }

    public Integer getInvoiceID() {
        return invoiceID;
    }

    public void setInvoiceID(Integer invoiceID) {
        this.invoiceID = invoiceID;
    }

    public String getTransactionNo() {
        return transactionNo;
    }

    public void setTransactionNo(String transactionNo) {
        this.transactionNo = transactionNo;
    }

    public LocalDateTime getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(LocalDateTime invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public Float getInvoiceTotalPrice() {
        return invoiceTotalPrice;
    }

    public void setInvoiceTotalPrice(Float invoiceTotalPrice) {
        this.invoiceTotalPrice = invoiceTotalPrice;
    }

    public String getInvoiceStatus() {
        return invoiceStatus;
    }

    public void setInvoiceStatus(String invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
    }

    public String getInvoiceImage() {
        return invoiceImage;
    }

    public void setInvoiceImage(String invoiceImage) {
        this.invoiceImage = invoiceImage;
    }

    public Float getCommissionPrice() {
        return commissionPrice;
    }

    public void setCommissionPrice(Float commissionPrice) {
        this.commissionPrice = commissionPrice;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

}
