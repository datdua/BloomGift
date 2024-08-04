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
@Table(name = "Role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "roleid")
    private Integer roleid;

    @Column(name = "name", nullable = false, unique = true)
    private String name;


    @OneToMany(mappedBy = "roleid")
    private Set<Account> accounts;


    public Role(){

    }


    public Role(Integer roleid, String name, Set<Account> accounts) {
        this.roleid = roleid;
        this.name = name;
        this.accounts = accounts;
    }


    public Integer getRoleid() {
        return roleid;
    }


    public void setRoleid(Integer roleid) {
        this.roleid = roleid;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public Set<Account> getAccounts() {
        return accounts;
    }


    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }
    
}
