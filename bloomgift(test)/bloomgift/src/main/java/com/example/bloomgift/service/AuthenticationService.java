package com.example.bloomgift.service;

import java.lang.StackWalker.Option;
import java.sql.Date;
import java.util.Collections;
import java.util.Optional;

 import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.bloomgift.model.Account;
import com.example.bloomgift.reponse.AuthenticationResponse;
import com.example.bloomgift.repository.AccountRepository;
import com.example.bloomgift.request.LoginRequest;
import com.example.bloomgift.request.RegisterRequest;
import com.example.bloomgift.utils.JwtUtil;

import lombok.var;

@Service
public class AuthenticationService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AccountServiece accountServiece;

    @Autowired 
    private JwtUtil jwtTokenUtil;

    @Autowired 
    private PasswordEncoder passwordEncoder;

    @Autowired 
    private AccountRepository accountRepository;

    
    public AuthenticationService(AccountRepository accountRepository,AuthenticationManager authenticationManager, AccountServiece accountServiece, JwtUtil jwtTokenUtil, PasswordEncoder  passwordEncoder){
        this.authenticationManager = authenticationManager;
        this.accountServiece = accountServiece;
        this.jwtTokenUtil = jwtTokenUtil;
        this.passwordEncoder = passwordEncoder;
        this.accountRepository = accountRepository;
    }
    // public ResponseEntity<?> authenticate(LoginRequest loginRequest) throws Exception {
    //     try {
    //         org.springframework.security.core.Authentication authentication = authenticationManager.authenticate(
    //             new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
    //         );
    //         final UserDetails userDetails = accountServiece.loadUserByEmail(loginRequest.getEmail());
    //         final Account account = accountServiece.findByEmail(loginRequest.getEmail());
    //         if (account == null || !account.isActive()){
    //             return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Collections.singletonMap("message","dang nhap ko dc"));

    //         }else{
    //             final String jwt = jwtTokenUtil.generateToken(account);
    //             return ResponseEntity.ok(new AuthenticationResponse(jwt));
    //         }
    //     }catch(Exception e){
    //         return ResponseEntity.status(401).body(Collections.singletonMap("error","sai cmnr"));
    //     }
    
    // }
    public ResponseEntity<?> authenticate(LoginRequest loginRequest) throws Exception{
        try {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), 
                                                    loginRequest.getPassword())
        );
         } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body(Collections.singletonMap("message", "loi"));
        }
       
        Account accunt = accountRepository.findByEmail(loginRequest.getEmail()).orElseThrow();
        var token = jwtTokenUtil.generateToken(accunt);
        // return new AuthenticationResponse(token);
        // return new AuthenticationResponse().builder()
        // .token(token)
        // .build();
        return ResponseEntity.ok(new AuthenticationResponse(token));
    }
 
    public Map<String, String>  register(RegisterRequest registerRequest ){
        String fullname = registerRequest.getFullname();
        
        String password = registerRequest.getPassword();
        String email = registerRequest.getEmail();
        String address = registerRequest.getAddress();
        int phone = registerRequest.getPhone();
        java.util.Date brithday = registerRequest.getBirthday();
        
        
        if (!hasText(fullname) || !hasText(password) || !hasText(email) ||
        !hasText(address) || !hasInt(phone)) {
          throw new RuntimeException("Vui lòng nhập đầy đủ thông tin");
        }

        if (!email.matches("^[a-zA-Z0-9._%+-]+@gmail.com$") && !email.matches("^[a-zA-Z0-9._%+-]+@fpt.edu.vn$")) {
            throw new RuntimeException("Email không hợp lệ");
        }
         Optional<Account> existingAccount = accountRepository.findByEmail(email);
        if (existingAccount.isPresent()) {
            throw new RuntimeException("Tài khoản đã tồn tại");
        }
        Account account = new Account();
        account.setRoleid(1);
        account.setFullname(fullname);
        account.setEmail(email);
        account.setAddress(address);
        account.setBirthday(brithday);
        account.setPhone(phone);
        account.setPassword(passwordEncoder.encode(password));
        account.setActive(true);
        account = accountRepository.save(account);
        // var token = jwtTokenUtil.generateToken(account);
        // return new AuthenticationResponse().builder()
        //     .token(token)
        //     .build();
        return Collections.singletonMap("message", "thanh cong");
    }
     private boolean hasText(String text) {
        return text != null && !text.trim().isEmpty();
    }
    // private boolean hasDate(Date date){
    //     return date != null ;

    // }
    private boolean hasInt(int number){
        return number > 0 ;
    }


}