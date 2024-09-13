package com.example.bloomgift.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.bloomgift.model.Product;
import com.example.bloomgift.model.ProductImage;
import com.example.bloomgift.model.Store;
import com.example.bloomgift.reponse.ProductImageReponse;
import com.example.bloomgift.repository.ProductImageRepository;
import com.example.bloomgift.repository.ProductRepository;
import com.example.bloomgift.repository.StoreRepository;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

@Service
public class ProductImageService {
    @Autowired
    private ProductImageRepository productImageRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private FirebaseStorageService firebaseStorageService;

    @Autowired
    private StoreRepository storeRepository;

    public List<ProductImageReponse> getListImagesByProductID(int productID) {
        List<ProductImage> productImages = productImageRepository.findImagesByProductIDNative(productID);
        return productImages.stream()
                .map(image -> new ProductImageReponse(image.getImageID(), image.getProductImage()))
                .collect(Collectors.toList());
    }

    public void createProductImages(Integer productID, List<MultipartFile> imageFiles) {
        Product product = productService.getProductById(productID);
        if (product == null) {
            throw new RuntimeException("Product not found with ID: " + productID);
        }
        Store store = product.getStoreID();
        List<String> imageUrls = uploadImagesByStoreAndProduct(imageFiles, store.getStoreName(),
                product.getProductName());
        for (String imageUrl : imageUrls) {
            ProductImage image = new ProductImage();
            image.setProductImage(imageUrl);
            image.setProductID(product);
            productImageRepository.save(image);
        }
    }

    public List<String> uploadImagesByStoreAndProduct(List<MultipartFile> imageFiles, String storeName,
            String productName) {
        List<String> imageUrls = new ArrayList<>();

        for (MultipartFile imageFile : imageFiles) {
            try {
                String imageUrl = firebaseStorageService.uploadFileByProduct(imageFile, storeName, productName);
                imageUrls.add(imageUrl);
            } catch (IOException e) {
                throw new RuntimeException("Failed to upload image for product " + productName, e);
            }
        }

        return imageUrls;
    }

    public void updateImageByImageId(Integer imageID, MultipartFile newProductImage) throws IOException {
        ProductImage image = productImageRepository.findById(imageID)
                .orElseThrow(() -> new RuntimeException("Image not found with ID: " + imageID));
        String oldImageUrl = image.getProductImage();
        Product product = image.getProductID();
        Store store = product.getStoreID();
        firebaseStorageService.deleteFileFromFirebase(oldImageUrl, product.getProductName(), store.getStoreName());
        String sanitizedStoreName = store.getStoreName().replaceAll("[^a-zA-Z0-9]", "_");

        String folder = "products/" + sanitizedStoreName + "/" + product.getProductName() + "/";
        ;
        String newImageUrl = firebaseStorageService.updateImagetoFireBase(newProductImage, folder);

        image.setProductImage(newImageUrl);
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
