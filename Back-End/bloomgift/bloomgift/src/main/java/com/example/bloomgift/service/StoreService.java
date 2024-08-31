    package com.example.bloomgift.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.bloomgift.model.Account;
import com.example.bloomgift.model.Category;
import com.example.bloomgift.model.Store;
import com.example.bloomgift.repository.StoreRepository;
import com.example.bloomgift.request.StoreRequest;
import com.example.bloomgift.request.putRequest.StorePutRequest;
import com.example.bloomgift.specification.StoreSpecification;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@Service
public class StoreService {

    @Autowired
    private StoreRepository storeRepository;

    public List<Store> getAllStores() {
        return storeRepository.findAll();
    }

    public Store getStoreByName(String storeName) {
        return storeRepository.findByStoreName(storeName);
    }

    public ResponseEntity<?> addStore(StoreRequest storeRequest) {
        Store store = new Store();
        store.setStoreName(storeRequest.getStoreName());
        store.setType(storeRequest.getType());
        store.setStoreAddress(storeRequest.getStoreAddress());
        store.setBankAccountName(storeRequest.getBankAccountName());
        store.setBankAddress(storeRequest.getBankAddress());

        String storeEmail = storeRequest.getStoreEmail();
        if (!storeEmail.contains("^[a-zA-Z0-9._%+-]+@gmail.com$")) {
            return ResponseEntity.badRequest().body("Email không hợp lệ");
        }
        store.setStoreEmail(storeEmail);

        String storePhone = storeRequest.getStorePhone();
        if (storePhone.length() != 10) {
            return ResponseEntity.badRequest().body("Số điện thoại không hợp lệ");
        }
        store.setStorePhone(storePhone);

        String bankNumber = storeRequest.getBankNumber();
        if (bankNumber.length() < 10 || bankNumber.length() > 20) {
            return ResponseEntity.badRequest().body("Số tài khoản ngân hàng không hợp lệ");
        }
        store.setBankNumber(bankNumber);

        String taxNumber = storeRequest.getTaxNumber();
        if (taxNumber.length() < 10 || taxNumber.length() > 13) {
            return ResponseEntity.badRequest().body("Mã số thuế không hợp lệ");
        }
        store.setTaxNumber(taxNumber);

        String identityCard = storeRequest.getIdentityCard();
        if (identityCard.length() != 12) {
            return ResponseEntity.badRequest().body("Số CMND không hợp lệ");
        }
        store.setIdentityCard(identityCard);

        store.setIdentityName(storeRequest.getIdentityName());
        store.setStoreStatus("Chờ duyệt");
        store.setStoreAvatar(storeRequest.getStoreAvatar());

        Account account = new Account();
        account.setAccountID(storeRequest.getAccountID());
        store.setAccount(account);

        Category category = new Category();
        category.setCategoryID(storeRequest.getCategoryID());
        store.setCategory(category);

        storeRepository.save(store);
        return ResponseEntity.ok().body("Thêm cửa hàng thành công");
    }

    public ResponseEntity<?> updateStore(Integer storeID, StorePutRequest storePutRequest) {
        Store store = storeRepository.findById(storeID).orElseThrow();

        if (store == null) {
            return ResponseEntity.badRequest().body("Không tìm thấy cửa hàng");
        }

        store.setStoreName(storePutRequest.getStoreName());
        store.setType(storePutRequest.getType());
        store.setStoreAddress(storePutRequest.getStoreAddress());
        store.setBankAccountName(storePutRequest.getBankAccountName());
        store.setBankAddress(storePutRequest.getBankAddress());

        String storeEmail = storePutRequest.getStoreEmail();
        if (!storeEmail.contains("^[a-zA-Z0-9._%+-]+@gmail.com$")) {
            return ResponseEntity.badRequest().body("Email không hợp lệ");
        }
        store.setStoreEmail(storeEmail);

        String storePhone = storePutRequest.getStorePhone();
        if (storePhone.length() != 10) {
            return ResponseEntity.badRequest().body("Số điện thoại không hợp lệ");
        }
        store.setStorePhone(storePhone);

        String bankNumber = storePutRequest.getBankNumber();
        if (bankNumber.length() < 10 || bankNumber.length() > 20) {
            return ResponseEntity.badRequest().body("Số tài khoản ngân hàng không hợp lệ");
        }
        store.setBankNumber(bankNumber);

        String taxNumber = storePutRequest.getTaxNumber();
        if (taxNumber.length() < 10 || taxNumber.length() > 13) {
            return ResponseEntity.badRequest().body("Mã số thuế không hợp lệ");
        }
        store.setTaxNumber(taxNumber);

        String identityCard = storePutRequest.getIdentityCard();
        if (identityCard.length() != 12) {
            return ResponseEntity.badRequest().body("Số CMND không hợp lệ");
        }
        store.setIdentityCard(identityCard);

        store.setIdentityName(storePutRequest.getIdentityName());
        store.setStoreStatus(storePutRequest.getStoreStatus());
        store.setStoreAvatar(storePutRequest.getStoreAvatar());

        Account account = new Account();
        account.setAccountID(storePutRequest.getAccountID());
        store.setAccount(account);

        Category category = new Category();
        category.setCategoryID(storePutRequest.getCategoryID());

        return ResponseEntity.ok().body("Cập nhật cửa hàng thành công");

    }

