package com.example.bloomgift.controllers.Product;

import java.io.IOException;
import java.util.List;
import org.springframework.http.MediaType;

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
import org.springframework.web.multipart.MultipartFile;

import com.example.bloomgift.model.ProductImage;
import com.example.bloomgift.reponse.ProductImageReponse;
import com.example.bloomgift.service.ProductImageService;

@RestController
@RequestMapping("/api/products-image")
public class ProductImageController {
    @Autowired
    private ProductImageService productImageService;

    @GetMapping("/{productID}/images")
    public ResponseEntity<List<ProductImageReponse>> getListImagesByProductID(@PathVariable int productID) {
        List<ProductImageReponse> productImageResponses = productImageService.getListImagesByProductID(productID);
        return ResponseEntity.ok(productImageResponses);
    }

    @PostMapping(value ="/{productId}/create-images-mutiple",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> createImageProduct(
            @RequestParam("productID") Integer productID,
            @RequestParam("imageFiles") List<MultipartFile> imageFiles) {
        try {
            // Gọi service để tạo sản phẩm và upload hình ảnh
            productImageService.createProductImages(productID, imageFiles);
            return ResponseEntity.ok("Product and images uploaded successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } 
    }


    @PutMapping(value="/update-images/{imageID}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> updateProductImage(
            @PathVariable Integer imageID,
            @RequestParam("newImageFile") MultipartFile newImageFile) throws IOException {
        try {
            productImageService.updateImageByImageId(imageID, newImageFile);
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
