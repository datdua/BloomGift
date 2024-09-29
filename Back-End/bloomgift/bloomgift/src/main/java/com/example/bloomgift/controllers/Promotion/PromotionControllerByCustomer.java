package com.example.bloomgift.controllers.Promotion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.bloomgift.model.Promotion;
import com.example.bloomgift.request.PromotionRequest;
import com.example.bloomgift.service.PromotionService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/customer/promotion")
public class PromotionControllerByCustomer {
    @Autowired
    private PromotionService promotionService;

    @GetMapping("/get-all")
    public List<Promotion> getAllPromotions() {
        return promotionService.getAllPromotions();
    }

    @GetMapping("/get-by-promotionID/{promotionID}")
    public Promotion getPromotionById(@PathVariable int promotionID) {
        return promotionService.getPromotionById(promotionID);
    }

    @GetMapping("/get-by-promotion-code/{promotionCode}")
    public Promotion getPromotionByPromotionCode(@PathVariable String promotionCode) {
        return promotionService.getPromotionByPromotionCode(promotionCode);
    }

    @GetMapping("/status/{status}")
    public Promotion getPromotionsByStatus(@PathVariable String status) {
        return promotionService.getPromotionsByStatus(status);
    }

    @GetMapping(value = "/search/get-paging", produces = "application/json;charset=UTF-8")
    public Page<Promotion> searchPromotionWithFilterPage(
            @RequestParam(required = false) String promotionDescription,
            @RequestParam(required = false) BigDecimal promotionDiscount,
            @RequestParam(required = false) String promotionStatus,
            @RequestParam(required = false) LocalDateTime startDate,
            @RequestParam(required = false) LocalDateTime endDate,
            @RequestParam(required = false) String storeName,
            @RequestParam int page,
            @RequestParam int size) {
        return promotionService.searchPromotionWithFilterPage(
                promotionDescription, promotionDiscount, promotionStatus, startDate, endDate, storeName, page, size);
    }
}
