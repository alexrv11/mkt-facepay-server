package com.hackaton.facepayapi.controller;

import com.hackaton.facepayapi.daos.SessionsEntity;
import com.hackaton.facepayapi.daos.UsersEntity;
import com.hackaton.facepayapi.models.PaymentFrontRequest;
import com.hackaton.facepayapi.repositories.SessionsRepository;
import com.hackaton.facepayapi.repositories.UsersRepository;
import com.hackaton.facepayapi.service.AWSFaceRecognition;
import com.hackaton.facepayapi.services.PaymentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/test")
public class PaymentController {
    @Autowired
    private PaymentsService paymentsService;
    @Autowired
    private AWSFaceRecognition AWSFaceRecognition;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private SessionsRepository sessionsRepository;

    @PostMapping(value = "/payments", produces = "application/json")
    public ResponseEntity<String> processPaymentNotification(@RequestBody PaymentFrontRequest request, @CookieValue("sessionID") String fooCookie) {
        try {
            Optional<SessionsEntity> session = sessionsRepository.findFirstBySessionIdAndLoggedOrderBydtLoggedInDesc(Long.valueOf(fooCookie), true);
            if (!session.isPresent()) {
                return ResponseEntity.notFound().build();
            }
            Optional<UsersEntity> seller = usersRepository.findByUserName(session.get().getUserName());
            if (!seller.isPresent()) {
                return ResponseEntity.notFound().build();
            }
            Optional<String> faceID = AWSFaceRecognition.validateFace(request.getImageBase64());
            if (!faceID.isPresent()) {
                return ResponseEntity.notFound().build();
            }
            Optional<UsersEntity> payer = usersRepository.findByFaceId(faceID.get());
            if (!payer.isPresent()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.status(OK).body(paymentsService.makePayment(request).toString());
        } catch (RuntimeException runtimeException) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(runtimeException.getMessage());
        }
    }
}