    public ResponseEntity<?> deleteStore(@RequestBody List<Integer> storeIDs) {
        // Filter existent storeIDs
        List<Integer> existingStoreIDs = storeIDs.stream()
                .filter(storeID -> storeRepository.existsById(storeID))
                .collect(Collectors.toList());

        // Filter non-existent storeIDs
        List<Integer> nonExistentStoreIDs = storeIDs.stream()
                .filter(storeID -> !storeRepository.existsById(storeID))
                .collect(Collectors.toList());

        if (existingStoreIDs.isEmpty()) {
            return ResponseEntity.badRequest().body("Không tìm thấy cửa hàng để xóa");
        } else {
            storeRepository.deleteAllById(existingStoreIDs);
            String message = "Xóa các cửa hàng thành công";
            if (!nonExistentStoreIDs.isEmpty()) {
                message += ". Các cửa hàng không tồn tại: " + nonExistentStoreIDs;
            }
            return ResponseEntity.ok().body(message);
        }
    }

    public Store getStoreById(Integer storeID) {
        return storeRepository.findById(storeID).orElseThrow();
    }

    public Page<Store> getAllStoreByPage(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return storeRepository.findAll(pageable);
    }

    public Page<Store> searchStoreWithFilterPage(
            String storeName,
            String type,
            String storePhone,
            String storeAddress,
            String storeEmail,
            String bankAccountName,
            String bankNumber,
            String bankAddress,
            String taxNumber,
            String storeStatus,
            String storeAvatar,
            String identityCard,
            String identityName,
            String accountFullName,
            String categoryName,
            Integer page,
            Integer size) {

        Specification<Store> spec = Specification.where(null);

        if (storeName != null && !storeName.isEmpty()) {
            spec = spec.and(StoreSpecification.hasStoreName(storeName));
        }
        if (type != null && !type.isEmpty()) {
            spec = spec.and(StoreSpecification.hasType(type));
        }
        if (storePhone != null && !storePhone.isEmpty()) {
            spec = spec.and(StoreSpecification.hasStorePhone(storePhone));
        }
        if (storeAddress != null && !storeAddress.isEmpty()) {
            spec = spec.and(StoreSpecification.hasStoreAddress(storeAddress));
        }
        if (storeEmail != null && !storeEmail.isEmpty()) {
            spec = spec.and(StoreSpecification.hasStoreEmail(storeEmail));
        }
        if (bankAccountName != null && !bankAccountName.isEmpty()) {
            spec = spec.and(StoreSpecification.hasBankAccountName(bankAccountName));
        }
        if (bankNumber != null && !bankNumber.isEmpty()) {
            spec = spec.and(StoreSpecification.hasBankNumber(bankNumber));
        }
        if (bankAddress != null && !bankAddress.isEmpty()) {
            spec = spec.and(StoreSpecification.hasBankAddress(bankAddress));
        }
        if (taxNumber != null && !taxNumber.isEmpty()) {
            spec = spec.and(StoreSpecification.hasTaxNumber(taxNumber));
        }
        if (storeStatus != null && !storeStatus.isEmpty()) {
            spec = spec.and(StoreSpecification.hasStoreStatus(storeStatus));
        }
        if (storeAvatar != null && !storeAvatar.isEmpty()) {
            spec = spec.and(StoreSpecification.hasStoreAvatar(storeAvatar));
        }
        if (identityCard != null && !identityCard.isEmpty()) {
            spec = spec.and(StoreSpecification.hasIdentityCard(identityCard));
        }
        if (identityName != null && !identityName.isEmpty()) {
            spec = spec.and(StoreSpecification.hasIdentityName(identityName));
        }
        if (accountFullName != null && !accountFullName.isEmpty()) {
            spec = spec.and(StoreSpecification.hasAccountFullName(accountFullName));
        }
        if (categoryName != null && !categoryName.isEmpty()) {
            spec = spec.and(StoreSpecification.hasCategoryName(categoryName));
        }

        Pageable pageable = PageRequest.of(page - 1, size);
        return storeRepository.findAll(spec, pageable);

    }

}
