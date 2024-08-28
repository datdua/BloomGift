package com.example.bloomgift.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bloomgift.model.Product;
import com.example.bloomgift.model.ProductImage;
import com.example.bloomgift.reponse.ProductImageReponse;
import com.example.bloomgift.repository.ProductImageRepository;
import com.example.bloomgift.repository.ProductRepository;

@Service
public class ProductImageService {
    @Autowired
    private ProductImageRepository productImageRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    public List<ProductImageReponse> getListImagesByProductID(int productID) {
        List<ProductImage> productImages = productImageRepository.findImagesByProductIDNative(productID);
        return productImages.stream()
            .map(image -> new ProductImageReponse(image.getImageID(), image.getProductImage()))
            .collect(Collectors.toList());  
    }
    
    public void createProductImage(Integer productID, List<String> imageUrls) {
        Product product = productService.getProductById(productID); 
        if (product == null) {
            throw new RuntimeException("Product not found with ID: " + productID);
        }

        for (String imageUrl : imageUrls) {
            ProductImage image = new ProductImage();
            image.setProductImage(imageUrl);
            image.setProductID(product);
            productImageRepository.save(image);
        }
    
    }
  
   
    public void updateImageByImageId(Integer imageID, String newProductImage) {
        // Fetch the image by imageID
        ProductImage image = productImageRepository.findById(imageID)
                .orElseThrow(() -> new RuntimeException("Image not found with ID: " + imageID));

        // Update the image URL
        image.setProductImage(newProductImage);

        // Save the updated image
        productImageRepository.save(image);
    }
    public void deleteImageById(Integer imageID) {
        // Fetch the image by imageID
        ProductImage image = productImageRepository.findById(imageID)
                .orElseThrow(() -> new RuntimeException("Image not found with ID: " + imageID));

        // Delete the image
        productImageRepository.delete(image);
    }
  
}
