package com.example.bloomgift.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Sort;
import com.example.bloomgift.model.Account;

import java.util.Date;
import java.util.List;
import java.util.Optional;


public interface AccountRepository extends JpaRepository<Account, Integer> {
    Account findByEmail(String email);

    Optional<Account> findByid(Integer id);

    Account findByFullname(String fullname);

    List<Account> findByRoleid_Name(String roleName);

    @Query("SELECT a FROM Account a WHERE " +
           "(:id IS NULL OR a.id = :id) AND " +
           "(:text IS NULL OR (LOWER(a.email) LIKE LOWER(CONCAT('%', :text, '%')) OR " +
           "LOWER(a.fullname) LIKE LOWER(CONCAT('%', :text, '%')) OR " +
           "LOWER(a.address) LIKE LOWER(CONCAT('%', :text, '%')))) AND " +
           "(:birthday IS NULL OR a.birthday = :birthday) AND " +
           "(:phone IS NULL OR a.phone = :phone) AND " +
           "(:roleName IS NULL OR LOWER(a.roleid.name) LIKE LOWER(CONCAT('%', :roleName, '%')))")
    List<Account> searchAccounts(
            @Param("id") Integer id,
            @Param("text") String text,
            @Param("birthday") Date birthday,
            @Param("phone") Integer phone,
            @Param("roleName") String roleName);        
}
