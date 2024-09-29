package com.example.bloomgift.controllers.Product;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.bloomgift.model.Product;
import com.example.bloomgift.reponse.ProductReponse;
import com.example.bloomgift.service.ProductService;

@RestController
@RequestMapping("/api/customer/product")
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

    @GetMapping("/search-product")
    public Page<Product> searchProductWithFilters(
            @RequestParam(required = false) String descriptionProduct,
            @RequestParam(required = false) String colourProduct,
            @RequestParam(required = false) Float priceProduct,
            @RequestParam(required = false) String productName,
            @RequestParam(required = false) String categoryName,
            @RequestParam(required = false) Date createDate,
            @RequestParam(required = false) String storeName,
            @RequestParam int page,
            @RequestParam int size) {

        return productService.searchProductWithFilterPage(
                descriptionProduct, colourProduct, priceProduct, productName,
                categoryName, createDate, storeName, page, size);

    }

    @GetMapping("/new-product")
    public List<ProductReponse> listNewProducts() {
        return productService.ListNewProduct();
    }
}
