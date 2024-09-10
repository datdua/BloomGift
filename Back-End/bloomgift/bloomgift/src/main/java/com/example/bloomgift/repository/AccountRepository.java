package com.example.bloomgift.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.bloomgift.model.Account;

import io.lettuce.core.dynamic.annotation.Param;

import java.util.Date;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AccountRepository extends JpaRepository<Account, Integer> {
        Account findByEmail(String email);

        Account findByPhone(Integer phone);

        boolean existsByEmail(String email);
        @Query("SELECT a FROM Account a WHERE " +
                        "(:accountID IS NULL OR a.accountID = :accountID) AND " +
                        "(:address IS NULL OR a.address LIKE %:address%) AND " +
                        "(:gender IS NULL OR a.gender LIKE %:gender%) AND " +
                        "(:phone IS NULL OR a.phone = :phone) AND " +
                        "(:fullName IS NULL OR a.fullname LIKE %:fullName%) AND " +
                        "(:birthday IS NULL OR a.birthday = :birthday)")
        Page<Account> findByFilters(@Param("fullName") String fullName,
                        @Param("birthday") Date birthday,
                        Pageable pageable,
                        @Param("accountID") int accountID,
                        @Param("gender") String gender,
                        @Param("phone") int phone,
                        @Param("address") String address);

}
