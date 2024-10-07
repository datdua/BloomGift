package com.example.bloomgift.controllers.Account;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.bloomgift.model.Account;
import com.example.bloomgift.model.Category;
import com.example.bloomgift.reponse.AccountReponse;
import com.example.bloomgift.repository.AccountRepository;
import com.example.bloomgift.request.AccountRequest;
import com.example.bloomgift.request.CategoryRequest;
import com.example.bloomgift.service.AccountService;
import com.example.bloomgift.service.CategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api/admin/accounts-management")
public class AccountControllerByAdmin {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountService accountService;

    // @Autowired
    // private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    public AccountControllerByAdmin(AccountRepository accountRepository, AccountService accountService) {
        this.accountRepository = accountRepository;
        this.accountService = accountService;
    }

    @GetMapping("/list-all-account")
    public ResponseEntity<Page<AccountReponse>> getAllAccounts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<AccountReponse> accountReponse = accountService.getAllAccounts(pageable);
        return new ResponseEntity<>(accountReponse, HttpStatus.OK);
    }

    @GetMapping("/{accountID}")
    public ResponseEntity<AccountReponse> getAccountById(@PathVariable int accountID) {
        AccountReponse accountReponse = accountService.getAccountById(accountID);
        return new ResponseEntity<>(accountReponse, HttpStatus.OK);
    }

    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createAccount(
            @RequestPart("accountRequest") String accountRequestJson,
            @RequestPart(value = "avatarFile", required = false) MultipartFile avatarFile) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            AccountRequest accountRequest = objectMapper.readValue(accountRequestJson, AccountRequest.class);
            accountService.createAccount(accountRequest, avatarFile);
            return ResponseEntity.ok(Collections.singletonMap("message", "Tài khoản đã được tạo thành công"));
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Invalid request data");
        }
    }

    @PutMapping(value = "/update/{accountID}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateAccount(
            @PathVariable Integer accountID,
            @RequestPart("accountRequest") String accountRequestJson,
            @RequestPart(value = "avatarFile", required = false) MultipartFile avatarFile) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            AccountRequest accountRequest = objectMapper.readValue(accountRequestJson, AccountRequest.class);
            Account updatedAccount = accountService.updateAccount(accountID, accountRequest, avatarFile);
            return ResponseEntity.ok(updatedAccount);
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Invalid request data");
        }
    }

    @DeleteMapping("/{accountID}")
    public ResponseEntity<String> deleteAccount(@PathVariable("accountID") Integer accountID) {
        try {
            accountService.deleteAccount(accountID);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Account deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }

    @PostMapping(value = "/create-category", produces = "application/json;charset=UTF-8")
    public Category createCategory(@RequestBody CategoryRequest categoryRequest) {
        return categoryService.createCategory(categoryRequest);
    }
}
