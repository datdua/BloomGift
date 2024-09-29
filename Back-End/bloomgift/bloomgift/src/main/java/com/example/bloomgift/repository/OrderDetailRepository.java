package com.example.bloomgift.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.bloomgift.model.OrderDetail;
import com.example.bloomgift.model.Product;
import com.example.bloomgift.model.Store;
import com.example.bloomgift.model.Order;
import com.example.bloomgift.model.Size;
import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {

    List<OrderDetail> findByStoreID(Store store);

    Float findTotalRevenueByStoreID(Store storeID);

    List<OrderDetail> findByStoreIDAndOrderID(Store storeID, Order orderID);

    List<OrderDetail> findByOrderID(Order orderID);

    List<OrderDetail> findBySizeID(Size size);

    List<OrderDetail> findByProductID(Product product);
}