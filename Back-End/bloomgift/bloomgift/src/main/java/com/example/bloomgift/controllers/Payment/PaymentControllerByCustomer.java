package com.example.bloomgift.controllers.Payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.bloomgift.model.Payment;
import com.example.bloomgift.service.PaymentService;

@RestController
@RequestMapping("/api/customer/payment")
public class PaymentControllerByCustomer {
    @Autowired
    private PaymentService paymentService;

    // Update payment code
    @PutMapping("input-code-by-{customerID}")
    public ResponseEntity<Payment> updatePaymentCode(
            @PathVariable Integer accountId,
            @PathVariable Integer paymentId,
            @RequestParam String paymentCode) {
        
        Payment updatedPayment = paymentService.inputPaymentCode(accountId, paymentId, paymentCode);
        return new ResponseEntity<>(updatedPayment, HttpStatus.OK);
    }
}
