package com.example.bloomgift.controllers.Revenue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bloomgift.service.RevenueService;

@RestController
@RequestMapping("/api/seller/revenue/revenue-management")
public class RevenueBySeller {
    @Autowired 
    private RevenueService revenueService;

    @GetMapping("/total-revenue-by-store/{storeID}")
    public ResponseEntity<Float> getTotalRevenueByStoreID(@PathVariable Integer storeID) {
        Float totalRevenue = revenueService.getTotalRevenueByStoreID(storeID);
        return new ResponseEntity<>(totalRevenue, HttpStatus.OK);
    }

}
