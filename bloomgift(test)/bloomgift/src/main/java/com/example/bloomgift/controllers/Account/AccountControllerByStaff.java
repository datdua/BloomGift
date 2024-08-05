package com.example.bloomgift.controllers.Account;

import java.sql.Date;
import java.util.Collections;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bloomgift.DTO.StaffPublicInfoDTO;
import com.example.bloomgift.model.Account;
import com.example.bloomgift.repository.AccountRepository;
import com.example.bloomgift.request.AccountRequest;
import com.example.bloomgift.service.AccountService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/api/staff-manager/accounts")
public class AccountControllerByStaff {
    
    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountRepository accountRepository;

    
    @PutMapping(value = "/edit-profile/{id}", produces = "application/json;charset=UTF-8")
    public ResponseEntity<?> editProfile(@PathVariable Integer id, @RequestBody AccountRequest accountRequest) {
        try {
            Account account = accountService.editAccountProfile(id, accountRequest);
            return ResponseEntity.ok(Collections.singletonMap("message", "Cập nhật thành công"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("message", e.getMessage()));
        }
    }

    @GetMapping("/get-by-name/{accountName}")
    public ResponseEntity<Account> getByAccountName(@PathVariable String accountName) {
        Account account = accountRepository.findByFullname(accountName);
        if (account == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(account);
    }


    @GetMapping("/get-by-email/{email}")
    public ResponseEntity<?> getByAccountEmail(@PathVariable String email) {
        Account account = accountRepository.findByEmail(email);
        if (account == null) {
            return ResponseEntity.ok(account);
        } else {
            return ResponseEntity.badRequest().body(Collections.singletonMap("message", "Không tìm thấy tài khoản"));
        }
    }


    @GetMapping("/show-information-staff/{id}")
    public ResponseEntity<?> getContactInfoByAccountID(@PathVariable Integer id) {
        Optional<Account> accountOpt = accountRepository.findById(id);

        if (!accountOpt.isPresent()) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("message", "Không tìm thấy tài khoản"));
        }

        Account account = accountOpt.get();
        StaffPublicInfoDTO staffPublicInfoDTO = new StaffPublicInfoDTO(
            account.getPhone(),
            account.getAddress(),
            account.getEmail(),
            account.getBirthday(),
            account.getFullname(),
            account.isActive()
        );

        return ResponseEntity.ok(staffPublicInfoDTO);
    }
         
         
        


}
