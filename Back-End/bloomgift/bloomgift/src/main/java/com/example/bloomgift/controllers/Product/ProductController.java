package com.example.bloomgift.controllers.Product;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import org.springframework.data.domain.Page;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.bloomgift.model.Product;
import com.example.bloomgift.reponse.ProductReponse;
import com.example.bloomgift.request.ProductRequest;
import com.example.bloomgift.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> createProduct(@RequestPart("productRequest") String productRequestJson,
            @RequestPart("imageFiles") List<MultipartFile> imageFiles) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ProductRequest productRequest = objectMapper.readValue(productRequestJson, ProductRequest.class);
            productService.createProduct(productRequest, imageFiles);
            return ResponseEntity.status(HttpStatus.CREATED).body("Product created successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while creating the product");
        }
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<ProductReponse>> getAllProducts() {
        List<ProductReponse> productResponses = productService.getAllProducts();
        return ResponseEntity.ok(productResponses);
    }

    @DeleteMapping("/delete-product/{productID}")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer productID) {
        try {
            productService.deleteProduct(productID);
            return ResponseEntity.ok(Collections.singletonMap("message", "Sản phẩm đã được xóa thành công"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("message", e.getMessage()));
        }
    }

    @PutMapping(value = "/update-product/{productID}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = "application/json;charset=UTF-8")
    public ResponseEntity<?> updateProduct(
            @PathVariable Integer productID,
            @RequestPart("productRequest") String productRequestJson,
            @RequestPart(value = "images", required = false) List<MultipartFile> images) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ProductRequest productRequest = objectMapper.readValue(productRequestJson, ProductRequest.class);

            Product product = productService.updateProduct(productID, productRequest, images);
            return ResponseEntity.ok(Collections.singletonMap("message", "Cập nhật thành công"));
        } catch (JsonProcessingException e) {
            return ResponseEntity.badRequest()
                    .body(Collections.singletonMap("message", "Lỗi xử lý dữ liệu JSON: " + e.getMessage()));
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

    @GetMapping("/products/status")
    public List<ProductReponse> getProductsByProductStatus(@RequestParam Boolean productStatus) {
        return productService.getProductsByProductStatus(productStatus);
    }

}
