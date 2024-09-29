package com.example.bloomgift.controllers.Product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bloomgift.request.SizeRequest;
import com.example.bloomgift.service.SizeSerivce;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/seller/products/product-size/product-size-management")
public class ProductSizeController {
    @Autowired
    private SizeSerivce sizeService;

    @PostMapping("/create/{productId}")
    public ResponseEntity<String> createProductBySize(
            @PathVariable Integer productId,
            @RequestBody SizeRequest sizeRequest) {
        try {
            sizeService.createProductBySize(productId, sizeRequest);
            return ResponseEntity.ok("Size created successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/update/{sizeId}")
    public ResponseEntity<String> updateProductBySize(
            @PathVariable Integer sizeId,
            @RequestBody SizeRequest sizeRequest) {
        try {
            sizeService.updateProductBySize(sizeId,sizeRequest);
            return ResponseEntity.ok("Size updated successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{sizeId}")
    public ResponseEntity<String> deleteProductBySize(@PathVariable Integer sizeId) {
        try {
            sizeService.deleteProductBySize(sizeId);
            return ResponseEntity.ok("Size deleted successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
