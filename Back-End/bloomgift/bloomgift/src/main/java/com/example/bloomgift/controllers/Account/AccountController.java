package com.example.bloomgift.controllers.Account;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bloomgift.model.Account;
import com.example.bloomgift.reponse.AccountReponse;
import com.example.bloomgift.request.AccountRequest;
import com.example.bloomgift.service.AccountService;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

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

}
