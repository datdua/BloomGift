package com.example.bloomgift.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import static org.springframework.http.ResponseEntity.ok;
import org.springframework.stereotype.Service;

import com.example.bloomgift.model.Account;
import com.example.bloomgift.model.CartItem;
import com.example.bloomgift.model.Product;
import com.example.bloomgift.model.ProductImage;
import com.example.bloomgift.model.Size;
import com.example.bloomgift.repository.ProductRepository;
import com.example.bloomgift.repository.SizeRepository;
import com.example.bloomgift.repository.StoreRepository;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;

@Service
public class CartService {
    private static final String CART_KEY = "CART";
    @Autowired
    private StoreRepository storeRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private SizeRepository sizeRepository;
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

    @Transactional
    public ResponseEntity<?> addToCart(Account account, Product product, int quantity) {
        String key = "CART:" + account.getAccountID();
        Map<String, Object> productMap = (Map<String, Object>) hashOperations.get(key, product.getProductID());

        if (productMap != null) {
            // Update existing cart item
            int currentQuantity = (int) productMap.get("quantity");
            int newQuantity = currentQuantity + quantity;
            return updateCart(account, product, newQuantity);
        } else {
            // Add new cart item
            float discount = product.getDiscount() != null ? product.getDiscount() : 0;
            float discountedPrice = product.getPrice() * (1 - discount / 100);
            float totalPrice = discountedPrice * quantity;

            // Extract all product image URLs with unique keys
            Map<String, String> productImages = new HashMap<>();
            int imageIndex = 1;
            for (ProductImage image : product.getProductImages()) {
                productImages.put("image" + imageIndex, image.getProductImage());
                imageIndex++;
            }

            productMap = new HashMap<>();
            productMap.put("productID", product.getProductID());
            productMap.put("storeName", product.getStoreName());
            productMap.put("productName", product.getProductName());
            productMap.put("price", discountedPrice);
            productMap.put("quantity", quantity);
            productMap.put("totalPrice", totalPrice);
            productMap.putAll(productImages);

            hashOperations.put(key, product.getProductID(), productMap);

            // Reduce product quantity
            if (product.getQuantity() < quantity) {
                return ResponseEntity.badRequest().body("Số lượng sản phẩm không đủ");
            } else {
                product.setQuantity(product.getQuantity() - quantity);
                productRepository.save(product);
                return ResponseEntity.ok().body("Sản phẩm đã được thêm vào giỏ hàng");
            }
        }
    }

    @Transactional
    public ResponseEntity<?> addToCartWithSize(Account account, Product product, int quantity, Size size) {
        String key = "CART:" + account.getAccountID();
        Map<String, Object> productMap = (Map<String, Object>) hashOperations.get(key, product.getProductID());

        if (productMap != null) {
            // Update existing cart item
            int currentQuantity = (int) productMap.get("quantity");
            int newQuantity = currentQuantity + quantity;
            return updateCart(account, product, newQuantity);
        } else {
            // Add new cart item
            float discount = product.getDiscount() != null ? product.getDiscount() : 0;
            float discountedPrice = product.getPrice() * (1 - discount / 100);
            float totalPrice = discountedPrice * quantity;

            // Extract all product image URLs with unique keys
            Map<String, String> productImages = new HashMap<>();
            int imageIndex = 1;
            for (ProductImage image : product.getProductImages()) {
                productImages.put("image" + imageIndex, image.getProductImage());
                imageIndex++;
            }

            productMap = new HashMap<>();
            productMap.put("storeName", product.getStoreName());
            productMap.put("productID", product.getProductID());
            productMap.put("productName", product.getProductName());
            productMap.put("price", size.getPrice());
            productMap.put("sizeText", size.getText());
            productMap.put("quantity", quantity);
            productMap.put("totalPrice", size.getPrice() * quantity);
            productMap.putAll(productImages);

            hashOperations.put(key, product.getProductID(), productMap);

            // Reduce product and size quantity
            if (size.getSizeQuantity() < quantity || product.getQuantity() < quantity) {
                return ResponseEntity.badRequest().body("Số lượng sản phẩm không đủ");
            } else {
                product.setQuantity(product.getQuantity() - quantity);
                size.setSizeQuantity(size.getSizeQuantity() - quantity);
                productRepository.save(product);
                sizeRepository.save(size);
                return ResponseEntity.ok().body("Sản phẩm đã được thêm vào giỏ hàng");
            }
        }
    }

    @Transactional
    public ResponseEntity<?> updateCart(Account account, Product product, int newQuantity) {
        String key = CART_KEY + ":" + account.getAccountID();

        Map<String, Object> productMap = (Map<String, Object>) hashOperations.get(key, product.getProductID());

        if (productMap != null) {
            int currentQuantity = (int) productMap.get("quantity");
            int quantityDifference = newQuantity - currentQuantity;

            // Check if the new quantity is valid
            if (product.getQuantity() < quantityDifference) {
                return ResponseEntity.badRequest().body("Không đủ số lượng sản phẩm.");
            }

            // If size information is present, update size quantity as well
            if (productMap.containsKey("sizeText")) {
                String sizeText = (String) productMap.get("sizeText");
                Size size = product.getSizes().stream()
                        .filter(s -> s.getText().equals(sizeText))
                        .findFirst()
                        .orElseThrow(() -> new IllegalArgumentException("Kích cỡ không tồn tại."));

                if (size.getSizeQuantity() < quantityDifference) {
                    return ResponseEntity.badRequest().body("Không đủ số lượng sản phẩm.");
                }

                size.setSizeQuantity(size.getSizeQuantity() - quantityDifference);
                sizeRepository.save(size);
            }

            // Update product quantity
            product.setQuantity(product.getQuantity() - quantityDifference);
            productRepository.save(product);

            // Update cart item
            Map<String, Object> mutableProductMap = new HashMap<>(productMap);
            mutableProductMap.put("quantity", newQuantity);
            Float price = (Float) mutableProductMap.get("price");
            double newTotalPrice = price * newQuantity;
            mutableProductMap.put("totalPrice", newTotalPrice);
            hashOperations.put(key, product.getProductID(), mutableProductMap);

            return ResponseEntity.ok().body("Giỏ hàng đã được cập nhật");
        } else {
            System.out.println("Sản phẩm không tồn tại trong giỏ hàng.");
            return ResponseEntity.badRequest().body("Sản phẩm không tồn tại trong giỏ hàng.");
        }
    }

    @Transactional
    public void clearCart(Integer accountID) {
        String key = "CART:" + accountID;
        Map<Integer, Map<String, Object>> cartItems = hashOperations.entries(key);

        for (Map<String, Object> item : cartItems.values()) {
            Integer productID = (Integer) item.get("productID");
            Product product = productRepository.findById(productID).orElse(null);

            if (product != null) {
                int quantity = (int) item.get("quantity");
                product.setQuantity(product.getQuantity() + quantity);

                if (item.containsKey("sizeText")) {
                    String sizeText = (String) item.get("sizeText");
                    Size size = product.getSizes().stream()
                            .filter(s -> s.getText().equals(sizeText))
                            .findFirst()
                            .orElse(null);

                    if (size != null) {
                        size.setSizeQuantity(size.getSizeQuantity() + quantity);
                        sizeRepository.save(size);
                    }
                }

                productRepository.save(product);
            }
        }

        redisTemplate.delete(key);
    }

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
}