package com.example.bloomgift.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bloomgift.model.Account;
import com.example.bloomgift.model.Order;
import com.example.bloomgift.model.OrderDetail;
import com.example.bloomgift.model.Store;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findByAccountID(Account account);

    List<Order> findByOrderStatus(String orderStatus);

    List<Order> findByOrderDetailStoreID(Store store);
    
}