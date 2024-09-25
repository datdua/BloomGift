package com.example.bloomgift.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.bloomgift.model.OrderDetail;
import com.example.bloomgift.model.Store;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {

    List<OrderDetail> findByStoreID(Store stores);

    Float findTotalRevenueByStoreID(Store storeID);

    
}