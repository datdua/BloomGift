package com.example.bloomgift.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bloomgift.model.Account;

import java.nio.file.OpenOption;
import java.util.List;
import java.util.Optional;


public interface AccountRepository extends JpaRepository<Account, Integer> {
    Account findByEmail(String email);

    Optional<Account> findByid(Integer id);
}
