package com.example.bloomgift.controllers.Order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bloomgift.reponse.OrderReponse;
import com.example.bloomgift.service.OrderService;

@RestController
@RequestMapping("/api/admin/order")
public class OrderControllerByAdmin {

    @Autowired
    private OrderService orderService;

    @GetMapping("/get-all-order")
    public ResponseEntity<List<OrderReponse>> getAllOrders() {
        List<OrderReponse> orders = orderService.getAllOrder();
        return ResponseEntity.ok(orders);
    }

}
