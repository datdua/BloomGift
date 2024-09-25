package com.example.bloomgift.model;

import java.util.ArrayList;
import java.util.List;

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
import jakarta.persistence.Table;

@Entity
@Table(name = "Size")
public class Size {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sizeID")
    private Integer sizeID;

    @Column(name = "price")
    private Float price;

    @Column(name = "text")
    private String text;

    @Column(name = "sizeQuantity")
    private Integer sizeQuantity;

    @ManyToOne
    @JoinColumn(name = "productID")
    private Product productID;

    @OneToMany(mappedBy = "sizeID", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderDetail> orderDetails = new ArrayList<>();

    public Size() {

    }

    public Size(Integer sizeID, Float price, String text, Integer sizeQuantity, Product productID,
            List<OrderDetail> orderDetails) {
        this.sizeID = sizeID;
        this.price = price;
        this.text = text;
        this.sizeQuantity = sizeQuantity;
        this.productID = productID;
        this.orderDetails = orderDetails;
    }

    public Integer getSizeID() {
        return sizeID;
    }

    public void setSizeID(Integer sizeID) {
        this.sizeID = sizeID;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getSizeQuantity() {
        return sizeQuantity;
    }

    public void setSizeQuantity(Integer sizeQuantity) {
        this.sizeQuantity = sizeQuantity;
    }

    public Product getProductID() {
        return productID;
    }

    public void setProductID(Product productID) {
        this.productID = productID;
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

}
