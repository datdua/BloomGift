package com.example.bloomgift.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.example.bloomgift.model.Account;
import com.example.bloomgift.model.CartItem;
import com.example.bloomgift.model.Product;

import jakarta.annotation.PostConstruct;

@Service
public class CartService {
    private static final String CART_KEY = "CART";
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    
    private HashOperations<String, Integer,  Map<String, Object>> hashOperations;
    
    @PostConstruct
    public void init() {
        hashOperations = redisTemplate.opsForHash();
    }
    @Autowired
    public CartService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.hashOperations = redisTemplate.opsForHash(); // Khởi tạo HashOperations từ RedisTemplate
    }

    
    public void addToCart(Integer accountId, Product product, int quantity) {
        String key = "CART:" + accountId;
        Map<String, Object> productMap = Map.of(
                "productID", product.getProductID(),
                "productName", product.getProductName(),
                "price", product.getPrice(),
                "quantity", quantity
        );
        hashOperations.put(key, product.getProductID(), productMap);
    }
    public Map<Integer, Map<String, Object>> getCart(Integer accountId) {
        String key = CART_KEY + ":" + accountId;
        return hashOperations.entries(key);
    }
}
