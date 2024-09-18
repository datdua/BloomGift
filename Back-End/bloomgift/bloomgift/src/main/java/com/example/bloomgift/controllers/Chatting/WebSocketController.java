package com.example.bloomgift.controllers.Chatting;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bloomgift.model.ChatMessage;
import com.example.bloomgift.service.ChatMessageService;


@RestController
@RequestMapping("/api/auth/WebSocketController")
public class WebSocketController {

     @Autowired
    private ChatMessageService chatMessageService;

    // Khi Account gửi tin nhắn tới một Store
    @MessageMapping("/chat.sendMessage/{storeId}")
    @SendTo("/topic/store/{storeId}")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage, @PathVariable Long storeId) {
        chatMessage.setTimestamp(LocalDateTime.now());
        
        // Lưu tin nhắn vào Redis hoặc Database
        chatMessageService.saveChatMessage(chatMessage.getStore().getStoreID(), chatMessage);

        return chatMessage;
    }

    // Khi một người tham gia chat (Account tham gia chat với Store)
    @MessageMapping("/chat.addUser/{storeId}")
    @SendTo("/topic/store/{storeId}")
    public ChatMessage addUser(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        chatMessage.setTimestamp(LocalDateTime.now());
        chatMessage.setType(ChatMessage.MessageType.JOIN);

        // Lưu username vào session
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender().getFullname());
        
        return chatMessage;
    }

}
