package com.example.bloomgift.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.bloomgift.model.Combo;

@Repository
public interface ComboRepository extends JpaRepository<Combo, Integer> {
    
}
