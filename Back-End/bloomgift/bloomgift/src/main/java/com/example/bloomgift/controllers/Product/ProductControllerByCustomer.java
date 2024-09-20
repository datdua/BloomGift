package com.example.bloomgift.controllers.Product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bloomgift.reponse.ProductReponse;
import com.example.bloomgift.service.ProductService;

@RestController
@RequestMapping("/api/auth/product-customer")
public class ProductControllerByCustomer {
    @Autowired
    private ProductService productService;


     @GetMapping("/list-product-by-customer")
    public ResponseEntity<List<ProductReponse>> getAllProducts() {
        List<ProductReponse> productResponses = productService.getAllProductByCustomer();
        return ResponseEntity.ok(productResponses);
    }
}
