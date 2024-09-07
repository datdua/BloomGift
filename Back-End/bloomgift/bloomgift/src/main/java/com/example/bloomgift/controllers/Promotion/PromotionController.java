package com.example.bloomgift.controllers.Promotion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.bloomgift.model.Promotion;
import com.example.bloomgift.reponse.PromotionResponse;
import com.example.bloomgift.request.PromotionRequest;
import com.example.bloomgift.service.PromotionService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/promotion")
public class PromotionController {

    @Autowired
    private PromotionService promotionService;

    @GetMapping("/get-all")
    public List<PromotionResponse> getAllPromotions() {
        return promotionService.getAllPromotions();
    }

    @GetMapping("/get-by-promotionID/{promotionID}")
    public ResponseEntity<?> getPromotionById(@PathVariable int promotionID) {
        return promotionService.getPromotionById(promotionID);
    }

    @GetMapping("/get-by-promotion-code/{promotionCode}")
    public ResponseEntity<?> getPromotionByPromotionCode(@PathVariable String promotionCode) {
        return promotionService.getPromotionByPromotionCode(promotionCode);
    }

    @PostMapping(value ="/create", produces = "application/json;charset=UTF-8")
    public ResponseEntity<?> createPromotion(@RequestBody PromotionRequest promotionRequest) {
        return promotionService.createPromotion(promotionRequest);
    }

    @PutMapping(value = "/update/{promotionID}", produces = "application/json;charset=UTF-8")
    public ResponseEntity<?> updatePromotion(@PathVariable int promotionID, @RequestBody PromotionRequest promotionRequest) {
        return promotionService.updatePromotion(promotionID, promotionRequest);
    }

    @DeleteMapping(value = "/delete", produces = "application/json;charset=UTF-8")
    public ResponseEntity<?> deletePromotion(@RequestBody List<Integer> promotionIDs) {
        return promotionService.deletePromotion(promotionIDs);
    }

    @GetMapping(value = "/get-all-paging", produces = "application/json;charset=UTF-8")
    public Page<PromotionResponse> getPromotions(@RequestParam int page, @RequestParam int size) {
        return promotionService.getPromotions(page, size);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<?> getPromotionsByStatus(@PathVariable String status) {
        return promotionService.getPromotionsByStatus(status);
    }

    @GetMapping(value = "/search/get-paging", produces = "application/json;charset=UTF-8")
    public Page<PromotionResponse> searchPromotionWithFilterPage(
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