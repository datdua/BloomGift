
package com.example.bloomgift.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bloomgift.model.Role;


public interface RoleRepository extends JpaRepository<Role, Integer>  {
    Role findByName(String name);
    List<Role> findAll();

    
}
