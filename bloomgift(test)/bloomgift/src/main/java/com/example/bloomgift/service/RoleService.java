package com.example.bloomgift.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bloomgift.model.Role;
import com.example.bloomgift.repository.RoleRepository;



@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }
}
