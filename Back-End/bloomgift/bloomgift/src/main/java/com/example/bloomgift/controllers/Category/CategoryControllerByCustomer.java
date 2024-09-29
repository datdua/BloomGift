package com.example.bloomgift.controllers.Category;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bloomgift.reponse.CategoryReponse;
import com.example.bloomgift.service.CategoryService;

@RestController
@RequestMapping("/api/customer/category")
public class CategoryControllerByCustomer {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("list-category")
    public ResponseEntity<List<CategoryReponse>> getAllCategories() {
        List<CategoryReponse> categoryReponses = categoryService.getAllCategories();
        return ResponseEntity.ok(categoryReponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryReponse> getCategoryById(@PathVariable Integer id) {
        CategoryReponse categoryReponse = categoryService.getCategoryById(id);
        return new ResponseEntity<>(categoryReponse, HttpStatus.OK);
    }

}
