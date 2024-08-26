package com.example.bloomgift.service;
import com.example.bloomgift.model.Category;
import com.example.bloomgift.reponse.CategoryReponse;
import com.example.bloomgift.repository.CategoryRepository;
import com.example.bloomgift.request.CategoryRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors; 
@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public List<CategoryReponse> getAllCategories() {

        List<Category>categories= categoryRepository.findAll();
        return categories.stream()
        .map(category -> new CategoryReponse(
                category.getCategoryID(),
                category.getCategoryName()
            ))
            .collect(Collectors.toList());
    }

    public CategoryReponse getCategoryById(Integer id) {
        Category category =  categoryRepository.findById(id).orElseThrow();
        return new CategoryReponse(
            category.getCategoryID(),
            category.getCategoryName()
            );
    }

    public void createCategory(CategoryRequest categoryRequest) {

        String categoryName = categoryRequest.getCategoryName();
        if(categoryName == null){
            throw new RuntimeException("ko dc de trong");
        }
        Category category = new Category();
        category.setCategoryName(categoryRequest.getCategoryName());
        categoryRepository.save(category);
    }

    public Category updateCategory(Integer id, CategoryRequest categoryRequest) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        category.setCategoryName(categoryRequest.getCategoryName());
        return categoryRepository.save(category);
    }
    public void deleteCategory(Integer id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        categoryRepository.delete(category);
    }
}