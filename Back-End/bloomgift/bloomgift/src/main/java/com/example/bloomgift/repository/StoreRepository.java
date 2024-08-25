package com.example.bloomgift.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bloomgift.model.Store;

public interface StoreRepository extends JpaRepository<Store,Integer> {

    Store findByStoreName(String storeName);

}
