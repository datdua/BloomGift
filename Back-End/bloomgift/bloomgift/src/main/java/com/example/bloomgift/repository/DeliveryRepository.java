package com.example.bloomgift.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.bloomgift.model.Delivery;
public interface DeliveryRepository extends JpaRepository<Delivery,Integer> {

    
}  
