package com.example.bloomgift.controllers.Account;

import java.util.Collections;
// import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// import com.example.bloomgift.reponse.AuthenticationResponse;
import com.example.bloomgift.request.LoginRequest;
import com.example.bloomgift.request.RegisterRequest;
// import com.example.bloomgift.service.AccountServiece;
import com.example.bloomgift.service.AuthenticationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/accounts")
public class AuthenticationController {
    // @Autowired
    // private AccountServiece accountServiece;
    @Autowired
    private AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }
    
    // @PostMapping(value = "/api/auth/register", produces = "application/json;charset=UTF-8")
    // public ResponseEntity<Map<String, String>> registerAccount(@RequestBody RegisterRequest registerRequest) {
    //     Map<String, String> response = accountServiece.register(registerRequest);
    //     return ResponseEntity.ok(response);
    // }
    @PostMapping(value="/api/auth/register", produces = "application/json;charset=UTF-8")
    public ResponseEntity<?> register(
        @RequestBody RegisterRequest registerRequest
        ){
            try{
            return ResponseEntity.ok(authenticationService.register(registerRequest));
            }catch(Exception e){
                return ResponseEntity.status(500).body(Collections.singletonMap("error", "Error"));
            }
    
        }
        
        
    
    
    
    @PostMapping(value = "/api/auth/login", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> login(
        @RequestBody LoginRequest loginRequest
        ) {
        try {
            return authenticationService.authenticate(loginRequest);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Collections.singletonMap("error", "Error"));
        }
    
        }       
}
