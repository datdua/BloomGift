package com.example.bloomgift.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.bloomgift.model.Product;
import com.example.bloomgift.model.ProductImage;

public interface ProductImageRepository extends JpaRepository<ProductImage,Integer> {

    // List<ProductImage> findAllByProductID(Product productID);

    @Query(value = "SELECT * FROM ProductImage WHERE productID = :productID", nativeQuery = true)
    List<ProductImage> findImagesByProductIDNative(@Param("productID") Integer productID);

    ProductImage findByProductID(Product existingProduct);
    
} 
