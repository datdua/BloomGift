package com.example.bloomgift.model;

import java.time.LocalDateTime;

import com.google.protobuf.Extension.MessageType;
import lombok.Data;

public class ChatMessage  {
    private Account sender;  // Account gửi tin nhắn
    private Store store;     // Store đang được nhắn tin
    private String content;  // Nội dung tin nhắn
    private LocalDateTime timestamp;  // Thời gian gửi tin nhắn

    public enum MessageType {
        CHAT, JOIN, LEAVE
    }
    private MessageType type;
    public ChatMessage(){
        
    } // Loại tin nhắn (CHAT, JOIN, LEAVE)
    public Account getSender() {
        return sender;
    }
    public void setSender(Account sender) {
        this.sender = sender;
    }
    public Store getStore() {
        return store;
    }
    public void setStore(Store store) {
        this.store = store;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
    public MessageType getType() {
        return type;
    }
    public void setType(MessageType type) {
        this.type = type;
    }

    // Getters và Setters
}