package com.example.bloomgift.model;

import java.util.HashMap;
import java.util.Map;

public class Chat {
    private Integer storeId;
    private Integer accountId;
    private Map<String, Message> messages;

    // Constructors, getters, and setters
    public Chat() {}
    
    public Chat(int storeId, int accountId) {
        this.storeId = storeId;
        this.accountId = accountId;
        this.messages = new HashMap<>();
    }

    public Chat(Integer storeId, Integer accountId, Map<String, Message> messages) {
        this.storeId = storeId;
        this.accountId = accountId;
        this.messages = messages;
    }
    
    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Map<String, Message> getMessages() {
        return messages;
    }

    public void setMessages(Map<String, Message> messages) {
        this.messages = messages;
    }

    
   
}