package com.example.bloomgift.controllers.Payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.bloomgift.model.Payment;
import com.example.bloomgift.model.PaymentNotification;
import com.example.bloomgift.repository.PaymentRepository;
import com.example.bloomgift.service.PaymentService;
import com.example.bloomgift.service.QRCodeService;
import com.example.bloomgift.service.VietQRService;
import com.google.zxing.WriterException;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class PaymentController {
    // private final QRCodeService qrCodeService;
    // private final VietQRService vietQRService;
    // @Autowired
    // private PaymentRepository paymentRepository;
    // @Autowired
    // private PaymentService paymentService;

    // public PaymentController(QRCodeService qrCodeService, VietQRService vietQRService) {
    //     this.qrCodeService = qrCodeService;
    //     this.vietQRService = vietQRService;
    // }

    // @PostMapping("/generateVietQR")
    // public ResponseEntity<String> generateVietQR(
    //         @RequestParam String accountNo,
    //         @RequestParam String accountName,
    //         @RequestParam int acqId,
    //         @RequestParam double amount,
    //         @RequestParam String addInfo) {
    //     try {
    //         ResponseEntity<String> qrResponse = vietQRService.generateVietQR(accountNo, accountName, acqId, amount,
    //                 addInfo);
    //         return qrResponse;
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //         return ResponseEntity.status(500).body("Lỗi khi tạo mã QR");
    //     }
    // }

    // @PostMapping("/generate-QR-code")
    // public ResponseEntity<byte[]> generateVietQR(@RequestParam String qrDataURL) {
    //     try {
    //         // Tạo mã QR từ qrDataURL
    //         byte[] qrCodeImage = qrCodeService.generateQRCode(qrDataURL, 300, 300);

    //         HttpHeaders headers = new HttpHeaders();
    //         headers.setContentType(MediaType.IMAGE_PNG);

    //         return ResponseEntity.ok().headers(headers).body(qrCodeImage);
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //         return ResponseEntity.status(500).body(null);
    //     }
    // }

    // @PostMapping("/payment")
    // public ResponseEntity<String> payment(@RequestParam Integer paymentID) {
    //     Payment payment = paymentRepository.findById(paymentID).orElseThrow();
    //     try {
    //         ResponseEntity<String> qrResponse = paymentService.generateVietQR(payment);
    //         return qrResponse;
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //         return ResponseEntity.status(500).body("Lỗi khi tạo mã QR");
    //     }

    // }

    // @PostMapping("/webhook")
    // public ResponseEntity<String> paymentWebhook(@RequestBody PaymentNotification notification) {
    //     try {
    //         Payment payment = paymentRepository.findByBankNumberAndTotalPrice(
    //                 notification.getAccountNumber(), notification.getAmount());

    //         if (payment != null && payment.getPaymentCode().equals(notification.getPaymentCode())) {
    //             payment.setPaymentStatus(true);
    //             paymentRepository.save(payment);
    //             return ResponseEntity.ok("Payment status updated successfully");
    //         } else {
    //             return ResponseEntity.status(400).body("Invalid payment information");
    //         }
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //         return ResponseEntity.status(500).body("Error processing payment webhook");
    //     }
    // }
    // @PostMapping("/confirm")
    // public ResponseEntity<String> confirmPayment(@RequestParam Integer paymentID) {
    //     // Tìm giao dịch dựa trên ID
    //     Payment payment = paymentRepository.findById(paymentID).orElseThrow();
        
    //     if (payment == null) {
    //         return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Giao dịch không tồn tại");
    //     }

    //     try {
    //         boolean isConfirmed = paymentService.confirmPaymentWithBank(payment);
    //         if (isConfirmed) {
    //             paymentService.updatePaymentStatus(paymentID);
    //             return ResponseEntity.ok("Xác nhận thanh toán thành công");
    //         } else {
    //             return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Giao dịch chưa được xác nhận");
    //         }
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi khi xác nhận thanh toán");
    //     }
    // }
}
