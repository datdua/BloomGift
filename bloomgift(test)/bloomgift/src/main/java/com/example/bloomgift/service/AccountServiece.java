package com.example.bloomgift.service;


// import java.sql.Date;
import java.util.ArrayList;
// import java.util.Collections;
// import java.util.Optional;
// import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.example.bloomgift.model.Account;
import com.example.bloomgift.repository.AccountRepository;
// import com.example.bloomgift.request.RegisterRequest;



public class AccountServiece {
    @Autowired
    private AccountRepository accountRepository;
    
    public Account findByEmail(String email) {
        return accountRepository.findByEmail(email).orElse(null);
       
    }
    public UserDetails loadUserByEmail(String email) throws UsernameNotFoundException {
       Account account = accountRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Khong tim thay email nay: " + email));

        return new User(account.getEmail(), account.getPassword(), new ArrayList<>());
    }
    // public Account findByFullName(String fullname){
    //     return accountRepository.findByFullName(fullname);
    // }
    // public Map<String, String> register(RegisterRequest registerRequest){
       
    //     String fullname = registerRequest.getFullname();
    //     String password = registerRequest.getPassword();
    //     String email = registerRequest.getEmail();
    //     String address = registerRequest.getAddress();
    //     int phone = registerRequest.getPhone();
    //     java.util.Date brithday = registerRequest.getBirthday();

    //       if (!hasText(fullname) || !hasText(password) || !hasText(email) ||
    //       !hasText(address) || !hasInt(phone)) {
    //         throw new RuntimeException("Vui lòng nhập đầy đủ thông tin");
    //     }
    //     if (!email.matches("^[a-zA-Z0-9._%+-]+@gmail.com$") && !email.matches("^[a-zA-Z0-9._%+-]+@fpt.edu.vn$")) {
    //         throw new RuntimeException("Email không hợp lệ");
    //     }
    //     Optional<Account> existingAccount = accountRepository.findByEmail(email);
    //     if (existingAccount.isPresent()) {
    //         throw new RuntimeException("Tài khoản đã tồn tại");
    //     }
        
    //         Account account = new Account(null, email, password, fullname,brithday, address,false,phone);

    //     accountRepository.save(account);
    //     return Collections.singletonMap("message","thanh cong");

    
    // }
    // private boolean hasText(String text) {
    //     return text != null && !text.trim().isEmpty();
    // }
    // private boolean hasDate(Date date){
    //     return date != null ;

    // }
    // private boolean hasInt(int number){
    //     return number > 0 ;
    // }
}   


