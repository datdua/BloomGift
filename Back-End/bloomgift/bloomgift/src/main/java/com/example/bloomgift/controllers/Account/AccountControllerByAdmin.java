package com.example.bloomgift.controllers.Account;

import java.util.Collections;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.HttpStatus;

import com.example.bloomgift.model.Account;
import com.example.bloomgift.model.Category;
import com.example.bloomgift.reponse.AccountReponse;
import com.example.bloomgift.repository.AccountRepository;
import com.example.bloomgift.request.AccountRequest;
import com.example.bloomgift.request.CategoryRequest;
import com.example.bloomgift.service.AccountService;
import com.example.bloomgift.service.CategoryService;
import com.example.bloomgift.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/accounts-management")
public class AccountControllerByAdmin {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    public AccountControllerByAdmin(AccountRepository accountRepository, AccountService accountService) {
        this.accountRepository = accountRepository;
        this.accountService = accountService;
    }

    // @PostMapping(value = "/create-account", produces =
    // "application/json;charset=UTF-8")
    // public ResponseEntity<Map<String, String>> createAccountbyAdmin(
    // @RequestBody AccountRequest accountRequest) {
    // try {
    // accountService.createAccount(accountRequest);
    // return ResponseEntity.ok(Collections.singletonMap("message", "Tạo tài khoản
    // thành công"));
    // } catch (Exception e) {
    // return ResponseEntity.badRequest().body(Collections.singletonMap("message",
    // e.getMessage()));
    // }

    // }

    @PostMapping(value = "/create-account", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> createAccount(
            @RequestParam("accountRequest") String accountRequestJson,
            @RequestParam("avatarFile") MultipartFile avatarFile) {

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            AccountRequest accountRequest = objectMapper.readValue(accountRequestJson,
                    AccountRequest.class);
            accountService.createAccount(accountRequest, avatarFile);
            return ResponseEntity.ok("Account created successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " +
                    e.getMessage());
        }
    }

    // @GetMapping("/list-all-account")
    // public ResponseEntity<List<AccountReponse>> getAllAccounts() {
    // List<AccountReponse> accountReponse = accountService.getAllAccounts();
    // return new ResponseEntity<>(accountReponse, HttpStatus.OK);
    // }
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

    @PutMapping(value = "/update-account/{accountID}", produces = "application/json;charset=UTF-8")
    public ResponseEntity<?> updateAccount(
            @PathVariable Integer accountID,
            @RequestBody AccountRequest accountRequest) {
        try {
            Account account = accountService.updateAccount(accountID, accountRequest);
            return ResponseEntity.ok(Collections.singletonMap("message", "Cập nhật thành công"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("message", e.getMessage()));
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
