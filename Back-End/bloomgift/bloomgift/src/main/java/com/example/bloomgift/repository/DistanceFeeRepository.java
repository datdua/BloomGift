package com.example.bloomgift.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bloomgift.model.DistanceFee;
import com.example.bloomgift.model.Store;

@Repository
public interface DistanceFeeRepository extends JpaRepository<DistanceFee,Integer>{

    DistanceFee findByStore_StoreID(Integer storeID);  // ĐÚNG

    DistanceFee findByStore(Store store);


    
}
