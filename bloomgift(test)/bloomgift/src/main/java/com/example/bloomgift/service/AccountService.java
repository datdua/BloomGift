package com.example.bloomgift.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.util.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.bloomgift.model.Account;
import com.example.bloomgift.repository.AccountRepository;
import com.example.bloomgift.repository.RoleRepository;
import com.example.bloomgift.request.AccountRequest;
import com.example.bloomgift.utils.JwtUtil;

import io.jsonwebtoken.Claims;

@Service
public class AccountService implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

   @Autowired
   private JwtUtil jwtUtil;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account account = accountRepository.findByEmail(email);
        if (account == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
        return new User(account.getEmail(), account.getPassword(), new ArrayList<>());
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }
    

    public Optional<Account> getAccountById(int id) {
        return accountRepository.findById(id);
    }

    public void createAccount(AccountRequest accountRequest){
        checkvalidateAccount(accountRequest) ;
        String fullname = accountRequest.getFullname();
        String email = accountRequest.getEmail();
        String password = accountRequest.getPassword();
        Integer phone = accountRequest.getPhone();
        String address = accountRequest.getAddress();
        Date birthday = accountRequest.getBirthday(); 
        String roleName = accountRequest.getRoleName();


        Account existingAccount = accountRepository.findByEmail(email);
        if (existingAccount != null) {
            throw new RuntimeException("Tài khoản đã tồn tại");
        }

        com.example.bloomgift.model.Role role = roleRepository.findByName(roleName);
        if (role == null) {
            throw new RuntimeException("Role not found");
        }


        Account account = new Account();
        account.setRoleid(role);
        account.setFullname(fullname);
        account.setEmail(email);
        account.setAddress(address);
        account.setBirthday(birthday);
        account.setPhone(phone);
        account.setPassword(password);
        account.setActive(true);
        accountRepository.save(account);

        
    }


    public Account updateAccount(Integer id,AccountRequest accountRequest){
       
        checkvalidateAccount(accountRequest) ;
        Account existingAccount = accountRepository.findById(id).orElse(null);
        if (existingAccount == null) {
           throw new RuntimeException("Không tìm thấy tài khoản"); 
        }

        String fullname = accountRequest.getFullname();
        String email = accountRequest.getEmail();
        String password = accountRequest.getPassword();
        Integer phone = accountRequest.getPhone();
        String address = accountRequest.getAddress();
        Date birthday = accountRequest.getBirthday(); 
        //-----------------------------------------------------//
        existingAccount.setFullname(fullname);
        existingAccount.setEmail(email);
        existingAccount.setPassword(password);
        existingAccount.setPhone(phone);
        existingAccount.setAddress(address);
        existingAccount.setBirthday(birthday);
        existingAccount.setActive(accountRequest.isActive());
        
        String roleName = accountRequest.getRoleName();
        if (roleName != null && !roleName.isEmpty()) {
            com.example.bloomgift.model.Role role = roleRepository.findByName(roleName);
            if (role == null) {
                throw new RuntimeException("Role not found");
            }
            existingAccount.setRoleid(role);
        }
        
        if(accountRequest.getPassword() !=null && !accountRequest.getPassword().isEmpty()
                    && !accountRequest.getPassword().equals(existingAccount.getPassword())){
                             BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                             String encodedPassword = passwordEncoder.encode(accountRequest.getPassword());
                             existingAccount.setPassword(encodedPassword);
        }
       
        return accountRepository.save(existingAccount);
    }
    
    public void deleteAccount(Integer id) {
        Account existingAccount = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        if ("ADMIN".equals(existingAccount.getRoleName())) {
            throw new RuntimeException("Cannot delete admin account");
        }

        accountRepository.delete(existingAccount);
    }


    public Account editAccountProfile_admin(Integer id, AccountRequest accountRequest){
        checkvalidateAccount(accountRequest) ;
        Account existingAccount = accountRepository.findById(id).orElseThrow();
        if (existingAccount == null) {
            throw new RuntimeException("Không tìm thấy tài khoản");
        }

        String fullname = accountRequest.getFullname();
        String password = accountRequest.getPassword();
        Integer phone = accountRequest.getPhone();
        String address = accountRequest.getAddress();
        Date birthday = accountRequest.getBirthday(); 
        String email = accountRequest.getFullname();
        // ----=----------------------------------------------
        existingAccount.setFullname(fullname);
        existingAccount.setPhone(phone);
        existingAccount.setBirthday(birthday);
        existingAccount.setAddress(address);
        existingAccount.setEmail(email);
        if (accountRequest.getPassword() != null && !password.isEmpty()
        && !accountRequest.getPassword().equals(password)) {
                existingAccount.setPassword(password);
        }
        return accountRepository.save(existingAccount);
    }
    


    public Account editAccountProfile(Integer id, AccountRequest accountRequest){
        checkvalidateAccount(accountRequest) ;
        Account existingAccount = accountRepository.findById(id).orElseThrow();
        if (existingAccount == null) {
            throw new RuntimeException("Không tìm thấy tài khoản");
        }

        String fullname = accountRequest.getFullname();
        String password = accountRequest.getPassword();
        Integer phone = accountRequest.getPhone();
        String address = accountRequest.getAddress();
        Date birthday = accountRequest.getBirthday(); 
        // ----=----------------------------------------------
        existingAccount.setFullname(fullname);
        existingAccount.setPhone(phone);
        existingAccount.setBirthday(birthday);
        existingAccount.setAddress(address);
        String roleName = accountRequest.getRoleName();
        if (roleName != null && !roleName.isEmpty()) {
            com.example.bloomgift.model.Role role = roleRepository.findByName(roleName);
            if (role == null) {
                throw new RuntimeException("Role not found");
            }
            existingAccount.setRoleid(role);
        }
        if (accountRequest.getPassword() != null && !password.isEmpty()
        && !accountRequest.getPassword().equals(password)) {
                existingAccount.setPassword(password);
        }
        return accountRepository.save(existingAccount);
    }
    

    private void checkvalidateAccount(AccountRequest accountRequest){
        String fullname = accountRequest.getFullname();
        String email = accountRequest.getEmail();
        String password = accountRequest.getPassword();
        Integer phone = accountRequest.getPhone();
        String address = accountRequest.getAddress();
        Date birthday = accountRequest.getBirthday(); 
        String rolename = accountRequest.getRoleName();
        if (!email.matches("^[a-zA-Z0-9._%+-]+@gmail.com$") && !email.matches("^[a-zA-Z0-9._%+-]+@fpt.edu.vn$")) {
            throw new RuntimeException("Invalid email format");
        }

        // if (phone == null || !String.valueOf(phone).matches("^(90|93|89|96|97|98)[0-9]{7}$")) {
        //     throw new RuntimeException("Số điện thoại không hợp lệ");
        // }
        if (!StringUtils.hasText(fullname) 
            || !StringUtils.hasText(password) 
                || !StringUtils.hasText(email)
                    || !StringUtils.hasText(address)
                        || !StringUtils.hasText(rolename)) {
            throw new RuntimeException("Vui lòng nhập đầy đủ thông tin");
        }
     
     

    }





}