package com.example.bloomgift.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.bloomgift.model.Payment;

@Service
public class PaymentService {
    
    @Value("${vietqr.api.url}")
    private String apiUrl; // URL của API VietQR

    @Value("${vietqr.api.key}")
    private String apiKey; // API Key của bạn

    private final RestTemplate restTemplate;

    public PaymentService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    public ResponseEntity<String> generateVietQR(Payment payment){
        String qrContent = String.format(
            "{\"accountNo\": \"%s\",\"accountName\": \"%s\",\"acqId\": %d,\"amount\": %.2f,\"addInfo\": \"%s\",\"format\": \"text\",\"template\": \"compact\"}",
            payment.getBankNumber(), payment.getBankAccountName(), payment.getAcqId(), payment.getTotalPrice(),payment.getPaymentCode());
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + apiKey);
    
        HttpEntity<String> requestEntity = new HttpEntity<>(qrContent, headers);
    
        try {
            return restTemplate.postForEntity(apiUrl, requestEntity, String.class);
        } catch (Exception e) {
            e.printStackTrace(); 
            throw new RuntimeException("Lỗi khi tạo mã QR: " + e.getMessage());
        }
        
    }
}
