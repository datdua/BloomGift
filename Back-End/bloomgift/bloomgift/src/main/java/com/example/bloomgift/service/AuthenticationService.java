package com.example.bloomgift.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.bloomgift.model.Account;
import com.example.bloomgift.model.Role;
import com.example.bloomgift.model.Store;
import com.example.bloomgift.reponse.AuthenticationResponse;
import com.example.bloomgift.repository.AccountRepository;
import com.example.bloomgift.repository.RoleRepository;
import com.example.bloomgift.repository.StoreRepository;
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

    @Autowired
    private StoreRepository storeRepository;

    public AuthenticationService(AccountRepository accountRepository, RoleRepository roleRepository,
            AuthenticationManager authenticationManager, JwtUtil jwtUtil, AccountService accountService) {
        this.accountRepository = accountRepository;
        this.roleRepository = roleRepository;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.accountService = accountService;
    }

    public ResponseEntity<?> authenticate(LoginRequest loginRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body(Collections.singletonMap("message", "Sai email hoặc mật khẩu"));
        }

        // Tìm thông tin người dùng trong cả Account và Store
        final UserDetails userDetails = accountService.loadUserByEmail(loginRequest.getEmail());

        // Kiểm tra trạng thái tài khoản
        Account account = accountRepository.findByEmail(loginRequest.getEmail());
        Store store = storeRepository.findByEmail(loginRequest.getEmail());

        if (account != null && !account.getAccountStatus(true)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Collections.singletonMap("message",
                            "Tài khoản chưa được kích hoạt. Vui lòng kiểm tra email để kích hoạt tài khoản."));
        }

        if (store != null && !store.getStoreStatus().equals("Đã kích hoạt")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Collections.singletonMap("message",
                            "Cửa hàng chưa được kích hoạt. Vui lòng liên hệ quản lý."));
        }

        // Tạo JWT cho tài khoản hoặc cửa hàng
        final String jwt = account != null ? jwtUtil.generateToken(account) : jwtUtil.generateToken(store);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

    public Map<String, String> register(RegisterRequest registerRequest) {
        checkvalidateRegister(registerRequest);
        String email = registerRequest.getEmail();
        Account existingAccount = accountRepository.findByEmail(email);

        if (existingAccount != null && !existingAccount.getAccountStatus(true)) {
            accountRepository.delete(existingAccount);
        }
        String otp = otpUtil.generateOtp();
        try {
            emailUtil.sendOtpEmail(registerRequest.getEmail(), otp);
        } catch (MessagingException e) {
            throw new RuntimeException("unble");
        }
        String fullname = registerRequest.getFullname();
        // String email = registerRequest.getEmail();
        Integer phone = registerRequest.getPhone();
        String password = registerRequest.getPassword();
        String address = registerRequest.getAddress();
        String gender = registerRequest.getGender();
        Date birthday = registerRequest.getBirthday();
        Role roleID = roleRepository.findById(2).orElseThrow();

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
        account.setOtp(otp);
        account.setOtp_generated_time(LocalDateTime.now());
        accountRepository.save(account);
        return Collections.singletonMap("messag e", "kiểm tra mail và nhập OTP");
    }

    public void checkvalidateRegister(RegisterRequest registerRequest) {
        String fullname = registerRequest.getFullname();
        String email = registerRequest.getEmail();
        Integer phone = registerRequest.getPhone();
        String password = registerRequest.getPassword();
        String address = registerRequest.getAddress();
        String gender = registerRequest.getGender();
        Date birthday = registerRequest.getBirthday();
        if (!email.matches("^[a-zA-Z0-9._%+-]+@gmail.com$") && !email.matches("^[a-zA-Z0-9._%+-]+@fpt.edu.vn$")) {
            throw new RuntimeException("Invalid email format");
        }

        Account existingEmail = accountRepository.findByEmail(email);
        if (existingEmail != null) {
            throw new RuntimeException("Account already exists");
        }
        Account existingPhone = accountRepository.findByPhone(phone);
        if (existingPhone != null) {
            if (existingPhone.getAccountStatus(true) != null && existingPhone.getAccountStatus(true)) {
                throw new RuntimeException("Account already exists");
            }
        }
        if (birthday.after(new Date())) {
            throw new RuntimeException("Birthday cannot be in the future.");
        }

        if (!StringUtils.hasText(fullname)
                || !StringUtils.hasText(email)
                || !StringUtils.hasText(password)
                || !StringUtils.hasText(address)
                || phone == null
                || gender == null
                || birthday == null) {
            throw new RuntimeException("Vui lòng nhập đầy đủ thông tin");
        }
    }

    public String verifyAccount(String email, String otp) {
        Account account = accountRepository.findByEmail(email);
        Store store = storeRepository.findByEmail(email);

        if (account == null && store == null) {
            return "Account not found";
        }

        if (account != null) {
            if (account.getOtp().equals(otp) && Duration.between(account.getOtp_generated_time(),
                    LocalDateTime.now()).getSeconds() < (1 * 500)) {
                account.setAccountStatus(true);
                accountRepository.save(account);
                return "OTP verified, you can login";
            }
        }

        if (store != null) {
            if (store.getOtp().equals(otp) && Duration.between(store.getOtp_generated_time(),
                    LocalDateTime.now()).getSeconds() < (1 * 500)) {
                store.setStoreStatus("Chờ duyệt");
                storeRepository.save(store);
                return "OTP verified, you can login";
            }
        }

        return "Please regenerate OTP and try again";
    }

    public String generateOtp(String email) {
        Account account = accountRepository.findByEmail(email);
        
        if (!account.getAccountStatus(true)) {  // Assuming accountStatus is a boolean field
            String otp = otpUtil.generateOtp();
            try {
                emailUtil.sendOtpEmail(email, otp);
            } catch (MessagingException e) {
                throw new RuntimeException("Unable to send OTP email.");
            }
            account.setOtp(otp);
            account.setOtp_generated_time(LocalDateTime.now());
            accountRepository.save(account);
            return "Email sent. Please verify your account within 1 minute.";
        } else {
            return "Account is already active.";
        }
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

        // Generate JWT token
        String jwt = jwtUtil.generateToken(account);

        // Create a new modifiable map and copy attributes
        Map<String, Object> attributes = new HashMap<>(oAuth2User.getAttributes());
        attributes.put("jwt", jwt);

        return attributes;
    }
}