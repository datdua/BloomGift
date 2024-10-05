package com.example.bloomgift.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        this.hashOperations = redisTemplate.opsForHash(); 
    }

    public void addToCart(Account account, Product product, int quantity) {
        String key = "CART:" + account.getAccountID();
        float discount = product.getDiscount() != null ? product.getDiscount() : 0;
        float discountedPrice = product.getPrice() * (1 - discount / 100);
        float totalPrice = discountedPrice * quantity;
        Map<String, Object> productMap = Map.of(
                "productID", product.getProductID(),
                "storeName", product.getStoreName(),
                "productName", product.getProductName(),
                "price", discountedPrice,
                "quantity", quantity,
                "totalPrice", totalPrice);
        hashOperations.put(key, product.getProductID(), productMap);
    }

    public void addToCartWithSize(Account account, Product product, int quantity, Size size) {
        String key = "CART:" + account.getAccountID();
        Map<String, Object> productMap = Map.of(
                "storeName", product.getStoreName(),
                "productID", product.getProductID(),
                "productName", product.getProductName(),
                "price", size.getPrice(),
                "sizeText", size.getText(),
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
        
        for (Integer productID : selectedProductIds) {
            Map<String, Object> item = cartItems.get(productID);
            
            if (item != null) {
                Float price = (Float) item.get("price");
                int quantity = (int) item.get("quantity");
                double itemTotal = price * quantity;
                
                totalPriceForSelected += itemTotal;
                selectedItems.put(productID, item);  
            }
        }
    
        Map<String, Object> response = new HashMap<>();
        response.put("selectedItems", selectedItems);
        response.put("totalPrice", totalPriceForSelected);
    
        return response;
    }

    public List<CartItem> getCartItemsByAccountID(Integer accountID) {
        String key = "CART:" + accountID;
        Map<Integer, Map<String, Object>> cartData = hashOperations.entries(key);

        if (cartData.isEmpty()) {
            return new ArrayList<>();
        }
        List<CartItem> cartItems = cartData.values().stream()
                .map(map -> {
                    CartItem cartItem = new CartItem();
                    cartItem.setProductID((Integer) map.get("productID"));
                    cartItem.setQuantity((Integer) map.get("quantity"));
                    cartItem.setTotalPrice((Float) map.get("totalPrice"));
                    return cartItem;
                })
                .collect(Collectors.toList());

        return cartItems;
    }

    public void clearCart(Integer accountID) {
        String key = "CART:" + accountID;
        redisTemplate.delete(key);
    }

}
