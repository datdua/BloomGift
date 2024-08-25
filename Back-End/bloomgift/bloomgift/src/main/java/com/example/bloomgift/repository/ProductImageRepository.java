package com.example.bloomgift.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bloomgift.model.ProductImage;

public interface ProductImageRepository extends JpaRepository<ProductImage,Integer> {

    
} 
