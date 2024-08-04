package com.example.bloomgift.service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;
import java.time.Duration;
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
import com.example.bloomgift.utils.EmailUtil;
import com.example.bloomgift.utils.JwtUtil;
import com.example.bloomgift.utils.OtpUtil;

import jakarta.mail.MessagingException;


@Service
public class AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AccountService accountService;

    @Autowired
    private JwtUtil jwtUtil;

     @Autowired
    private OtpUtil otpUtil;


    @Autowired
    private EmailUtil emailUtil;


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
        String otp = otpUtil.generateOtp();
        try{
            emailUtil.sendOtpEmail(registerRequest.getEmail(), otp);
        }catch(MessagingException e){
            throw new RuntimeException("unble");
        }
        
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

    public String verifyAccount(String email,String otp){
        Account account = accountRepository.findByEmail(email);
           
        if(account.getOtp().equals(otp) && Duration.between(account.getOtp_generated_time(),
                     LocalDateTime.now()).getSeconds()<(1* 60)){
                        account.setActive(true);
                        accountRepository.save(account);
                        return "OTP verify you can login";
                     }

        return "please regenerate otp and try again";

    }



    public String generateOtp(String email){
        Account account = accountRepository.findByEmail(email);
           
        String otp = otpUtil.generateOtp();
        try{
            emailUtil.sendOtpEmail(email, otp);
        }catch(MessagingException e){
            throw new RuntimeException("unble");
        }
        account.setOtp(otp);
        account.setOtp_generated_time(LocalDateTime.now());
        accountRepository.save(account);
        return "Email sent ..... please veryfi account within 1 minute";
    }



    public String forgotPassword(String email){
        Account account =  accountRepository.findByEmail(email);
            try{
        emailUtil.sendSetPasswordEmail(email);
            }catch (MessagingException e){
                throw new RuntimeException("unble");
            }
            return "Please check your email to set new password";
    }



    public String setPassword(String email,String newPassword){
         Account account = accountRepository.findByEmail(email);
         account.setPassword(newPassword);
         accountRepository.save(account);
         return "new password set successfull";
    }
}
