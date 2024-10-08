package com.example.bloomgift.controllers.Category;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bloomgift.model.Category;
import com.example.bloomgift.reponse.CategoryReponse;
import com.example.bloomgift.request.CategoryRequest;
import com.example.bloomgift.service.CategoryService;


@RestController
@RequestMapping("/api/category")
public class CategoryController {
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
        return new ResponseEntity<>(categoryReponse,HttpStatus.OK);
    }

    @PostMapping("create-category")
    public Category createCategory(@RequestBody CategoryRequest categoryRequest) {
        return categoryService.createCategory(categoryRequest);
    }

    @PutMapping("update-category/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Integer id, @RequestBody CategoryRequest categoryRequest) {
        Category updatedCategory = categoryService.updateCategory(id, categoryRequest);
        return ResponseEntity.ok(updatedCategory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Integer id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}