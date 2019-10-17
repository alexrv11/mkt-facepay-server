package com.hackaton.facepayapi.controllers;

import com.hackaton.facepayapi.models.PaymentFrontRequest;
import com.hackaton.facepayapi.services.PaymentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    private PaymentsService paymentsService;

    @PostMapping(value = "/payments", produces = "application/json")
    public ResponseEntity<String> processPaymentNotification(@RequestBody PaymentFrontRequest request) {
        try {
            return ResponseEntity.status(OK).body(paymentsService.makePayment(request).toString());
        } catch (RuntimeException runtimeException) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(runtimeException.getMessage());
        }
    }
}
