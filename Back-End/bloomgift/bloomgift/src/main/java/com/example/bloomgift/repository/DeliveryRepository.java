package com.example.bloomgift.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.bloomgift.model.Delivery;
import com.example.bloomgift.model.Order;
import com.example.bloomgift.model.Store;
public interface DeliveryRepository extends JpaRepository<Delivery,Integer> {

    Delivery findByStoreID(Store stores);


    
    Optional<Delivery> findByOrderIDAndStoreID(Order order, Store store);

    
}  
