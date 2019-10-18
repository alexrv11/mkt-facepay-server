package com.hackaton.facepayapi.controller;

import com.hackaton.facepayapi.models.FaceLogin;
import com.hackaton.facepayapi.models.FaceValidationResult;
import com.hackaton.facepayapi.service.AWSFaceRecognition;
import com.hackaton.facepayapi.services.RegistrationService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@RestController
public class FaceController {

    @Autowired
    private AWSFaceRecognition AWSFaceRecognition;

    @Autowired
    private RegistrationService registrationService;

    @PostMapping("/payer/face")
    public String registerFace(@RequestBody FaceLogin login) {
        try {

            String faceId = AWSFaceRecognition.uploadFace(login.getFace());
            String applicationId = "6078376556362919";
            String redirectUrl = "https%3A%2F%2Fthawing-wildwood-80127.herokuapp.com/payer/" + faceId + "/register";
            return "https://auth.mercadopago.com.br/authorization?client_id=" + applicationId + "&response_type=token&platform_id=mp&state=iframe&display=popup&interactive=1&scopes=wallet-payments&redirect_uri=" + redirectUrl;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return StringUtils.EMPTY;
    }

    @GetMapping("/payer/{faceId}/register")
    public String callbackRegisterFace(@Valid @NotNull @PathVariable("faceId") String faceId, @Valid @NotNull @RequestParam String code) {
        registrationService.userRegistration(faceId, code);
        return "redirected";
    }


    // Only used to add a new face to the collection
    @PostMapping("/facelogin")
    public String faceLogin(@RequestBody FaceLogin login) {

        try {
            return AWSFaceRecognition.uploadFace(login.getFace());
        } catch (Exception e) {
            e.printStackTrace();
        }

        //writeByte(decodedBytes);

        return "no cargue nada";
    }

    //Deprecated
    @PostMapping("/facelogin/validate")
    public ResponseEntity<FaceValidationResult> validateLogin(@RequestBody FaceLogin login) {
        Optional<String> faceID = AWSFaceRecognition.validateFace(login.getFace());
        if (!faceID.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.accepted().body(new FaceValidationResult(faceID.get()));

    }

    // Internal method to create a new collection.
    public ResponseEntity<String> createCollection() {
        Optional<String> res = AWSFaceRecognition.createCollection();
        if (!res.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.accepted().body(res.get());

    }

    public RegistrationService getRegistrationService() {
        return registrationService;
    }

    public void setRegistrationService(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }
}