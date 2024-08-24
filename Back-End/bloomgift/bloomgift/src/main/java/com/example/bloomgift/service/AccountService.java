package com.example.bloomgift.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.bloomgift.model.Account;
import com.example.bloomgift.repository.AccountRepository;
import com.example.bloomgift.utils.EmailUtil;
import com.example.bloomgift.utils.OtpUtil;

@Service
public class AccountService implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private EmailUtil emailUtil;

    @Autowired
    private OtpUtil otpUtil;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account account = accountRepository.findByEmail(email);
        if (account == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
        return new User(account.getEmail(), account.getPassword(), new ArrayList<>());
    }

    public Map<String, String> setPassword(String email, String newPassword) {
        Account account = accountRepository.findByEmail(email);
        if (account == null) {
            return Collections.singletonMap("message", "Email không tồn tại.");
        }
        account.setPassword(newPassword);
        accountRepository.save(account);
        return Collections.singletonMap("message", "Mật khẩu đã được thiết lập. Vui lòng đăng nhập.");
    }

    public Map<String, String> forgetPassword(String email) {
        Account account = accountRepository.findByEmail(email);
        if (account == null) {
            return Collections.singletonMap("message", "Email không tồn tại.");
        }
        try {
            emailUtil.sendForgetPasswordEmail(email);
        } catch (Exception e) {
            return Collections.singletonMap("message", "Có lỗi xảy ra khi gửi email.");
        }
        return Collections.singletonMap("message", "Mã OTP đã được gửi đến email của bạn.");
    }

}
