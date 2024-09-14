package com.example.bloomgift.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.Date;

import jakarta.persistence.*;
@Entity
@Table(name = "Comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "commentID")
    private Integer commentID;

    @ManyToOne
    @JoinColumn(name = "accountID")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "productID")
    private Product product;

    @Column(name = "content")
    private String content;

    @Column(name = "date")
    private Date date;

    // Getters and setters...

    public Comment() {
    }

    public Comment(Integer commentID, Account account, Product product, String content, Date date) {
        this.commentID = commentID;
        this.account = account;
        this.product = product;
        this.content = content;
        this.date = date;
    }

    public Comment(Account account, Product product, String content) {
        this.account = account;
        this.product = product;
        this.content = content;
    }

    public Integer getCommentID() {
        return commentID;
    }

    public void setCommentID(Integer commentID) {
        this.commentID = commentID;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate(Date date2) {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    
}