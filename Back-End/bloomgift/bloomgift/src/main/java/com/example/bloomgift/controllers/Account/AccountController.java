package com.example.bloomgift.controllers.Account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.bloomgift.reponse.AccountReponse;
import com.example.bloomgift.service.AccountService;

@RestController
@RequestMapping("/api/customer")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/list-all-account")
    public ResponseEntity<Page<AccountReponse>> getAllAccounts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<AccountReponse> accountReponse = accountService.getAllAccounts(pageable);
        return new ResponseEntity<>(accountReponse, HttpStatus.OK);
    }

}
