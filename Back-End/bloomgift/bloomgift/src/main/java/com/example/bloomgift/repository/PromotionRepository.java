package com.example.bloomgift.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.example.bloomgift.model.Promotion;
import java.util.List;

public interface  PromotionRepository extends JpaRepository<Promotion,Integer>, JpaSpecificationExecutor<Promotion> {
    
    Promotion findByPromotionCode(String promotionCode);

    List <Promotion> findByPromotionStatus(String promotionStatus);
    
}
