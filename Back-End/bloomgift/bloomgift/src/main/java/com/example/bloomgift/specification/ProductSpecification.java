package com.example.bloomgift.specification;

import java.util.Date;
import java.util.Locale.Category;

import org.springframework.data.jpa.domain.Specification;

import com.example.bloomgift.model.Product;
import com.example.bloomgift.model.Promotion;
import com.example.bloomgift.model.Store;

import jakarta.persistence.criteria.Join;

public class ProductSpecification {
    public static Specification<Product> hasDescriptionProduct(String descriptionProduct) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("descriptionProduct"),
                descriptionProduct);
    }

    public static Specification<Product> hasColourProduct(String colourProduct) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("colourProduct"),
                colourProduct);
    }

    public static Specification<Product> hasPriceProduct(Float priceProduct) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("priceProduct"),
                priceProduct);
    }

    public static Specification<Product> hasCategoryName(String categoryName) {
        return (root, query, criteriaBuilder) -> {
            Join<Product, Category> categoryJoin = root.join("categoryID");
            return criteriaBuilder.equal(categoryJoin.get("categoryName"),
                    categoryName);
        };
    }

    public static Specification<Product> hasProductName(String productName) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("productName"),
                productName);
    }

    public static Specification<Product> hasSizeProduct(Integer sizeProduct) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("sizeProduct"),
                sizeProduct);
    }

    public static Specification<Product> hasCreateDate(Date createDate) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("createDate"),
                createDate);
    }

    public static Specification<Product> hasStoreName(String storeName) {
        return (root, query, criteriaBuilder) -> {
            Join<Product, Store> storeJoin = root.join("storeID");
            return criteriaBuilder.equal(storeJoin.get("storeName"), storeName);
        };
    }

}
