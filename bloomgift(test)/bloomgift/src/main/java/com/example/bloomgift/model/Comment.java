package com.example.bloomgift.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "userID", nullable = false)
    private Account userID;

    @ManyToOne
    @JoinColumn(name = "productID", nullable = false)
    private Product productID;

    @Column(name = "[context]", nullable = false)
    private String context;

    @Column(name = "commentDate", nullable = false)
    private Date commentDate ;

    @Column(name= "report")
    private boolean report;

    @Column(name = "rate", nullable = false)
    private Integer rate;

    @Column(name = "status", nullable = false)
    private boolean status;
    public Comment(){

    }
    public Comment(Integer id, Account userID, Product productID, String context, Date commentDate, boolean report,
            Integer rate, boolean status) {
        this.id = id;
        this.userID = userID;
        this.productID = productID;
        this.context = context;
        this.commentDate = commentDate;
        this.report = report;
        this.rate = rate;
        this.status = status;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Account getUserID() {
        return userID;
    }
    public void setUserID(Account userID) {
        this.userID = userID;
    }
    public Product getProductID() {
        return productID;
    }
    public void setProductID(Product productID) {
        this.productID = productID;
    }
    public String getContext() {
        return context;
    }
    public void setContext(String context) {
        this.context = context;
    }
    public Date getCommentDate() {
        return commentDate;
    }
    public void setCommentDate(Date commentDate) {
        this.commentDate = commentDate;
    }
    public boolean isReport() {
        return report;
    }
    public void setReport(boolean report) {
        this.report = report;
    }
    public Integer getRate() {
        return rate;
    }
    public void setRate(Integer rate) {
        this.rate = rate;
    }
    public boolean isStatus() {
        return status;
    }
    public void setStatus(boolean status) {
        this.status = status;
    }
   
    
     
}

