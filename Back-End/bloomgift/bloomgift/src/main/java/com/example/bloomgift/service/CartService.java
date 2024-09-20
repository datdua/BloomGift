package com.example.bloomgift.service;

import java.util.HashMap;
import java.util.List;
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
                "storeName", product.getStoreName(),
                "productName", product.getProductName(),
                "price", product.getPrice(),
                "quantity", quantity,
                "totlePrice", product.getPrice() * quantity);
        hashOperations.put(key, product.getProductID(), productMap);
    }

    public void addToCartWithSize(Account account, Product product, int quantity, Size size) {
        String key = "CART:" + account.getAccountID();
        Map<String, Object> productMap = Map.of(
                "storeName", product.getStoreName(),
                "productID", product.getProductID(),
                "productName", product.getProductName(),
                "price", size.getPrice(),
                "productSize", size.getSizeFloat(),
                "quantity", quantity,
                "totlePrice", size.getPrice() * quantity

        );
        hashOperations.put(key, product.getProductID(), productMap);
    }

    // public Map<Integer, Map<String, Object>> getCart(Account account) {
    // String key = CART_KEY + ":" + account.getAccountID();
    // return hashOperations.entries(key);
    // }
    public Map<String, Object> getCart(Account account) {
        String key = "CART:" + account.getAccountID();
        Map<Integer, Map<String, Object>> cartItems = hashOperations.entries(key);
        double totalPriceCart = 0.0;
        for (Map<String, Object> item : cartItems.values()) {
            Float price = (Float) item.get("price");
            int quantity = (int) item.get("quantity");
            totalPriceCart += price * quantity;
        }
        Map<String, Object> cartResponse = new HashMap<>();
        cartResponse.put("cartItems", cartItems);
        cartResponse.put("totalPriceCart", totalPriceCart);

        return cartResponse;
    }

    public void DeteleCart(Account account, Product product) {
        String key = CART_KEY + ":" + account.getAccountID();
        hashOperations.delete(key, product.getProductID());
    }

    public void updateCart(Account account, Product product, int newQuantity) {
        String key = CART_KEY + ":" + account.getAccountID();

        Map<String, Object> productMap = (Map<String, Object>) hashOperations.get(key, product.getProductID());

        if (productMap != null) {
            Map<String, Object> mutableProductMap = new HashMap<>(productMap);

            mutableProductMap.put("quantity", newQuantity);
            Float price = (Float) mutableProductMap.get("price");
            double newTotalPrice = price * newQuantity;
            mutableProductMap.put("totlePrice", newTotalPrice);
            hashOperations.put(key, product.getProductID(), mutableProductMap);
        } else {
            System.out.println("Product not found in the cart");
            throw new IllegalArgumentException("Product not found in the cart.");
        }
    }

    public Map<String, Object> calculateTotalForSelectedProducts(Account account, List<Integer> selectedProductIds) {
        String key = "CART:" + account.getAccountID();
        Map<Integer, Map<String, Object>> cartItems = hashOperations.entries(key);
        
        double totalPriceForSelected = 0.0;
        Map<Integer, Map<String, Object>> selectedItems = new HashMap<>();
        
        for (Integer productId : selectedProductIds) {
            Map<String, Object> item = cartItems.get(productId);
            
            if (item != null) {
                Float price = (Float) item.get("price");
                int quantity = (int) item.get("quantity");
                double itemTotal = price * quantity;
                
                totalPriceForSelected += itemTotal;
                selectedItems.put(productId, item);  
            }
        }
    
        Map<String, Object> response = new HashMap<>();
        response.put("selectedItems", selectedItems);
        response.put("totalPrice", totalPriceForSelected);
    
        return response;
    }
}
