package com.example.bloomgift.model;

import java.io.Serializable;
import java.util.List;

public class Cart implements Serializable {
    private Integer accountID;
    private List<CartItem> items;

    // Constructors
    public Cart() {
    }

    public Cart(Integer accountID, List<CartItem> items) {
        this.accountID = accountID;
        this.items = items;
    }

    // Getters and Setters
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

    // Method to calculate total price of the cart
    public Float calculateTotal() {
        return items.stream()
                .map(CartItem::getTotalPrice)
                .reduce(0f, Float::sum);
    }

    // Optional: Add toString() for easier debugging
    @Override
    public String toString() {
        return "Cart{" +
                "accountID=" + accountID +
                ", items=" + items +
                '}';
    }
}
