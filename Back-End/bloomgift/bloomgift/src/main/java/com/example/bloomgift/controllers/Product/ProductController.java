package com.example.bloomgift.controllers.Product;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.bloomgift.model.Product;
import com.example.bloomgift.reponse.ProductReponse;
import com.example.bloomgift.request.ProductRequest;
import com.example.bloomgift.service.ProductService;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    @Autowired
    private ProductService productService;


    @PostMapping("/create-product")
    public ResponseEntity<String> createProduct(@RequestBody ProductRequest productRequest) {
        try {
            productService.createProduct(productRequest);
            return new ResponseEntity<>("Product created successfully", HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/list-product")
        public ResponseEntity<List<ProductReponse>> getAllProducts() {
        List<ProductReponse> productResponses = productService.getAllProducts();
    return ResponseEntity.ok(productResponses);
    }
    @DeleteMapping("/{productID}")
    public ResponseEntity<String> deleteProduct(@PathVariable("productID") Integer productID){
        try {
            productService.deleteProduct(productID);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Account deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }

      @PutMapping(value = "/update-product/{productID}",
            produces = "application/json;charset=UTF-8")
    public ResponseEntity<?> updateProduct(
        @PathVariable Integer productID,    
        @RequestBody ProductRequest productRequest) {
        try{
            Product prodcut = productService.updateProduct(productID, productRequest);
            return ResponseEntity.ok(Collections.singletonMap("message", "Cập nhật thành công"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("message", e.getMessage()));
        }
   
    }
}
