package com.hackaton.facepayapi.controller;

import com.hackaton.facepayapi.daos.SessionsEntity;
import com.hackaton.facepayapi.daos.UsersEntity;
import com.hackaton.facepayapi.models.PaymentFrontRequest;
import com.hackaton.facepayapi.models.PaymentResponse;
import com.hackaton.facepayapi.repositories.SessionsRepository;
import com.hackaton.facepayapi.repositories.UsersRepository;
import com.hackaton.facepayapi.service.AWSFaceRecognition;
import com.hackaton.facepayapi.services.PaymentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("")
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
            Optional<SessionsEntity> session = sessionsRepository.findFirstBySessionIdAndLoggedOrderByDtLoggedInDesc(Long.valueOf(fooCookie), true);
            if (!session.isPresent()) {
                return ResponseEntity.status(404).body("Seller not logged in");
            }
            Optional<UsersEntity> seller = usersRepository.findByUserName(session.get().getUserName());
            if (!seller.isPresent()) {
                return ResponseEntity.status(404).body("Seller not logged in");
            }
            Optional<String> faceID = AWSFaceRecognition.validateFace(request.getImageBase64());
            if (!faceID.isPresent()) {
                return ResponseEntity.status(404).body("User not found");
            }

            Optional<UsersEntity> payer = usersRepository.findByFaceId(faceID.get());
            if (!payer.isPresent()) {
                return ResponseEntity.status(404).body("User not found");
            }
            PaymentResponse resp = paymentsService.makePayment(seller.get(), payer.get(), request);
            return ResponseEntity.status(CREATED).build();
        } catch (RuntimeException runtimeException) {
            return ResponseEntity.status(404).body("Payment unsuccesfull");
        }
    }
}
