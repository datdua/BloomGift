package com.example.bloomgift.controllers.Account;

import java.util.Collections;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bloomgift.reponse.AuthenticationResponse;
import com.example.bloomgift.request.LoginRequest;
import com.example.bloomgift.request.RegisterRequest;
import com.example.bloomgift.service.AccountServiece;
import com.example.bloomgift.service.AuthenticationService;

@RestController
@RequestMapping("/api/accounts")
public class AuthenticationController {
    // @Autowired
    // private AccountServiece accountServiece;
    @Autowired
    private AuthenticationService authenticationService;
    private AccountServiece accountServiece;

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
    // @PostMapping("/register")
    // public ResponseEntity<AuthenticationResponse> register(
    //         @RequestBody RegisterRequest registerRequest) {
        
    //         return ResponseEntity.ok(authenticationService.register(registerRequest));
        

    // }

    // @PostMapping("login")
    // public ResponseEntity<?> login(
    //         @RequestBody LoginRequest loginRequest) {
    //     try {
    //         return authenticationService.authenticate(loginRequest);
    //     } catch (Exception e) {
    //         return ResponseEntity.status(500).body(Collections.singletonMap("error", "Error"));
    //     }

    // }
    @PostMapping("login")
    public ResponseEntity<?> login (
        @RequestBody LoginRequest loginRequest)  {
            try {   
            return authenticationService.authenticate(loginRequest);
        } catch (Exception e) {

        }     return ResponseEntity.status(500).body(Collections.singletonMap("error", "Error"));
        
    }

 
}
