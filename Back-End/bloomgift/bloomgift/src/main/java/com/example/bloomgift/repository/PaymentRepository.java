package com.example.bloomgift.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bloomgift.model.Account;
import com.example.bloomgift.model.Payment;

public interface PaymentRepository extends JpaRepository<Payment,Integer>{

    Payment findByBankNumberAndTotalPrice(String accountNumber, Float amount);

    
}
