package com.example.bloomgift.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.bloomgift.repository.DeliveryRepository;
import com.example.bloomgift.repository.StoreRepository;

public class DeliveryService {
    
    @Autowired
    private DeliveryRepository deliveryRepository;

    @Autowired
    private StoreRepository storeRepository;

}
