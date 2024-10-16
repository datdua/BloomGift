package com.example.bloomgift.controllers.Order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.bloomgift.reponse.OrderByStoreReponse;
import com.example.bloomgift.reponse.OrderReponse;
import com.example.bloomgift.request.OrderRequest;
import com.example.bloomgift.service.OrderService;

@RestController
@RequestMapping("/api/seller/order/order-management")
public class OrderControllerBySeller {

    @Autowired
    private OrderService orderService;

    // @PostMapping("/create-order")
    // public ResponseEntity<String> createOrder(
    //         @RequestParam Integer accountID,
    //         @RequestBody OrderRequest orderRequest) {
    //     try {
    //         orderService.createOrder(accountID, orderRequest);
    //         return ResponseEntity.ok("Order created successfully");
    //     } catch (RuntimeException e) {
    //         return ResponseEntity.badRequest().body(e.getMessage());
    //     }
    // } 
    @GetMapping("/get-all-order-by-store/{storeID}")
    public ResponseEntity<List<OrderByStoreReponse>> getAllOrderByStore(@PathVariable int storeID) {
        List<OrderByStoreReponse> orderReponses = orderService.getAllOrderByStore(storeID);
        return new ResponseEntity<>(orderReponses, HttpStatus.OK);
    }

    @GetMapping("/get-order-by-id/{orderID}")
    public ResponseEntity<OrderReponse> getOrderById(@PathVariable int orderID) {
        OrderReponse orderReponses = orderService.getOrderByOrderID(orderID);
        return new ResponseEntity<>(orderReponses, HttpStatus.OK);
    }

    @PostMapping("/update-order-status/{orderID}")
    public ResponseEntity<?> updateOrderStatus(@PathVariable Integer orderID, @RequestParam String orderStatus) {
        return orderService.updateOrderStatus(orderID, orderStatus);
    }

}

