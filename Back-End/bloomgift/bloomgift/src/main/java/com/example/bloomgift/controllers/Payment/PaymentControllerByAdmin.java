package com.example.bloomgift.controllers.Payment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.bloomgift.model.Payment;
import com.example.bloomgift.reponse.PaymentReponse;
import com.example.bloomgift.service.PaymentService;

@RestController
@RequestMapping("/api/auth")
public class PaymentControllerByAdmin {
      @Autowired
    private PaymentService paymentService;

    // Get all payments
    @GetMapping("/all")
    public ResponseEntity<List<PaymentReponse>> getAllPayments() {
        List<PaymentReponse> paymentResponses = paymentService.getAllpayment();
        return new ResponseEntity<>(paymentResponses, HttpStatus.OK);
    }

    @PutMapping("/update-payment-status{paymentId}/{status}")
    public ResponseEntity<Payment> updatePaymentStatus(
            @PathVariable Integer paymentId,
            @RequestParam Boolean paymentStatus) {
        
        Payment updatedPayment = paymentService.updatePaymentStatus(paymentId, paymentStatus);
        return new ResponseEntity<>(updatedPayment, HttpStatus.OK);
    }
}
