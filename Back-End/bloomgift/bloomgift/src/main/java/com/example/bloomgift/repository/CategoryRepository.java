package com.example.bloomgift.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bloomgift.model.Category;


public interface CategoryRepository extends JpaRepository<Category,Integer>{

    Category findByCategoryName(String categoryName);
    
}
