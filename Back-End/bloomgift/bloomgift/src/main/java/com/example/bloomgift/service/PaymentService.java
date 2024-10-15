package com.example.bloomgift.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collections;
import com.example.bloomgift.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.bloomgift.model.Payment;

@Service
public class PaymentService {

    @Value("${bank.api.url}")
    private String bankApiUrl;

    @Value("${vietqr.api.url}")
    private String apiUrl;

    @Value("${vietqr.api.key}")
    private String apiKey;

    @Autowired
    private PaymentRepository paymentRepository;

    private final RestTemplate restTemplate;

    public PaymentService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<String> generateVietQR(Payment payment) {
        String qrContent = String.format(
                "{\"accountNo\": \"%s\",\"accountName\": \"%s\",\"acqId\": %d,\"amount\": %.2f,\"addInfo\": \"%s\",\"format\": \"text\",\"template\": \"compact\"}",
                payment.getBankNumber(), payment.getBankAccountName(), payment.getAcqId(), payment.getTotalPrice(),
                payment.getPaymentCode());

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

    public boolean confirmPaymentWithBank(Payment payment) {
        String requestPayload = String.format(
                "{\"accountNo\": \"%s\",\"accountName\": \"%s\",\"acqId\": %d,\"amount\": %.2f,\"addInfo\": \"%s\"}",
                payment.getBankNumber(), payment.getBankAccountName(), payment.getAcqId(), payment.getTotalPrice(),
                payment.getPaymentCode());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> requestEntity = new HttpEntity<>(requestPayload, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(bankApiUrl, requestEntity, String.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                // Kiểm tra nội dung phản hồi để xác nhận giao dịch thành công
                return response.getBody().contains("transaction successful");
            } else {
                // Xử lý mã trạng thái khác
                System.out.println("Lỗi xác nhận thanh toán: " + response.getStatusCode());
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi khi xác nhận thanh toán: " + e.getMessage());
        }
    }

    public void updatePaymentStatus(Integer paymentID) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updatePaymentStatus'");
    }

    @Transactional
    public ResponseEntity<?> updatePaymentStatus(@PathVariable Integer paymentID, boolean paymentStatus) {
        Payment payment = paymentRepository.findById(paymentID).orElse(null);

        if (payment == null) {
            return ResponseEntity.badRequest().body("Không tìm thấy thanh toán");
        }

        // Update payment status
        payment.setPaymentStatus(paymentStatus);
        paymentRepository.save(payment);

        return ResponseEntity.ok(Collections.singletonMap("message", "Cập nhật trạng thái thanh toán thành công"));
    }

    public ResponseEntity<?> enterPaymentCode(@PathVariable Integer paymentID, String paymentCode) {
        Payment payment = paymentRepository.findById(paymentID).orElse(null);

        if (payment == null) {
            return ResponseEntity.badRequest().body("Không tìm thấy thanh toán");
        }

        payment.setPaymentCode(paymentCode);
        paymentRepository.save(payment);
        return ResponseEntity.ok(Collections.singletonMap("message", "Nhập mã thanh toán thành công"));
    }

    public ResponseEntity<?> checkPaymentCode(String paymentCode) {
        Payment payment = paymentRepository.findByPaymentCode(paymentCode);
        if (payment == null) {
            return ResponseEntity.badRequest().body("Mã thanh toán không hợp lệ");
        } else {
            payment.setPaymentStatus(true);
            return ResponseEntity.ok(Collections.singletonMap("message", "Mã thanh toán hợp lệ"));
        }
    }

}
