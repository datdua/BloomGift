package com.example.bloomgift.specification;

import org.springframework.data.jpa.domain.Specification;

import com.example.bloomgift.model.Store;

public class StoreSpecification{
    public static Specification<Store> hasStoreName(String storeName) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("storeName"), storeName);
    }

    public static Specification<Store> hasType(String type) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("type"), type);
    }

    public static Specification<Store> hasStorePhone(String storePhone) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("storePhone"), storePhone);
    }

    public static Specification<Store> hasStoreAddress(String storeAddress) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("storeAddress"), storeAddress);
    }

    public static Specification<Store> hasStoreEmail(String storeEmail) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("storeEmail"), storeEmail);
    }

    public static Specification<Store> hasBankAccountName(String bankAccountName) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("bankAccountName"), bankAccountName);
    }

    public static Specification<Store> hasBankNumber(String bankNumber) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("bankNumber"), bankNumber);
    }

    public static Specification<Store> hasBankAddress(String bankAddress) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("bankAddress"), bankAddress);
    }

    public static Specification<Store> hasTaxNumber(String taxNumber) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("taxNumber"), taxNumber);
    }

    public static Specification<Store> hasStoreStatus(String storeStatus) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("storeStatus"), storeStatus);
    }

    public static Specification<Store> hasStoreAvatar(String storeAvatar) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("storeAvatar"), storeAvatar);
    }

    public static Specification<Store> hasIdentityCard(String identityCard) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("identityCard"), identityCard);
    }

    public static Specification<Store> hasIdentityName(String identityName) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("identityName"), identityName);
    }

    public static Specification<Store> hasAccountFullName(String fullName) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("account").get("fullName"), fullName);
    }

    public static Specification<Store> hasCategoryName(String categoryName) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("category").get("categoryName"),
                categoryName);
    }
}