package com.example.bloomgift.controllers.Revenue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bloomgift.service.OrderSevice;
import com.example.bloomgift.service.RevenueService;

@RestController
@RequestMapping("/api/admin/revenue/revenue-management")
public class RevenueByAdmin {

    @Autowired 
    private RevenueService revenueService;

    @GetMapping("/total-revenue")
    public ResponseEntity<Double> getTotalRevenue() {
        double totalRevenue = revenueService.calculateTotalRevenue();
        return new ResponseEntity<>(totalRevenue, HttpStatus.OK);
    }
  
}
