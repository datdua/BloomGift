package com.example.bloomgift.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.bloomgift.model.Promotion;
import com.example.bloomgift.model.Store;
import com.example.bloomgift.reponse.PromotionResponse;
import com.example.bloomgift.reponse.StoreResponse;
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

    private PromotionResponse convertPromotionToResponse(Promotion promotion) {
        PromotionResponse promotionResponse = new PromotionResponse();
        promotionResponse.setPromotionID(promotion.getPromotionID());
        promotionResponse.setPromotionName(promotion.getPromotionName());
        promotionResponse.setPromotionCode(promotion.getPromotionCode());
        promotionResponse.setPromotionDescription(promotion.getPromotionDescription());
        promotionResponse.setPromotionDiscount(promotion.getPromotionDiscount());
        promotionResponse.setQuantity(promotion.getQuantity());
        promotionResponse.setPromotionStatus(promotion.getPromotionStatus());
        promotionResponse.setStartDate(promotion.getStartDate());
        promotionResponse.setEndDate(promotion.getEndDate());

        // Create a StoreResponse
        StoreResponse storeResponse = new StoreResponse();
        storeResponse.setStoreID(promotion.getStoreID().getStoreID());
        storeResponse.setStoreName(promotion.getStoreID().getStoreName());
        storeResponse.setType(promotion.getStoreID().getType());
        storeResponse.setStorePhone(promotion.getStoreID().getStorePhone());
        storeResponse.setStoreAddress(promotion.getStoreID().getStoreAddress());
        storeResponse.setStoreEmail(promotion.getStoreID().getStoreEmail());
        storeResponse.setBankAccountName(promotion.getStoreID().getBankAccountName());
        storeResponse.setBankNumber(promotion.getStoreID().getBankNumber());
        storeResponse.setBankAddress(promotion.getStoreID().getBankAddress());
        storeResponse.setTaxNumber(promotion.getStoreID().getTaxNumber());
        storeResponse.setStoreStatus(promotion.getStoreID().getStoreStatus());
        storeResponse.setStoreAvatar(promotion.getStoreID().getStoreAvatar());
        storeResponse.setIdentityCard(promotion.getStoreID().getIdentityCard());
        storeResponse.setIdentityName(promotion.getStoreID().getIdentityName());

        // storeResponse to promotionResponse
        promotionResponse.setStoreID(storeResponse);

        return promotionResponse;
    }

    public List<PromotionResponse> getAllPromotions() {
        List<Promotion> promotions = promotionRepository.findAll();
        List<PromotionResponse> promotionResponses = new ArrayList<>();
        for (Promotion promotion : promotions) {
            promotionResponses.add(convertPromotionToResponse(promotion));
        }
        return promotionResponses;
    }

    public ResponseEntity<?> getPromotionById(int promotionID) {
        Promotion promotion = promotionRepository.findById(promotionID).orElse(null);
        if (promotion == null) {
            return ResponseEntity.badRequest().body("Không tìm thấy mã giảm giá.");
        } else {
            return ResponseEntity.ok(convertPromotionToResponse(promotion));
        }
    }

    public ResponseEntity<?> getPromotionByPromotionCode(String promotionCode) {
        Promotion promotion = promotionRepository.findByPromotionCode(promotionCode);
        if (promotion == null) {
            return ResponseEntity.badRequest().body("Không tìm thấy mã giảm giá.");
        } else {
            return ResponseEntity.ok(convertPromotionToResponse(promotion));
        }
    }

    public ResponseEntity<?> createPromotion(@RequestBody PromotionRequest promotionRequest) {

        if (promotionRequest.getStartDate().isAfter(promotionRequest.getEndDate())) {
            return ResponseEntity.badRequest().body("Ngày bắt đầu không thể sau ngày kết thúc.");
        }

        if (promotionRepository.findByPromotionCode(promotionRequest.getPromotionCode()) != null) {
            return ResponseEntity.badRequest().body("Mã giảm giá đã tồn tại.");
        }

        Store store = storeRepository.findById(promotionRequest.getStoreID()).orElse(null);
        if (store == null) {
            return ResponseEntity.badRequest().body("Không tìm thấy cửa hàng.");
        }

        Promotion promotion = new Promotion();
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

        Store store = storeRepository.findById(promotionRequest.getStoreID()).orElse(null);
        if (store == null) {
            return ResponseEntity.badRequest().body("Không tìm thấy cửa hàng.");
        }

        promotion.setPromotionCode(promotionRequest.getPromotionCode());
        promotion.setPromotionDiscount(promotionRequest.getPromotionDiscount());
        promotion.setQuantity(promotionRequest.getQuantity());
        promotion.setStartDate(promotionRequest.getStartDate());
        promotion.setEndDate(promotionRequest.getEndDate());
        promotion.setPromotionStatus(promotionRequest.getPromotionStatus());
        promotion.setPromotionDescription(promotionRequest.getPromotionDescription());
        promotion.setStoreID(store);
        promotionRepository.save(promotion);
        return ResponseEntity.ok(Collections.singletonMap("message", "Cập nhật mã giảm giá thành công."));
    }

    public ResponseEntity<?> deletePromotion(@RequestBody List<Integer> promotionIDs) {
        // Filter existent promotionIDs
        List<Integer> existingPromotionIDs = promotionIDs.stream()
                .filter(promotionID -> promotionRepository.existsById(promotionID))
                .collect(Collectors.toList());

        // Filter non-existent promotionIDs
        List<Integer> nonExistentPromotionIDs = promotionIDs.stream()
                .filter(promotionID -> !existingPromotionIDs.contains(promotionID))
                .collect(Collectors.toList());

        if (existingPromotionIDs.isEmpty()) {
            return ResponseEntity.badRequest().body("Không tìm thấy cửa hàng để xóa");
        } else {
            promotionRepository.deleteAllById(existingPromotionIDs);
            String message = "Xóa các cửa hàng thành công";
            if (!nonExistentPromotionIDs.isEmpty()) {
                message += ". Các cửa hàng không tồn tại: " + nonExistentPromotionIDs;
            }
            return ResponseEntity.ok().body(message);
        }
    }

    public Page<PromotionResponse> getPromotions(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Promotion> promotions = promotionRepository.findAll(pageable);
        return promotions.map(this::convertPromotionToResponse);
    }

    public ResponseEntity<?> getPromotionsByStatus(String promotionStatus) {
        Promotion promotion = promotionRepository.findByPromotionStatus(promotionStatus);
        if (promotion == null) {
            return ResponseEntity.badRequest().body("Không tìm thấy mã giảm giá.");
        } else {
            return ResponseEntity.ok(convertPromotionToResponse(promotion));
        }
    }

    public Page<PromotionResponse> searchPromotionWithFilterPage(
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
        Page<Promotion> promotions = promotionRepository.findAll(spec, pageable);

        return promotions.map(this::convertPromotionToResponse);
    }

}
