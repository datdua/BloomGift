package com.example.bloomgift.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.time.Duration;

import javax.management.RuntimeErrorException;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties.Authentication;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
    private AccountRepository accountRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private AccountService accountService;

    @Autowired
    private OtpUtil otpUtil;

    @Autowired
    private EmailUtil emailUtil;
    
    public AuthenticationService(AccountRepository accountRepository, RoleRepository roleRepository,
            AuthenticationManager authenticationManager, JwtUtil jwtUtil, AccountService accountService) {
        this.accountRepository = accountRepository;
        this.roleRepository = roleRepository;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.accountService = accountService;
    }

    public AuthenticationResponse authenticate(LoginRequest loginRequest) {
        org.springframework.security.core.Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassrword()));

        UserDetails userDetails = accountService.loadUserByUsername(loginRequest.getEmail());
        Account account = accountRepository.findByEmail(loginRequest.getEmail());

        String role = account.getRoleID().getRoleName(); 

        String token = jwtUtil.generateToken(userDetails, role); 
        return new AuthenticationResponse(token);
    }

    public Map<String,String> register(RegisterRequest registerRequest){
         checkvalidateRegister(registerRequest);
         String otp = otpUtil.generateOtp();
         try{
             emailUtil.sendOtpEmail(registerRequest.getEmail(), otp);
         }catch(MessagingException e){
             throw new RuntimeException("unble");
         }    
         String fullname = registerRequest.getFullname() ; 
         String email = registerRequest.getEmail(); 
         Integer phone = registerRequest.getPhone();
         String password = registerRequest.getPassword();
         String address = registerRequest.getAddress();
         String gender = registerRequest.getGender();
         Date birthday = registerRequest.getBirthday();
         Role roleID = roleRepository.findById(2).orElseThrow(); 
        //  Role roleID = roleRepository.findById(1).orElseThrow(() -> new RuntimeException("Role not found"));
        Account account = new Account();
        account.setRoleID(roleID);
        account.setFullname(fullname);
        account.setEmail(email);
        account.setPassword(password);
        account.setAddress(address);
        account.setGender(gender);
        account.setBirthday(birthday);
        account.setPhone(phone);
        account.setAccountStatus(false);
        accountRepository.save(account);
        return Collections.singletonMap("messag e", "check mail and input OTP");
    }
public void checkvalidateRegister(RegisterRequest registerRequest){
    String fullname = registerRequest.getFullname() ; 
    String email = registerRequest.getEmail(); 
    Integer phone = registerRequest.getPhone();
    String password = registerRequest.getPassword();
    String address = registerRequest.getAddress();
    String gender = registerRequest.getGender();
    Date birthday = registerRequest.getBirthday();
    if (!email.matches("^[a-zA-Z0-9._%+-]+@gmail.com$") && !email.matches("^[a-zA-Z0-9._%+-]+@fpt.edu.vn$")) {
        throw new RuntimeException("Invalid email format");
    }
 
    // Check if email already exists
    Account existingEmail = accountRepository.findByEmail(email);
    if (existingEmail != null) {
        // If account exists and is active
        if (existingEmail.getAccountStatus() != null && existingEmail.getAccountStatus()) {
            throw new RuntimeException("Account already exists");
        }
    }
    Account existingPhone = accountRepository.findByPhone(phone);
        if (existingPhone != null) {
            if (existingEmail.getAccountStatus() != null && existingEmail.getAccountStatus()) {
                throw new RuntimeException("Account already exists");
            }
        }
        if (birthday.after(new Date())) {
            throw new RuntimeException("Birthday cannot be in the future.");
        }
    
    if(!StringUtils.hasText(fullname)
        || !StringUtils.hasText(email)
            || !StringUtils.hasText(password)
                ||!StringUtils.hasText(address)
                    ||phone == null
                        || gender == null
                            ||birthday == null){
        throw new RuntimeException("Vui lòng nhập đầy đủ thông tin");
        }
    }
        public String verifyAccount(String email,String otp){
        Account account = accountRepository.findByEmail(email);
           
        if(account.getOtp().equals(otp) && Duration.between(account.getOtp_generated_time(),
                     LocalDateTime.now()).getSeconds()<(1* 300)){
                        account.setAccountStatus(true);
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

    public Map<String, Object> loginWithGoogle(OAuth2AuthenticationToken oAuth2AuthenticationToken) {
        OAuth2User oAuth2User = oAuth2AuthenticationToken.getPrincipal();
        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");
        String picture = oAuth2User.getAttribute("picture");

        if (email == null || name == null) {
            throw new IllegalArgumentException("Email and Name cannot be null");
        }

        Account account = accountRepository.findByEmail(email);
        if (account == null) {
            account = new Account();
            account.setEmail(email);
            account.setFullname(name);
            account.setAvatar(picture);
            account.setAccountStatus(true);
            Role roleID = roleRepository.findById(2).orElseThrow(); 
            account.setRoleID(roleID);
            accountRepository.save(account);
        } else {
            account.setFullname(name);
            account.setAvatar(picture);
            accountRepository.save(account);
        }

        return oAuth2User.getAttributes();
    }
}