package com.example.bloomgift.model;

import java.util.List;

public class Cart {
    private Integer accountID ; 
    private List<CartItem> items;
    public Cart(Integer accountID, List<CartItem> items) {
        this.accountID = accountID;
        this.items = items;
    }
    public Integer getAccountID() {
        return accountID;
    }
    public void setAccountID(Integer accountID) {
        this.accountID = accountID;
    }
    public List<CartItem> getItems() {
        return items;
    }
    public void setItems(List<CartItem> items) {
        this.items = items;
    }
    public Float calculateTotal() {
        return items.stream()
                .map(item -> item.getTotalPrice())
                .reduce(0f, Float::sum);
    }
    
}
