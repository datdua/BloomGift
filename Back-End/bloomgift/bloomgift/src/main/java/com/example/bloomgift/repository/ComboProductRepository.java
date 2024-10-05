package com.example.bloomgift.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bloomgift.model.ComboProduct;
import com.example.bloomgift.model.ComboProductKey;

public interface ComboProductRepository extends JpaRepository<ComboProduct, ComboProductKey> {
    // List<ComboProduct> findByproductID(Integer productID);
    List<ComboProduct> findByProduct_ProductID(Integer productID);
}
