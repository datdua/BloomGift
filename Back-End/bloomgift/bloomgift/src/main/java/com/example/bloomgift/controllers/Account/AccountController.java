package com.example.bloomgift.controllers.Account;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.bloomgift.model.Account;
import com.example.bloomgift.reponse.AccountReponse;
import com.example.bloomgift.request.AccountRequest;
import com.example.bloomgift.service.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;

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

    @PutMapping(value = "/update/{accountID}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateAccount(
            @PathVariable Integer accountID,
            @RequestPart("accountRequest") String accountRequestJson,
            @RequestPart(value = "avatarFile", required = false) MultipartFile avatarFile) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            AccountRequest accountRequest = objectMapper.readValue(accountRequestJson, AccountRequest.class);
            Account updatedAccount = accountService.updateAccount(accountID, accountRequest, avatarFile);
            return ResponseEntity.ok("Cập nhật tài khoản thành công");
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Invalid request data" + e.getMessage());
        }
    }

}
