package com.example.bloomgift.controllers.Product;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import org.springframework.data.domain.Page;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
            productService.createProductt(productRequest);
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
    public ResponseEntity<String> deleteProduct(@PathVariable("productID") Integer productID) {
        try {
            productService.deleteProduct(productID);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Account deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }

    @PutMapping(value = "/update-product/{productID}", produces = "application/json;charset=UTF-8")
    public ResponseEntity<?> updateProduct(
            @PathVariable Integer productID,
            @RequestBody ProductRequest productRequest) {
        try {
            Product prodcut = productService.updateProduct(productID, productRequest);
            return ResponseEntity.ok(Collections.singletonMap("message", "Cập nhật thành công"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("message", e.getMessage()));
        }

    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductReponse> getProductById(@PathVariable int productId) {
        try {
            ProductReponse productResponse = productService.getProductByID(productId);
            return ResponseEntity.ok(productResponse);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
      @GetMapping("/product/{storeID}")
    public ResponseEntity<List<ProductReponse>> getProductsByStoreID(@PathVariable int storeID) {
        // Call service to get the list of products for the given storeID
        List<ProductReponse> products = productService.getProductByStoreID(storeID);
        
        // Return the list of products wrapped in a ResponseEntity
        return ResponseEntity.ok(products);
    }
    @GetMapping("/product-feature-true/{storeID}")
    public ResponseEntity<List<ProductReponse>> getProductsByStorIdAndFeaEntity(@PathVariable int storeID) {
        // Call service to get the list of products for the given storeID
        List<ProductReponse> products = productService.getProductByStoreID(storeID);
        
        // Return the list of products wrapped in a ResponseEntity
        return ResponseEntity.ok(products);
    }
    @GetMapping("/search")
    public ResponseEntity<Page<Product>> searchProductWithFilters(
            @RequestParam(required = false) String descriptionProduct,
            @RequestParam(required = false) String colourProduct,
            @RequestParam(required = false) Float priceProduct,
            @RequestParam(required = false) String productName,
            @RequestParam(required = false) String categoryName,
            @RequestParam(required = false) Date createDate,
            @RequestParam(required = false) String storeName,
            @RequestParam(required = false) Integer sizeProduct,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<Product> products = productService.searhProductWithFilterPage(
                descriptionProduct, colourProduct, priceProduct, productName,
                categoryName, createDate, storeName, sizeProduct, page, size);

        return ResponseEntity.ok(products);
    }
    @GetMapping("/new-product")
    public List<ProductReponse> listNewProducts() {
        return productService.ListNewProduct();
    }

}
