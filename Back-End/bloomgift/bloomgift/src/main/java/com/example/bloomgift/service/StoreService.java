package com.example.bloomgift.service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.bloomgift.model.Category;
import com.example.bloomgift.model.Role;
import com.example.bloomgift.model.Store;
import com.example.bloomgift.reponse.StoreResponse;
import com.example.bloomgift.repository.AccountRepository;
import com.example.bloomgift.repository.RoleRepository;
import com.example.bloomgift.repository.StoreRepository;
import com.example.bloomgift.request.StoreRequest;
import com.example.bloomgift.request.putRequest.StorePutRequest;
import com.example.bloomgift.specification.StoreSpecification;
import com.example.bloomgift.utils.EmailUtil;
import com.example.bloomgift.utils.OtpUtil;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.mail.MessagingException;

@Service
public class StoreService {

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private OtpUtil otpUtil;
    
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private EmailUtil emailUtil;

    private StoreResponse convertStoreToResponse(Store store) {
        StoreResponse storeResponse = new StoreResponse();
        storeResponse.setStoreID(store.getStoreID());
        storeResponse.setStoreName(store.getStoreName());
        storeResponse.setType(store.getType());
        storeResponse.setStorePhone(store.getStorePhone());
        storeResponse.setStoreAddress(store.getStoreAddress());
        storeResponse.setEmail(store.getEmail());
        storeResponse.setBankAccountName(store.getBankAccountName());
        storeResponse.setBankNumber(store.getBankNumber());
        storeResponse.setBankAddress(store.getBankAddress());
        storeResponse.setTaxNumber(store.getTaxNumber());
        storeResponse.setStoreStatus(store.getStoreStatus());
        storeResponse.setStoreAvatar(store.getStoreAvatar());
        storeResponse.setIdentityCard(store.getIdentityCard());
        storeResponse.setIdentityName(store.getIdentityName());
        storeResponse.setPassword(store.getPassword());
        storeResponse.setRoleName(store.getRole().getRoleName());

        return storeResponse;
    }

    public ResponseEntity<List<StoreResponse>> getAllStores() {
        List<Store> stores = storeRepository.findAll();
        List<StoreResponse> storeResponses = stores.stream().map(this::convertStoreToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(storeResponses);
    }

    public ResponseEntity<?> getStoreByName(String storeName) {
        Store store = storeRepository.findByStoreName(storeName);
        if (store != null) {
            return ResponseEntity.ok(convertStoreToResponse(store));
        } else {
            return ResponseEntity.badRequest().body("Không tìm thấy cửa hàng");
        }
    }

    public ResponseEntity<?> registerStore(StoreRequest storeRequest) {
        String otp = otpUtil.generateOtp();
        String Email = storeRequest.getEmail();
        if (!Email.matches("^[a-zA-Z0-9._%+-]+@gmail.com$")) {
            return ResponseEntity.badRequest().body("Email không hợp lệ");
        } else if (storeRepository.existsByEmail(Email) || accountRepository.existsByEmail(Email)) {
            return ResponseEntity.badRequest().body("Email đã tồn tại");
        } else {
            try {
                emailUtil.sendOtpEmail(Email, otp);
            } catch (MessagingException e) {
                return ResponseEntity.badRequest().body("Gửi email thất bại");
            }
        }

        Store store = new Store();
        store.setStoreName(storeRequest.getStoreName());
        store.setType(storeRequest.getType());
        store.setStoreAddress(storeRequest.getStoreAddress());
        store.setBankAccountName(storeRequest.getBankAccountName());
        store.setBankAddress(storeRequest.getBankAddress());
        store.setEmail(Email);

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
        store.setStoreStatus("Đang xử lý");
        store.setStoreAvatar(storeRequest.getStoreAvatar());
        store.setPassword(storeRequest.getPassword());
        store.setOtp(otp);
        store.setOtp_generated_time(LocalDateTime.now());

        Role roleID = roleRepository.findById(3).orElseThrow();
        store.setRole(roleID);

        storeRepository.save(store);
        return ResponseEntity.ok(Collections.singletonMap("message", "Gửi email xác thực thành công. Vui lòng kiểm tra email"));
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

        String Email = storePutRequest.getEmail();
        if (!Email.matches("^[a-zA-Z0-9._%+-]+@gmail\\.com$")) {
            return ResponseEntity.badRequest().body("Email không hợp lệ");
        }
        store.setEmail(Email);

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
            return ResponseEntity.badRequest().body("Số CCCD không hợp lệ");
        }
        store.setIdentityCard(identityCard);

        store.setIdentityName(storePutRequest.getIdentityName());
        store.setStoreStatus(storePutRequest.getStoreStatus());
        store.setStoreAvatar(storePutRequest.getStoreAvatar());

        

        Category category = new Category();
        category.setCategoryID(storePutRequest.getCategoryID());

        return ResponseEntity.ok(Collections.singletonMap("message", "Cập nhật cửa hàng thành công"));

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

    public ResponseEntity<?> getStoreById(Integer storeID) {
        Store store = storeRepository.findById(storeID).orElse(null);
        if (store != null) {
            return ResponseEntity.ok(convertStoreToResponse(store));
        } else {
            return ResponseEntity.badRequest().body("Không tìm thấy cửa hàng");
        }
    }

    public Page<StoreResponse> getAllStoreByPage(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Store> stores = storeRepository.findAll(pageable);
        return stores.map(this::convertStoreToResponse);
    }

    public Store findByEmail(String email) {
        return storeRepository.findByEmail(email);
    }

    public Page<StoreResponse> searchStoreWithFilterPage(
            String storeName,
            String type,
            String storePhone,
            String storeAddress,
            String email,
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
        if (email != null && !email.isEmpty()) {
            spec = spec.and(StoreSpecification.hasEmail(email));
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
        Page<Store> stores = storeRepository.findAll(spec, pageable);

        return stores.map(this::convertStoreToResponse);

    }

}