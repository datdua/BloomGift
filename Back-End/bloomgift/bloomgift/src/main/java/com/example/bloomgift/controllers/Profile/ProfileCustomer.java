package com.example.bloomgift.controllers.Profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.bloomgift.reponse.ProfileReponse;
import com.example.bloomgift.request.ProfileRequest;
import com.example.bloomgift.service.ProfileService;

import io.jsonwebtoken.io.IOException;

@RestController
@RequestMapping("/api/customer/profile")
public class ProfileCustomer {

    @Autowired
    private ProfileService profileService;

    @PostMapping(value = "/upload-avatar/{accountId}", consumes = "multipart/form-data")
    public ResponseEntity<String> uploadAvatar(@PathVariable Integer accountId,
            @RequestParam("file") MultipartFile file) {
        try {
            profileService.updateAvatar(accountId, file);
            return ResponseEntity.ok("Avatar updated successfully");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to upload avatar: " + e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("view-profile/{accountID}")
    public ResponseEntity<ProfileReponse> getProfile(@PathVariable int accountID) {
        try {
            ProfileReponse profileReponse = profileService.getProfileReponse(accountID);
            return new ResponseEntity<>(profileReponse, HttpStatus.OK);
        } catch (Exception e) {
            // Handle the exception (e.g., profile not found)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
     @PutMapping("update-profile/{accountID}")
    public ResponseEntity<ProfileReponse> updateProfile(
            @PathVariable int accountID,
            @RequestBody ProfileRequest profileRequest) {
        try {
            ProfileReponse updatedProfile = profileService.updateProfile(accountID, profileRequest);
            return new ResponseEntity<>(updatedProfile, HttpStatus.OK);
        } catch (Exception e) {
            // Handle the exception (e.g., account not found)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
