package com.example.bloomgift.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.bloomgift.model.Account;

import io.lettuce.core.dynamic.annotation.Param;

import java.util.Date;
import java.util.List;


public interface AccountRepository extends JpaRepository<Account,Integer>  {
    Account findByEmail(String email);
    Account findByPhone(Integer phone);

     @Query("SELECT a FROM Account a WHERE " +
           "(:accountID IS NULL OR a.accountID = :accountID) AND " +
           "(:text IS NULL OR (LOWER(a.email) LIKE LOWER(CONCAT('%', :text, '%')) OR " +
           "LOWER(a.fullname) LIKE LOWER(CONCAT('%', :text, '%')) OR " +
           "LOWER(a.address) LIKE LOWER(CONCAT('%', :text, '%')))) AND " +
           "(:birthday IS NULL OR a.birthday = :birthday) AND " +
           "(:phone IS NULL OR a.phone = :phone) AND " +
           "(:roleName IS NULL OR LOWER(a.roleID.roleName) LIKE LOWER(CONCAT('%', :roleName, '%')))")
    List<Account> searchAccounts(
            @Param("accountID") Integer accountID,
            @Param("text") String text,
            @Param("birthday") Date birthday,
            @Param("phone") Integer phone,
            @Param("roleName") String roleName);        
    
} 
