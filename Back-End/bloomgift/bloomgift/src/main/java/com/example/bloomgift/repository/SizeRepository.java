package com.example.bloomgift.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bloomgift.model.Product;
import com.example.bloomgift.model.Size;

public interface SizeRepository extends JpaRepository<Size,Integer>{

    Size findByProductID(Product existingProduct);

    
} 
