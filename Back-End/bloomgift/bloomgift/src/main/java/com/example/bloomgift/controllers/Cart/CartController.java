package com.example.bloomgift.controllers.Cart;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.bloomgift.model.Account;
import com.example.bloomgift.model.CartItem;
import com.example.bloomgift.model.Product;
import com.example.bloomgift.model.Size;
import com.example.bloomgift.reponse.AccountReponse;
import com.example.bloomgift.reponse.ProductReponse;
import com.example.bloomgift.repository.AccountRepository;
import com.example.bloomgift.repository.ProductRepository;
import com.example.bloomgift.repository.SizeRepository;
import com.example.bloomgift.service.CartService;
import com.example.bloomgift.service.ProductService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/api/customer/cart")
public class CartController {
    @Autowired
    private CartService cartService;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private SizeRepository sizeRepository;

    @PostMapping("/add")
    public ResponseEntity<?> addToCart(@RequestParam Integer accountId, @RequestParam Integer productId,
            @RequestParam int quantity) {
        Account account = accountRepository.findById(accountId).orElseThrow();
        Product product = productRepository.findById(productId).orElseThrow();
        if (product == null) {
            return ResponseEntity.badRequest().body("Sản phẩm không tồn tại.");
        }

        return cartService.addToCart(account, product, quantity);
    }

    @PostMapping("/add-with-size")
    public ResponseEntity<?> addToCartWithSize(@RequestParam Integer accountId, @RequestParam Integer productId,
            @RequestParam int quantity, @RequestParam int sizeID) {
        Account account = accountRepository.findById(accountId).orElseThrow();
        Product product = productRepository.findById(productId).orElseThrow();
        Size size = sizeRepository.findById(sizeID).orElse(null);
        if (product == null) {
            return ResponseEntity.badRequest().body("Sản phẩm không tồn tại.");
        }

        if (size == null || !product.getSizes().contains(size)) {
            return ResponseEntity.badRequest().body("Kích cỡ không tồn tại hoặc không thuộc về sản phẩm.");
        }

        return cartService.addToCartWithSize(account, product, quantity, size);
    }

    @GetMapping("/view/{accountId}")
    public ResponseEntity<Map<String, Object>> viewCart(@PathVariable Integer accountId) {
        Account account = accountRepository.findById(accountId).orElseThrow();
        return ResponseEntity.ok(cartService.getCart(account));
    }

    @DeleteMapping("/remove")
    public ResponseEntity<String> removeFromCart(@RequestParam Integer accountId, @RequestParam Integer productId) {
        Account account = accountRepository.findById(accountId).orElseThrow();
        Product product = productRepository.findById(productId).orElseThrow();
        cartService.DeteleCart(account, product);
        return ResponseEntity.ok("Đã xóa sản phẩm khỏi giỏ hàng.");
    }

    @PutMapping("/update-quantity")
    public ResponseEntity<?> updateCartQuantity(
            @RequestParam Integer accountId,
            @RequestParam Integer productId,
            @RequestParam Integer quantity) {

        // Retrieve the account and product (assuming these are already managed)
        Account account = accountRepository.findById(accountId).orElseThrow();
        Product product = productRepository.findById(productId).orElseThrow();

        return cartService.updateCart(account, product, quantity);
    }

    @PostMapping("/checkout-selected")
    public ResponseEntity<Map<String, Object>> checkoutSelectedProducts(
            @RequestParam Integer accountId,
            @RequestParam List<Integer> selectedProductIds) {

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));

        // Calculate the total for the selected products
        Map<String, Object> checkoutDetails = cartService.calculateTotalForSelectedProducts(account,
                selectedProductIds);

        return ResponseEntity.ok(checkoutDetails);
    }
}
