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

import com.example.bloomgift.reponse.OrderReponse;
import com.example.bloomgift.request.OrderRequest;
import com.example.bloomgift.service.OrderService;

@RestController
@RequestMapping("/api/customer/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/create-order")
    public ResponseEntity<String> createOrder(
            @RequestParam Integer accountID,
            @RequestBody OrderRequest orderRequest) {
        try {
            orderService.createOrder(accountID, orderRequest);
            return ResponseEntity.ok("Order created successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/get-all-order")
    public ResponseEntity<List<OrderReponse>> getAllOrders() {
        List<OrderReponse> orders = orderService.getAllOrder();
        return ResponseEntity.ok(orders); // Return the list of orders in the response
    }

    @GetMapping("/history-order/{accountID}")
    public ResponseEntity<List<OrderReponse>> getHistoryOrderByCustomer(@PathVariable int accountID) {
        List<OrderReponse> orderReponses = orderService.getHistoryOrderByCustomer(accountID);
        return new ResponseEntity<>(orderReponses, HttpStatus.OK);
    }

    @GetMapping("/get-order-by-store/{storeID}")
    public ResponseEntity<List<OrderReponse>> getOrderByStore(@PathVariable int storeID) {
        List<OrderReponse> orderReponses = orderService.getOrderByStore(storeID);
        return new ResponseEntity<>(orderReponses, HttpStatus.OK);
    }

    @GetMapping("/get-order-by-id/{orderID}")
    public ResponseEntity<List<OrderReponse>> getOrderById(@PathVariable int orderID) {
        List<OrderReponse> orderReponses = orderService.getOrderByOrderID(orderID);
        return new ResponseEntity<>(orderReponses, HttpStatus.OK);
    }

    @GetMapping("/get-order-by-status/{orderStatus}")
    public ResponseEntity<List<OrderReponse>> getOrderByStatus(@PathVariable String orderStatus) {
        List<OrderReponse> orderReponses = orderService.getOrderByOrderStatus(orderStatus);
        return new ResponseEntity<>(orderReponses, HttpStatus.OK);
    }
}
