package com.example.bloomgift.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.example.bloomgift.model.ChatMessage;


@Service
public class ChatMessageService {
     @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private static final String CHAT_HISTORY_PREFIX = "chatHistory_";

    // Lưu tin nhắn vào Redis, với khóa là StoreID
    public void saveChatMessage(Integer integer, ChatMessage message) {
        redisTemplate.opsForList().rightPush(CHAT_HISTORY_PREFIX + integer, message);
    }

    // Lấy lịch sử tin nhắn của Store từ Redis
    public List<Object> getChatMessages(Integer storeID) {
        return redisTemplate.opsForList().range(CHAT_HISTORY_PREFIX + storeID, 0, -1);
    }
}
