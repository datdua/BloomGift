package com.example.bloomgift.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.bloomgift.model.Promotion;
import com.example.bloomgift.model.Store;
import com.example.bloomgift.reponse.PromotionResponse;
import com.example.bloomgift.repository.PromotionRepository;
import com.example.bloomgift.repository.StoreRepository;
import com.example.bloomgift.request.PromotionRequest;
import com.example.bloomgift.specification.PromotionSpecification;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@Service
public class PromotionService {

    @Autowired
    private PromotionRepository promotionRepository;

    @Autowired
    private StoreRepository storeRepository;

    public List<Promotion> getAllPromotions() {
        return promotionRepository.findAll();
    }

    public Promotion getPromotionById(int promotionID) {
        return promotionRepository.findById(promotionID).orElse(null);
    }

    public Promotion getPromotionByPromotionCode(String promotionCode) {
    return promotionRepository.findByPromotionCode(promotionCode);
    }

    public PromotionResponse convertPromotionToPromotionResponse(Promotion promotion) {
        PromotionResponse response = new PromotionResponse();
        response.setPromotionID(promotion.getPromotionID());
        response.setPromotionName(promotion.getPromotionName());
        response.setPromotionCode(promotion.getPromotionCode());
        response.setPromotionDescription(promotion.getPromotionDescription());
        response.setPromotionDiscount(promotion.getPromotionDiscount());
        response.setQuantity(promotion.getQuantity());
        response.setPromotionStatus(promotion.getPromotionStatus());
        response.setStartDate(promotion.getStartDate());
        response.setEndDate(promotion.getEndDate());
        response.setStoreName(promotion.getStoreID().getStoreName());
        return response;
    }
    
    public ResponseEntity<?> createPromotion(@RequestParam Integer storeID,
            @RequestBody PromotionRequest promotionRequest) {

        if (promotionRequest.getStartDate().isAfter(promotionRequest.getEndDate())) {
            return ResponseEntity.badRequest().body("Ngày bắt đầu không thể sau ngày kết thúc.");
        }

        if (promotionRepository.findByPromotionCode(promotionRequest.getPromotionCode()) != null) {
            return ResponseEntity.badRequest().body("Mã giảm giá đã tồn tại.");
        }

        Store store = storeRepository.findById(storeID).orElse(null);
        if (store == null) {
            return ResponseEntity.badRequest().body("Không tìm thấy cửa hàng.");
        }

        Promotion promotion = new Promotion();
        promotion.setPromotionName(promotionRequest.getPromotionName());
        promotion.setPromotionCode(promotionRequest.getPromotionCode());
        promotion.setPromotionDiscount(promotionRequest.getPromotionDiscount());
        promotion.setQuantity(promotionRequest.getQuantity());
        promotion.setStartDate(promotionRequest.getStartDate());
        promotion.setEndDate(promotionRequest.getEndDate());
        promotion.setPromotionDescription(promotionRequest.getPromotionDescription());
        promotion.setPromotionStatus("Có hiệu lực");
        promotion.setStoreID(store);
        promotionRepository.save(promotion);
        return ResponseEntity.ok(Collections.singletonMap("message", "Tạo mã giảm giá thành công."));
    }

    public ResponseEntity<?> updatePromotion(int promotionID, PromotionRequest promotionRequest) {
        Promotion promotion = promotionRepository.findById(promotionID).orElse(null);
        if (promotion == null) {
            return ResponseEntity.badRequest().body("Không tìm thấy mã giảm giá.");
        }

        if (promotionRequest.getStartDate().isAfter(promotionRequest.getEndDate())) {
            return ResponseEntity.badRequest().body("Ngày bắt đầu không thể sau ngày kết thúc.");
        }

        if (!promotion.getPromotionCode().equals(promotionRequest.getPromotionCode())
                && promotionRepository.findByPromotionCode(promotionRequest.getPromotionCode()) != null) {
            return ResponseEntity.badRequest().body("Mã giảm giá đã tồn tại.");
        }

        promotion.setPromotionName(promotionRequest.getPromotionName());
        promotion.setPromotionCode(promotionRequest.getPromotionCode());
        promotion.setPromotionDiscount(promotionRequest.getPromotionDiscount());
        promotion.setQuantity(promotionRequest.getQuantity());
        promotion.setStartDate(promotionRequest.getStartDate());
        promotion.setEndDate(promotionRequest.getEndDate());
        promotion.setPromotionStatus(promotionRequest.getPromotionStatus());
        promotion.setPromotionDescription(promotionRequest.getPromotionDescription());
        promotionRepository.save(promotion);
        return ResponseEntity.ok(Collections.singletonMap("message", "Cập nhật mã giảm giá thành công."));
    }

    public ResponseEntity<?> deletePromotion(Integer promotionID) {
        Promotion promotion = promotionRepository.findById(promotionID).orElse(null);
        if (promotion == null) {
            return ResponseEntity.badRequest().body("Không tìm thấy mã giảm giá.");
        }
        promotionRepository.delete(promotion);
        return ResponseEntity.ok(Collections.singletonMap("message", "Xóa mã giảm giá thành công."));
    }

    public Page<Promotion> getPromotions(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return promotionRepository.findAll(pageable);
    }

    public List<Promotion> getPromotionsByStatus(String status) {
        return promotionRepository.findByPromotionStatus(status);
    }

    public Page<Promotion> searchPromotionWithFilterPage(
            String promotionDescription,
            BigDecimal promotionDiscount,
            String promotionStatus,
            LocalDateTime startDate,
            LocalDateTime endDate,
            String storeName,
            int page,
            int size) {

        Specification<Promotion> spec = Specification.where(null);

        if (promotionDescription != null && !promotionDescription.isEmpty()) {
            spec = spec.and(PromotionSpecification.hasPromotionDescription(promotionDescription));
        }
        if (promotionDiscount != null) {
            spec = spec.and(PromotionSpecification.hasPromotionDiscount(promotionDiscount));
        }
        if (promotionStatus != null && !promotionStatus.isEmpty()) {
            spec = spec.and(PromotionSpecification.hasPromotionStatus(promotionStatus));
        }
        if (startDate != null) {
            spec = spec.and(PromotionSpecification.hasStartDate(startDate));
        }
        if (endDate != null) {
            spec = spec.and(PromotionSpecification.hasEndDate(endDate));
        }
        if (storeName != null && !storeName.isEmpty()) {
            spec = spec.and(PromotionSpecification.hasStoreName(storeName));
        }

        Pageable pageable = PageRequest.of(page, size);
        return promotionRepository.findAll(spec, pageable);
    }



    
}
