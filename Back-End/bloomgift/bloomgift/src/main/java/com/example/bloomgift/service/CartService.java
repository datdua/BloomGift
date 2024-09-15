package com.example.bloomgift.service;

import java.util.HashMap;
import java.util.Map;

import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.example.bloomgift.model.Account;
import com.example.bloomgift.model.CartItem;
import com.example.bloomgift.model.Product;
import com.example.bloomgift.model.Size;
import com.example.bloomgift.repository.StoreRepository;

import jakarta.annotation.PostConstruct;

@Service
public class CartService {
    private static final String CART_KEY = "CART";
    @Autowired
    private StoreRepository storeRepository;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private HashOperations<String, Integer, Map<String, Object>> hashOperations;

    @PostConstruct
    public void init() {
        hashOperations = redisTemplate.opsForHash();
    }

    @Autowired
    public CartService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.hashOperations = redisTemplate.opsForHash(); // Khởi tạo HashOperations từ RedisTemplate
    }

    public void addToCart(Account account, Product product, int quantity) {
        String key = "CART:" + account.getAccountID();
        Map<String, Object> productMap = Map.of(
                "productID", product.getProductID(),
                "productName", product.getProductName(),
                "price", product.getPrice(),
                "quantity", quantity,
                "totlePrice", product.getPrice()*quantity);
        hashOperations.put(key, product.getProductID(), productMap);
    }

    public void addToCartWithSize(Account account,Product product,int quantity,Size size){
        String key = "CART:" + account.getAccountID();
        Map<String,Object> productMap = Map.of(
                "storeName", product.getStoreName(),
                "productID", product.getProductID(),
                "productName", product.getProductName(),
                "price", size.getPrice(),
                "productSize", size.getSizeFloat(),
                "quantity", quantity,
                "totlePrice", size.getPrice()*quantity

        );
        hashOperations.put(key, product.getProductID(), productMap);
    }

    // public Map<Integer, Map<String, Object>> getCart(Account account) {
    //     String key = CART_KEY + ":" + account.getAccountID();
    //     return hashOperations.entries(key);
    // }
    public Map<String, Object> getCart(Account account) {
    String key = "CART:" + account.getAccountID();
    Map<Integer, Map<String, Object>> cartItems = hashOperations.entries(key);
    double totalPrice = 0.0;
    for (Map<String, Object> item : cartItems.values()) {
        Float price = (Float) item.get("price");
        int quantity = (int) item.get("quantity");
        totalPrice += price * quantity;
    }
    Map<String, Object> cartResponse = new HashMap<>();
    cartResponse.put("cartItems", cartItems);
    cartResponse.put("totalPrice", totalPrice);

    return cartResponse;
}

    public void DeteleCart(Account account, Product product) {
        String key = CART_KEY + ":" + account.getAccountID();
        hashOperations.delete(key, product.getProductID());
    }

    public void updateCart(Account account, Product product, int newQuantity) {
        String key = CART_KEY + ":" + account.getAccountID();
        
        // Retrieve the product map from the cart
        Map<String, Object> productMap = (Map<String, Object>) hashOperations.get(key, product.getProductID());
        
        if (productMap != null) {
            // Convert the immutable map to a mutable map
            Map<String, Object> mutableProductMap = new HashMap<>(productMap);
            
            // Update the quantity
            mutableProductMap.put("quantity", newQuantity);
            
            // Save the updated map back to the cart
            hashOperations.put(key, product.getProductID(), mutableProductMap);
        } else {
            System.out.println("Product not found in the cart");
            throw new IllegalArgumentException("Product not found in the cart.");
        }
    }
}
