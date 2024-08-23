package com.example.bloomgift.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bloomgift.model.Account;
import java.util.List;


public interface AccountRepository extends JpaRepository<Account,Integer>  {
    Account findByEmail(String email);
    Account findByPhone(Integer phone);
    
} 
