package com.example.bloomgift.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.bloomgift.model.Account;


public interface AccountRepository extends JpaRepository<Account, Long> {
    
    Optional<Account> findByEmail(String email);
    
    Account findByFullName(String fullename);
    
}
