package com.example.bloomgift.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "OrderDetail")
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderDetailID")
    private Integer orderDetailID;

    @Column(name = "productTotalPrice")
    private Float productTotalPrice;

    @Column(name = "quantity")
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "accountID")
    private Account accountID;

    @ManyToOne
    @JoinColumn(name = "productID")
    private Product productID;

    @ManyToOne
    @JoinColumn(name = "orderID")
    private Order orderID;

    @ManyToOne
    @JoinColumn(name = "storeID")
    private Store storeID;

    @ManyToOne
    @JoinColumn(name = "sizeID")
    private Size sizeID;

    public OrderDetail() {
    }

    public OrderDetail(Integer orderDetailID, Float productTotalPrice, Integer quantity, Account accountID,
            Product productID, Order orderID, Store storeID, Size sizeID) {
        this.orderDetailID = orderDetailID;
        this.productTotalPrice = productTotalPrice;
        this.quantity = quantity;
        this.accountID = accountID;
        this.productID = productID;
        this.orderID = orderID;
        this.storeID = storeID;
        this.sizeID = sizeID;
    }

    public Integer getOrderDetailID() {
        return orderDetailID;
    }

    public void setOrderDetailID(Integer orderDetailID) {
        this.orderDetailID = orderDetailID;
    }

    public Float getProductTotalPrice() {
        return productTotalPrice;
    }

    public void setProductTotalPrice(Float productTotalPrice) {
        this.productTotalPrice = productTotalPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Account getAccountID() {
        return accountID;
    }

    public void setAccountID(Account accountID) {
        this.accountID = accountID;
    }

    public Product getProductID() {
        return productID;
    }

    public void setProductID(Product productID) {
        this.productID = productID;
    }

    public Order getOrderID() {
        return orderID;
    }

    public void setOrderID(Order orderID) {
        this.orderID = orderID;
    }

    public Store getStoreID() {
        return storeID;
    }

    public void setStoreID(Store storeID) {
        this.storeID = storeID;
    }

    public Size getSizeID() {
        return sizeID;
    }

    public void setSizeID(Size sizeID) {
        this.sizeID = sizeID;
    }

}
