package com.example.bloomgift.controllers.Account;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.example.bloomgift.model.Account;
import com.example.bloomgift.reponse.AuthenticationResponse;
import com.example.bloomgift.request.LoginRequest;
import com.example.bloomgift.request.RegisterRequest;
import com.example.bloomgift.service.AccountService;
import com.example.bloomgift.service.AuthenticationService;


@RestController
@RequestMapping("/api/accounts")
public class AuthenticationController {
    // @Autowired
    // private AccountService accountService;
    @Autowired
    private AuthenticationService authenticationService;


    @Autowired
    private AccountService accountService;

 

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
       
    }

    @PostMapping(value = "/register", produces =
    "application/json;charset=UTF-8")
    public ResponseEntity<Map<String, String>> registerAccount(@RequestBody
    RegisterRequest registerRequest) {
    Map<String, String> response = authenticationService.register(registerRequest);
    return ResponseEntity.ok(response);
    }
    
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginRequest loginRequest) {
        AuthenticationResponse response = authenticationService.authenticate(loginRequest);
        return ResponseEntity.ok(response);
    }
    
    @PutMapping("/verify-account")
    public ResponseEntity<String> verifyAccount(
        @RequestParam String email,
        @RequestParam String otp){
            return new ResponseEntity<>(authenticationService.verifyAccount(email,otp),HttpStatus.OK);

    }
    
    @PutMapping("/regenetate-otp")
    public ResponseEntity<String>  regenetateOtp(
            @RequestParam String email){

            return new ResponseEntity<>(authenticationService.generateOtp(email),HttpStatus.OK);

    }

    @PutMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(
            @RequestParam String email
    ){
        return new ResponseEntity<>(authenticationService.forgotPassword(email),HttpStatus.OK);
    }

    

    @PutMapping("/set-password")
    public ResponseEntity<String> setPassword(
            @RequestParam String email,
            @RequestParam String newPassword 
    ){
        return new ResponseEntity<>(authenticationService.setPassword(email,newPassword),HttpStatus.OK);
   }

   @GetMapping("/{id}")
   public ResponseEntity<Account> getAccountById(@PathVariable("id") int id) {
       Optional<Account> account = accountService.getAccountById(id);
       return account.map(ResponseEntity::ok)
                     .orElseGet(() -> ResponseEntity.notFound().build());
   }

 

}
