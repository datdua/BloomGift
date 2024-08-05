package com.example.bloomgift.controllers.Account;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.bloomgift.DTO.AdminPublicInfoDTO;
import com.example.bloomgift.model.Account;
import com.example.bloomgift.model.Role;
import com.example.bloomgift.repository.AccountRepository;
import com.example.bloomgift.repository.RoleRepository;
import com.example.bloomgift.request.AccountRequest;
import com.example.bloomgift.service.AccountService;
import com.example.bloomgift.service.AuthenticationService;

@RestController
@RequestMapping("/api/admin-manger/accounts")
public class AccountControllerAdmin {
     @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountService accountService;


    @Autowired
    private RoleRepository roleRepository;

 
    @Autowired
    public AccountControllerAdmin(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
       
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


    @GetMapping("/list-all-account")
    public List<Account> getAllAccounts() {
        return accountService.getAllAccounts();
    }
    
    @PutMapping(value = "/update-account/{id}",
            produces = "application/json;charset=UTF-8")
    public ResponseEntity<?> updateAccount(
        @PathVariable Integer id,    
        @RequestBody AccountRequest accountRequest) {
        try{
            Account account = accountService.updateAccount(id, accountRequest);
            return ResponseEntity.ok(Collections.singletonMap("message", "Cập nhật thành công"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("message", e.getMessage()));
        }
   
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable("id") Integer id) {
        try {
            accountService.deleteAccount(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Account deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }



    @PutMapping(value = "/edit-profile/{id}", produces = "application/json;charset=UTF-8")
    public ResponseEntity<?> editProfile(@PathVariable Integer id, @RequestBody AccountRequest accountRequest) {
        try {
            Account account = accountService.editAccountProfile_admin(id, accountRequest);
            return ResponseEntity.ok(Collections.singletonMap("message", "Cập nhật thành công"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("message", e.getMessage()));
        }
    }
    


     @GetMapping("/show-information-admin/{accountID}")
    public ResponseEntity<?> getContactInfoByAccountID(@PathVariable Integer id) {
        Optional<Account> accountOpt = accountRepository.findById(id);

        if (!accountOpt.isPresent()) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("message", "Không tìm thấy tài khoản"));
        }

        Account account = accountOpt.get();
        AdminPublicInfoDTO adminPublicInfoDTO = new AdminPublicInfoDTO(      
            account.getEmail(),
            account.getFullname(),
            account.isActive()
        );

        return ResponseEntity.ok(adminPublicInfoDTO);
    }

        @GetMapping("/get-paging")
        public ResponseEntity<Page<Account>> getAllAccountsPaged(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Account> pageAccount = accountRepository.findAll(pageable);
        return ResponseEntity.ok(pageAccount);
    }


    @GetMapping("/get-all-role")
    public ResponseEntity<?> getAllRoles() {
        List<Role> roles = roleRepository.findAll();

        // Convert to a list of role names
        List<String> roleNames = roles.stream()
                                      .map(Role::getName)
                                      .collect(Collectors.toList());

        return ResponseEntity.ok(roleNames);
    }

    @GetMapping("/filter-by-role")
    public ResponseEntity<?> getAccountsByRole(@RequestParam String roleName) {
        List<Account> accounts = accountRepository.findByRoleid_Name(roleName);

        if (accounts.isEmpty()) {
            return ResponseEntity.noContent().build(); // No content if no accounts found
        }

        return ResponseEntity.ok(accounts);
    }
    

   
    @GetMapping("/search")
    public ResponseEntity<?> searchAccounts(
            @RequestParam(required = false) Integer id,
            @RequestParam(required = false) String text,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date birthday,
            @RequestParam(required = false) Integer phone,
            @RequestParam(required = false) String roleName) {

        List<Account> accounts = accountRepository.searchAccounts(id, text, birthday, phone, roleName);

        if (accounts.isEmpty()) {
            return ResponseEntity.noContent().build(); // No content if no accounts found
        }

        return ResponseEntity.ok(accounts);
    }

         
}
