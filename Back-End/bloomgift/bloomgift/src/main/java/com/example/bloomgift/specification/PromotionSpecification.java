package com.example.bloomgift.specification;

import org.springframework.data.jpa.domain.Specification;

import com.example.bloomgift.model.Promotion;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.example.bloomgift.model.Store;

import jakarta.persistence.criteria.Join;

public class PromotionSpecification {

    public static Specification<Promotion> hasPromotionDescription(String promotionDescription) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("promotionDescription"),
                promotionDescription);
    }

    public static Specification<Promotion> hasPromotionDiscount(BigDecimal promotionDiscount) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("promotionDiscount"),
                promotionDiscount);
    }

    public static Specification<Promotion> hasPromotionStatus(String promotionStatus) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("promotionStatus"), promotionStatus);
    }

    public static Specification<Promotion> hasStartDate(LocalDateTime startDate) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("startDate"), startDate);
    }

    public static Specification<Promotion> hasEndDate(LocalDateTime endDate) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("endDate"), endDate);
    }

    public static Specification<Promotion> hasStoreName(String storeName) {
        return (root, query, criteriaBuilder) -> {
            Join<Promotion, Store> storeJoin = root.join("storeID");
            return criteriaBuilder.equal(storeJoin.get("storeName"), storeName);
        };
    }
}
