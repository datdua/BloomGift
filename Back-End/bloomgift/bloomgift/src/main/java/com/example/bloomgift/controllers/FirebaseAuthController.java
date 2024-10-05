package com.example.bloomgift.controllers;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/firebase")
public class FirebaseAuthController {

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String email, @RequestParam String password) {
        try {
            FirebaseAuth auth = FirebaseAuth.getInstance();
            UserRecord userRecord = auth.getUserByEmail(email);
            String token = FirebaseAuth.getInstance().createCustomToken(userRecord.getUid());
            return ResponseEntity.ok(token);

        } catch (FirebaseAuthException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Failed to login: " + e.getMessage());
        }
    }

    @GetMapping("/verifyToken")
    public ResponseEntity<String> verifyToken(@RequestParam String idToken) {
        try {
            FirebaseAuth auth = FirebaseAuth.getInstance();
            FirebaseToken decodedToken = auth.verifyIdToken(idToken);
            String uid = decodedToken.getUid();
            return ResponseEntity.ok("Token is valid. User ID: " + uid);
        } catch (FirebaseAuthException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token: " + e.getMessage());
        }
    }
}