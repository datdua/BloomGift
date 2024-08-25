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
@Table(name="Store")
public class Store {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "storeID") 
    private Integer storeID; 
    
    @OneToMany(mappedBy = "storeID")
    private Set<Product> products;

    @Column(name = "storeName")
    private String storeName;


    
    public Integer getStoreID() {
        return storeID;
    }

    public void setStoreID(Integer storeID) {
        this.storeID = storeID;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    
    
}
