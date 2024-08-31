package com.example.bloomgift.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.example.bloomgift.model.Promotion;

public interface  PromotionRepository extends JpaRepository<Promotion,Integer>, JpaSpecificationExecutor<Promotion> {
    
    Promotion findByPromotionCode(String promotionCode);

    Promotion findByPromotionStatus(String promotionStatus);
    
}
