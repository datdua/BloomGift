package com.example.bloomgift.reponse;

import jakarta.persistence.Column;

public class CategoryReponse {
    private Integer categoryID; 
    private String categoryName ;

    
    public CategoryReponse(Integer categoryID, String categoryName) {
        this.categoryID = categoryID;
        this.categoryName = categoryName;
    }
    public Integer getCategoryID() {
        return categoryID;
    }
    public void setCategoryID(Integer categoryID) {
        this.categoryID = categoryID;
    }
    public String getCategoryName() {
        return categoryName;
    }
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    
}
