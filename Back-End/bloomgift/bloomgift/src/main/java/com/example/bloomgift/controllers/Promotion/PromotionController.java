package com.example.bloomgift.controllers.Promotion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.stream.Collectors;

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
        List<Promotion> promotions = promotionService.getAllPromotions();
        return promotions.stream()
                .map(promotionService::convertPromotionToPromotionResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/get-by-promotionID/{promotionID}")
    public ResponseEntity<?> getPromotionById(@PathVariable int promotionID) {
        Promotion promotion = promotionService.getPromotionById(promotionID);
        if (promotion == null) {
            return ResponseEntity.status(404).body("Không tìm thấy mã giảm giá với ID này.");
        }
        PromotionResponse response = promotionService.convertPromotionToPromotionResponse(promotion);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-by-promotion-code/{promotionCode}")
    public ResponseEntity<?> getPromotionByPromotionCode(@PathVariable String promotionCode) {
        Promotion promotion = promotionService.getPromotionByPromotionCode(promotionCode);
        if (promotion == null) {
            return ResponseEntity.status(404).body("Không tìm thấy mã giảm giá với mã này.");
        }
        PromotionResponse response = promotionService.convertPromotionToPromotionResponse(promotion);
        return ResponseEntity.ok(response);
    }

    @PostMapping(value ="/create", produces = "application/json;charset=UTF-8")
    public ResponseEntity<?> createPromotion(@RequestParam Integer storeID  , @RequestBody PromotionRequest promotionRequest) {
        return promotionService.createPromotion(storeID, promotionRequest);
    }

    @PutMapping(value = "/update/{promotionID}", produces = "application/json;charset=UTF-8")
    public ResponseEntity<?> updatePromotion(@PathVariable int promotionID, @RequestBody PromotionRequest promotionRequest) {
        return promotionService.updatePromotion(promotionID, promotionRequest);
    }

    @DeleteMapping(value = "/delete", produces = "application/json;charset=UTF-8")
    public ResponseEntity<?> deletePromotion(@RequestBody List<Integer> promotionIDs) {
        return promotionService.deletePromotion(promotionIDs);
    }

   @GetMapping(value = "/paging", produces = "application/json;charset=UTF-8")
    public Page<PromotionResponse> getPromotions(@RequestParam int page, @RequestParam int size) {
        Page<Promotion> promotionPage = promotionService.getPromotions(page, size);
        return promotionPage.map(promotionService::convertPromotionToPromotionResponse);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<?> getPromotionsByStatus(@PathVariable String status) {
        Promotion promotion = promotionService.getPromotionsByStatus(status);
        if (promotion == null) {
            return ResponseEntity.status(404).body("Không tìm thấy khuyến mãi với trạng thái này.");
        }
        PromotionResponse response = promotionService.convertPromotionToPromotionResponse(promotion);
        return ResponseEntity.ok(response);
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
        Page<Promotion> promotionPage = promotionService.searchPromotionWithFilterPage(
                promotionDescription, promotionDiscount, promotionStatus, startDate, endDate, storeName, page, size);
        // Chuyển đổi Page<Promotion> sang Page<PromotionResponse>
        return promotionPage.map(promotionService::convertPromotionToPromotionResponse);
    }
}