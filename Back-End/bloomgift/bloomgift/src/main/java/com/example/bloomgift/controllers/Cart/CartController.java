package com.example.bloomgift.controllers.Cart;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.bloomgift.model.Account;
import com.example.bloomgift.model.CartItem;
import com.example.bloomgift.model.Product;
import com.example.bloomgift.reponse.AccountReponse;
import com.example.bloomgift.reponse.ProductReponse;
import com.example.bloomgift.repository.AccountRepository;
import com.example.bloomgift.repository.ProductRepository;
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

    @PostMapping("/add")
    public ResponseEntity<String> addToCart(@RequestParam Integer accountId, @RequestParam Integer productId,
            @RequestParam int quantity) {
        // Lấy thông tin sản phẩm (giả sử bạn có ProductService)
        Product product = productRepository.findById(productId).orElseThrow();
        if (product == null) {
            return ResponseEntity.badRequest().body("Sản phẩm không tồn tại.");
        }

        cartService.addToCart(accountId, product, quantity);
        return ResponseEntity.ok("Thêm vào giỏ hàng thành công.");
    }

    @GetMapping("/view/{accountId}")
    public ResponseEntity<Map<Integer, Map<String, Object>>> viewCart(@PathVariable Integer accountId) {
        return ResponseEntity.ok(cartService.getCart(accountId));
    }
}
