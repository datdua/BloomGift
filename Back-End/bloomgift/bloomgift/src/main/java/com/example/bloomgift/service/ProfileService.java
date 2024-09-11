package com.example.bloomgift.service;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.bloomgift.model.Account;
import com.example.bloomgift.reponse.ProfileReponse;
import com.example.bloomgift.repository.AccountRepository;
import com.example.bloomgift.request.ProfileRequest;

@Service
public class ProfileService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private FirebaseStorageService firebaseStorageService;

    public ProfileReponse getProfileReponse(int accountID) {
        Account account = accountRepository.findById(accountID).orElseThrow();
        return new ProfileReponse(
                account.getAccountID(),
                account.getFullname(),
                account.getEmail(),
                account.getPassword(),
                account.getAddress(),
                account.getGender(),
                account.getAvatar(),
                account.getBirthday(),
                account.getPhone());
    }
      public ProfileReponse updateProfile(int accountID, ProfileRequest profileRequest) {
        Account account = accountRepository.findById(accountID)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        // Update account fields
        account.setFullname(profileRequest.getFullname());
        account.setEmail(profileRequest.getEmail());
        account.setPassword(profileRequest.getPassword()); // Consider hashing password
        account.setAddress(profileRequest.getAddress());
        account.setGender(profileRequest.getGender());
        account.setBirthday(profileRequest.getBirthday());
        account.setPhone(profileRequest.getPhone());

        // Save updated account
        account = accountRepository.save(account);

        return new ProfileReponse(
                account.getAccountID(),
                account.getFullname(),
                account.getEmail(),
                account.getPassword(), // Be cautious about exposing password
                account.getAddress(),
                account.getGender(),
                account.getAvatar(),
                account.getBirthday(),
                account.getPhone());
    }

    public void updateAvatar(int accountID, MultipartFile file) {
        Optional<Account> optionalAccount = accountRepository.findById(accountID);
        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();
            String oldAvatarUrl = account.getAvatar();
            if (oldAvatarUrl != null && !oldAvatarUrl.isEmpty()) {
                String oldFilePath = oldAvatarUrl.substring(oldAvatarUrl.lastIndexOf("/")+1);
                firebaseStorageService.deleteFile(oldFilePath);
            }
            try {
                String newAvatarUrl = firebaseStorageService.uploadFileByCustomer(file, account.getEmail());
                account.setAvatar(newAvatarUrl);
                accountRepository.save(account);
            } catch (IOException e) {
                throw new RuntimeException("Failed to upload new avatar", e);
            }
        } else {
            throw new RuntimeException("Account not found");

        }

    }
    

}
