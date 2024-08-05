package com.example.bloomgift.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bloomgift.model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    
}