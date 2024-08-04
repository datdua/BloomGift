package com.example.bloomgift.service;

import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.example.bloomgift.model.Account;
import com.example.bloomgift.model.Role;
import com.example.bloomgift.reponse.AuthenticationResponse;
import com.example.bloomgift.repository.AccountRepository;
import com.example.bloomgift.repository.RoleRepository;
import com.example.bloomgift.request.LoginRequest;
import com.example.bloomgift.request.RegisterRequest;
import com.example.bloomgift.utils.JwtUtil;


@Service
public class AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AccountService accountService;

    @Autowired
    private JwtUtil jwtUtil;

    

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private RoleRepository roleRepository;


    public AuthenticationResponse authenticate(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        UserDetails userDetails = accountService.loadUserByUsername(loginRequest.getEmail());
        String token = jwtUtil.generateToken(userDetails);
        return new AuthenticationResponse(token);
    }

    public Map<String, String> register(RegisterRequest registerRequest) {
        String fullname = registerRequest.getFullname();
        String password = registerRequest.getPassword();
        String email = registerRequest.getEmail();
        String address = registerRequest.getAddress();
        int phone = registerRequest.getPhone();
        java.util.Date birthday = registerRequest.getBirthday();

        if (!hasText(fullname) || !hasText(password) || !hasText(email) ||
                !hasText(address) || !hasInt(phone)) {
            throw new RuntimeException("Please provide all required information");
        }

        if (!email.matches("^[a-zA-Z0-9._%+-]+@gmail.com$") && !email.matches("^[a-zA-Z0-9._%+-]+@fpt.edu.vn$")) {
            throw new RuntimeException("Invalid email format");
        }

        Account existingAccount = accountRepository.findByEmail(email);
        if (existingAccount != null) {
            throw new RuntimeException("Account already exists");
        }
        Role roleid = roleRepository.findById(2).orElseThrow(() -> new RuntimeException("Role not found"));


        Account account = new Account();
        account.setRoleid(roleid);
        account.setFullname(fullname);
        account.setEmail(email);
        account.setAddress(address);
        account.setBirthday(birthday);
        account.setPhone(phone);
        account.setPassword(password);
        account.setActive(true);
        accountRepository.save(account);
        return Collections.singletonMap("message", "Registration successful");
    }

    private boolean hasText(String text) {
        return text != null && !text.trim().isEmpty();
    }

    private boolean hasInt(int number) {
        return number > 0;
    }
}
