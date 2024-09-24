package com.example.bloomgift.service;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.bloomgift.model.Store;
import com.example.bloomgift.model.Order;
import com.example.bloomgift.model.OrderDetail;
import com.example.bloomgift.repository.AccountRepository;
import com.example.bloomgift.repository.OrderDetailRepository;
import com.example.bloomgift.repository.OrderRepository;
import com.example.bloomgift.repository.ProductRepository;
import com.example.bloomgift.repository.PromotionRepository;
import com.example.bloomgift.repository.SizeRepository;
import com.example.bloomgift.repository.StoreRepository;

@Service
public class RevenueService {

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    public double calculateTotalRevenue() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .mapToDouble(Order::getOrderPrice)
                .sum();
    }

    public Float getTotalRevenueByStoreID(Integer storeID) {
        Store store = storeRepository.findById(storeID)
                .orElseThrow(() -> new RuntimeException("Store not found"));

        List<OrderDetail> orderDetails = orderDetailRepository.findByStoreID(store);

        double totalRevenue = orderDetails.stream()
                .map(OrderDetail::getOrderID) 
                .filter(Objects::nonNull)
                .mapToDouble(Order::getOrderPrice) 
                .sum();

        return (float) totalRevenue;
    }
}
