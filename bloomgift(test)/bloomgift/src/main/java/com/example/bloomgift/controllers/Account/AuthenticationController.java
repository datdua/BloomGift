package com.example.bloomgift.controllers.Account;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bloomgift.reponse.AuthenticationResponse;
import com.example.bloomgift.request.LoginRequest;
import com.example.bloomgift.request.RegisterRequest;
import com.example.bloomgift.service.AccountService;
import com.example.bloomgift.service.AuthenticationService;
import com.example.bloomgift.utils.JwtUtil;

@RestController
@RequestMapping("/api/accounts")
public class AuthenticationController {
    // @Autowired
    // private AccountService accountService;
    @Autowired
    private AuthenticationService authenticationService;
    private AccountService accountService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
       
    }

    @PostMapping(value = "register", produces =
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

 
}
