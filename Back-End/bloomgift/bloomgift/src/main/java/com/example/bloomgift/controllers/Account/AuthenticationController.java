package com.example.bloomgift.controllers.Account;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.bloomgift.reponse.AuthenticationResponse;
import com.example.bloomgift.request.LoginRequest;
import com.example.bloomgift.request.RegisterRequest;
import com.example.bloomgift.service.AccountService;
import com.example.bloomgift.service.AuthenticationService;

@RestController
@RequestMapping("/api/auth")
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

    @PostMapping(value = "/register", produces = "application/json;charset=UTF-8")
    public ResponseEntity<Map<String, String>> registerAccount(@RequestBody RegisterRequest registerRequest) {
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
            @RequestParam String otp) {
        return new ResponseEntity<>(authenticationService.verifyAccount(email, otp), HttpStatus.OK);

    }

    @PutMapping("/regenetate-otp")
    public ResponseEntity<String> regenetateOtp(
            @RequestParam String email) {

        return new ResponseEntity<>(authenticationService.generateOtp(email), HttpStatus.OK);

    }

    @PostMapping("/forget-password")
    public ResponseEntity<Map<String, String>> forgetPassword(@RequestParam String email) {
        return ResponseEntity.ok(accountService.forgetPassword(email));
    }

    @PutMapping("/set-password")
    public ResponseEntity<Map<String, String>> setPassword(@RequestParam String email,
            @RequestParam String newPassword) {
        return ResponseEntity.ok(accountService.setPassword(email, newPassword));
    }

    @GetMapping("/signInWithGoogle")
    public ResponseEntity<Map<String, Object>> loginWithGoogle(OAuth2AuthenticationToken oAuth2AuthenticationToken) {
        Map<String, Object> attributes = authenticationService.loginWithGoogle(oAuth2AuthenticationToken);
        return ResponseEntity.ok(attributes);
    }
}
