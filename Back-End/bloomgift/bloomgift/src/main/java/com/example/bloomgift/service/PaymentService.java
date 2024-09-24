    package com.example.bloomgift.service;

    import org.springframework.beans.factory.annotation.Value;
    import org.springframework.context.annotation.Bean;
    import org.springframework.http.HttpEntity;
    import org.springframework.http.HttpHeaders;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.MediaType;
    import org.springframework.http.ResponseEntity;
    import org.springframework.stereotype.Service;
    import org.springframework.web.client.RestTemplate;

    import com.example.bloomgift.model.Payment;

    @Service
    public class PaymentService {

        @Value("${bank.api.url}")
        private String bankApiUrl;

        @Value("${vietqr.api.url}")
        private String apiUrl; 

        @Value("${vietqr.api.key}")
        private String apiKey; 

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
    
        public boolean confirmPaymentWithBank(Payment payment) {
            String requestPayload = String.format(
                "{\"accountNo\": \"%s\",\"accountName\": \"%s\",\"acqId\": %d,\"amount\": %.2f,\"addInfo\": \"%s\"}",
                payment.getBankNumber(), payment.getBankAccountName(), payment.getAcqId(), payment.getTotalPrice(),payment.getPaymentCode());
        
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
        
    }
