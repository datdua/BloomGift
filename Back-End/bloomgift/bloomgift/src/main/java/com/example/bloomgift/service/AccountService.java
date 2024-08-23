package com.example.bloomgift.service;

import java.util.ArrayList;
import org.springframework.security.core.userdetails.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.bloomgift.model.Account;
import com.example.bloomgift.repository.AccountRepository;


@Service
public class AccountService implements UserDetailsService {
    
    @Autowired
    private AccountRepository accountRepository;

     @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account account = accountRepository.findByEmail(email);
        if (account == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
        return new User(account.getEmail(), account.getPassword(), new ArrayList<>());
    }
}
