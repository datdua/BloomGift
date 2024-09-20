package com.example.bloomgift.service;

import java.time.LocalDate;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bloomgift.model.Account;
import com.example.bloomgift.repository.AccountRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class PoinService {
    @Autowired
    private AccountRepository accountRepository;
  @Autowired
    private HttpSession httpSession;
    public Account updatePoint(Integer accountID, Integer point) {
        Account existingAccount = accountRepository.findById(accountID).orElse(null);
        if (existingAccount == null) {
            throw new RuntimeException("Không tìm thấy tài khoản");
        }
        LocalDate today = LocalDate.now();
        LocalDate lastSpinDate = (LocalDate) httpSession.getAttribute("lastSpinDate_" + accountID);
        if (lastSpinDate != null && lastSpinDate.isEqual(today)) {
            throw new RuntimeException("Bạn đã quay hôm nay rồi");
        }
        existingAccount.setPoint(existingAccount.getPoint() + point);
        
        httpSession.setAttribute("lastSpinDate_" + accountID, today); 
        return accountRepository.save(existingAccount);
    }

    
}
