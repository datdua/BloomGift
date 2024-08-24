package com.example.bloomgift.controllers.Account;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.example.bloomgift.model.Account;
import com.example.bloomgift.repository.AccountRepository;
import com.example.bloomgift.request.AccountRequest;
import com.example.bloomgift.service.AccountService;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;



@RestController
@RequestMapping("/api/accounts")
public class AccountControllerByAdmin {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountService accountService;

    

    public AccountControllerByAdmin(AccountRepository accountRepository, AccountService accountService) {
        this.accountRepository = accountRepository;
        this.accountService = accountService;
    }



    @PostMapping(value ="/create-account", produces =
    "application/json;charset=UTF-8")
    public  ResponseEntity<Map<String, String>> createAccountbyAdmin(
            @RequestBody AccountRequest accountRequest) {
                try{
                    accountService.createAccount(accountRequest);
                    return ResponseEntity.ok(Collections.singletonMap("message", "Tạo tài khoản thành công"));
                }catch(Exception e){
                    return ResponseEntity.badRequest().body(Collections.singletonMap("message", e.getMessage()));
                }
   
    }

  
    // @GetMapping("/list-all-account")
    // public List<Account> getAllAccounts() {
    //     return accountService.getAllAccounts();
    // }

  
    @GetMapping("/list-all-account")
    public ResponseEntity<Page<Account>> getAllAccounts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Account> accounts = accountService.getAllAccounts(pageable);
        if (accounts.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(accounts);
    }
    
    @GetMapping("/{accountID}")
    public ResponseEntity<Account> getAccountById(@PathVariable("accountID") int accountID) {
        Optional<Account> account = accountService.getAccountById(accountID);
        return account.map(ResponseEntity::ok)
                      .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @PutMapping(value = "/update-account/{accountID}",
            produces = "application/json;charset=UTF-8")
    public ResponseEntity<?> updateAccount(
        @PathVariable Integer accountID,    
        @RequestBody AccountRequest accountRequest) {
        try{
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

    @GetMapping("/get-paging")
    public ResponseEntity<Page<Account>> getAllAccountsPaged(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {
        PageRequest pageable = PageRequest.of(page - 1, size);
        Page<Account> pageAccount = accountRepository.findAll(pageable);
        return ResponseEntity.ok(pageAccount);
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchAccounts(
            @RequestParam(required = false) Integer accountID,
            @RequestParam(required = false) String text,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date birthday,
            @RequestParam(required = false) Integer phone,
            @RequestParam(required = false) String roleName) {

        List<Account> accounts = accountRepository.searchAccounts(accountID, text, birthday, phone, roleName);

        if (accounts.isEmpty()) {
            return ResponseEntity.noContent().build(); // No content if no accounts found
        }

        return ResponseEntity.ok(accounts);
    }

}
