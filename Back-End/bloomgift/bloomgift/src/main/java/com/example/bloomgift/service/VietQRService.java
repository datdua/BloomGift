package com.example.bloomgift.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

@Service
public class VietQRService {

    @Value("${vietqr.api.url}")
    private String apiUrl; 

    @Value("${vietqr.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    public VietQRService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    public ResponseEntity<String> generateVietQR(String accountNo, String accountName, int acqId, double amount, String addInfo) {
        String qrContent = String.format(
            "{\"accountNo\": \"%s\",\"accountName\": \"%s\",\"acqId\": %d,\"amount\": %.2f,\"addInfo\": \"%s\",\"format\": \"text\",\"template\": \"compact\"}",
            accountNo, accountName, acqId, amount, addInfo
        );
    
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
