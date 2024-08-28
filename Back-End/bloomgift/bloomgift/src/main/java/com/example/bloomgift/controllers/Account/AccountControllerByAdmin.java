package com.example.bloomgift.controllers.Account;

import java.util.Collections;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.example.bloomgift.model.Account;
import com.example.bloomgift.model.Category;
import com.example.bloomgift.reponse.AccountReponse;
import com.example.bloomgift.repository.AccountRepository;
import com.example.bloomgift.request.AccountRequest;
import com.example.bloomgift.request.CategoryRequest;
import com.example.bloomgift.service.AccountService;
import com.example.bloomgift.service.CategoryService;
import com.example.bloomgift.service.ProductService;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;



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
    // public ResponseEntity<List<AccountReponse>> getAllAccounts() {
    //     List<AccountReponse> accountReponse = accountService.getAllAccounts();
    //     return new ResponseEntity<>(accountReponse, HttpStatus.OK);
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


    
//     @GetMapping("/search")
//     public ResponseEntity<Page<AccountReponse>> getFilteredAccounts(
//         @RequestParam(required = false) String fullName,
//         @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date birthday,
//         @RequestParam(required = false) int accountID,
//         @RequestParam(required = false) String gender,
//         @RequestParam(required = false) int phone,
//         @RequestParam(required = false) String address,
//         @RequestParam(defaultValue = "0") int page,
//         @RequestParam(defaultValue = "10") int size) {
//     Pageable pageable = PageRequest.of(page, size);
//     Page<AccountReponse> accountReponse = accountService.getFilteredAccounts( address, birthday,fullName,pageable,accountID,  gender, phone);
//     return new ResponseEntity<>(accountReponse, HttpStatus.OK);
// }

      @PostMapping(value="/create-category", produces =
      "application/json;charset=UTF-8")
    public Category createCategory(@RequestBody CategoryRequest categoryRequest) {
        return categoryService.createCategory(categoryRequest);
    }
}
