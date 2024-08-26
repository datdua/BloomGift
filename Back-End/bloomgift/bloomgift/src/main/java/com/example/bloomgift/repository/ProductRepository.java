package com.example.bloomgift.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.bloomgift.model.Product;
import com.example.bloomgift.reponse.ProductReponse;

public interface ProductRepository extends JpaRepository<Product,Integer> {
 
}