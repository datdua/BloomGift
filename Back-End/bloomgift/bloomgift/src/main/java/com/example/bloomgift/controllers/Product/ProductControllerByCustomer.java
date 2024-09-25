package com.example.bloomgift.controllers.Product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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


    @GetMapping("/get-product-by-storeID/{storeID}")
    public ResponseEntity<List<ProductReponse>> getProductsByStoreID(@PathVariable int storeID) {
        List<ProductReponse> products = productService.getProductByStoreID(storeID);
        return ResponseEntity.ok(products);
    }

    

    @GetMapping("/get-product-feature-true-by-storeID/{storeID}")
    public ResponseEntity<List<ProductReponse>> getProductsByStorIdAndFeaEntity(@PathVariable int storeID) {
        List<ProductReponse> products = productService.getProductByStoreID(storeID);
        return ResponseEntity.ok(products);
    }


    @GetMapping("/get-product-feature-true")
    public ResponseEntity<List<ProductReponse>> getProductFeatured() {
        List<ProductReponse> products = productService.getProductFeatured();
        return ResponseEntity.ok(products);
    }

       @GetMapping("/get-product-best-seller/{Top}")
    public ResponseEntity<List<ProductReponse>> getProductsBestSeller(@RequestParam int top) {
        List<ProductReponse> products = productService.getProductBySold(top);
        return ResponseEntity.ok(products);
    }
}
