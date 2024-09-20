package com.example.bloomgift.controllers.Point;

import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bloomgift.model.Account;
import com.example.bloomgift.service.PoinService;

@RestController
@RequestMapping("/api/point")

public class PointController {
    @Autowired
    private PoinService poinService;

    @PutMapping("/{accountID}/points")
    public ResponseEntity<?> updateAccountPoints(@PathVariable Integer accountID,
            @RequestBody Integer point) {

        try {
            Account updatedAccount = poinService.updatePoint(accountID, point);
            return ResponseEntity.ok(Collections.singletonMap("message", "Successful"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Collections.singletonMap("message", e.getMessage()));
        }
    }
}
