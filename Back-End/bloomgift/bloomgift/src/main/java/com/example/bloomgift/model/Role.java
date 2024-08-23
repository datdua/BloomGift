package com.example.bloomgift.model;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="Role")
public class Role {
     @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "roleID") 
    private Integer roleID; 

    @Column(name = "roleName")
    private String roleName ; 


    @OneToMany(mappedBy = "roleID")
    private Set<Account> accounts;

    public Role(){

    }

    public Role(Integer roleID, String roleName, Set<Account> accounts) {
        this.roleID = roleID;
        this.roleName = roleName;
        this.accounts = accounts;
    }

    public Integer getRoleID() {
        return roleID;
    }

    public void setRoleID(Integer roleID) {
        this.roleID = roleID;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Set<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }

    
}
