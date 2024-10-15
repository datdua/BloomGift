package com.example.bloomgift.controllers.Payment;

import org.springframework.web.bind.annotation.RestController;

import com.example.bloomgift.repository.PaymentRepository;
import com.example.bloomgift.service.PaymentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/customer/payment")
public class PaymentControllerByCustomer {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/check-payment-code")
    public ResponseEntity<?> checkPaymentCode(@RequestParam String paymentCode) {
        return paymentService.checkPaymentCode(paymentCode);
    }
}
