package com.example.bloomgift.controllers.Product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.bloomgift.model.ProductImage;
import com.example.bloomgift.reponse.ProductImageReponse;
import com.example.bloomgift.service.ProductImageService;


@RestController
@RequestMapping("/api/products-image")
public class ProductImageController {
    @Autowired
    private ProductImageService productImageService;

    @GetMapping("/{productID}/create-images")
    public ResponseEntity<List<ProductImageReponse>> getListImagesByProductID(@PathVariable int productID) {
        List<ProductImageReponse> productImageResponses = productImageService.getListImagesByProductID(productID);
        return ResponseEntity.ok(productImageResponses);
    }
    @PostMapping("/{productId}/images")
    public void createProductImages(@PathVariable Integer productId, @RequestBody List<String> imageUrls) {
        productImageService.createProductImage(productId, imageUrls);
    }
    @PutMapping("/update-images/{imageID}")
    public ResponseEntity<String> updateProductImage(
            @PathVariable Integer imageID,
            @RequestParam String newImageUrl) {
        try {
            productImageService.updateImageByImageId(imageID, newImageUrl);
            return new ResponseEntity<>("Image updated successfully", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("/delete-images/{imageID}")
    public ResponseEntity<String> deleteProductImage(@PathVariable Integer imageID) {
        try {
            productImageService.deleteImageById(imageID);
            return new ResponseEntity<>("Image deleted successfully", HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    
}
