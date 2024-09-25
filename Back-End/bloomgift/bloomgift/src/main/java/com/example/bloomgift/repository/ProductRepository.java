package com.example.bloomgift.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.example.bloomgift.model.Product;
import com.example.bloomgift.model.ProductImage;
import com.example.bloomgift.model.Promotion;
import com.example.bloomgift.model.Store;


public interface ProductRepository extends JpaRepository<Product,Integer> , JpaSpecificationExecutor<Product>{

    List<Product> findProductByStoreID(Store storeID);
    
    List<Product> findByProductStatus(Boolean productStatus);
  
}