package com.example.bloomgift.request;

import java.util.Date;

public class CommentCreateRequest {
    private Integer userId;
    private Integer productId;
    private String context;
    private Date commentDate;
    private Integer rate; // Rating as an integer
    private boolean status;
    public CommentCreateRequest(){

    }
    public CommentCreateRequest(Integer userId, Integer productId, String context, Date commentDate, Integer rate,
            boolean status) {
        this.userId = userId;
        this.productId = productId;
        this.context = context;
        this.commentDate = commentDate;
        this.rate = rate;
        this.status = status;
    }
    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public Integer getProductId() {
        return productId;
    }
    public void setProductId(Integer productId) {
        this.productId = productId;
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
