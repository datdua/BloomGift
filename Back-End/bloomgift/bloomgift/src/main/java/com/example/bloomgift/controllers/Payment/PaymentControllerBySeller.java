package com.example.bloomgift.controllers.Payment;

import org.springframework.web.bind.annotation.RestController;

import com.example.bloomgift.repository.PaymentRepository;
import com.example.bloomgift.service.PaymentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/seller/payment")
public class PaymentControllerBySeller {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/check-payment-code")
    public ResponseEntity<?> checkPaymentCode(@RequestParam String paymentCode) {
        return paymentService.checkPaymentCode(paymentCode);
    }

    @PutMapping("/update-payment-status/{paymentID}")
    public ResponseEntity<?> updatePaymentStatus(@PathVariable Integer paymentID, @RequestParam boolean paymentStatus) {
        return paymentService.updatePaymentStatus(paymentID, paymentStatus);
    }

    @PutMapping("/enter-payment-code/{paymentID}")
    public ResponseEntity<?> enterPaymentCode(@PathVariable Integer paymentID, @RequestParam String paymentCode) {
        return paymentService.enterPaymentCode(paymentID, paymentCode);
    }
}
