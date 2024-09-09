package com.example.bloomgift.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.example.bloomgift.model.Store;

public interface StoreRepository extends JpaRepository<Store,Integer>, JpaSpecificationExecutor<Store> {

    Store findByStoreName(String storeName);

    

}
