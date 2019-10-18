package com.hackaton.facepayapi.controller;

import com.amazonaws.services.rekognition.model.DeleteFacesResult;
import com.amazonaws.services.rekognition.model.ListFacesResult;
import com.hackaton.facepayapi.models.FaceLogin;
import com.hackaton.facepayapi.models.FaceValidationResult;
import com.hackaton.facepayapi.service.AWSFaceRecognition;
import com.hackaton.facepayapi.services.RegistrationService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.NoSuchElementException;
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
            return "https://auth.mercadopago.com.br/authorization?client_id=" + applicationId + "&response_type=code&platform_id=mp&state=iframe&display=popup&interactive=1&scopes=wallet-payments&redirect_uri=" + redirectUrl;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return StringUtils.EMPTY;
    }


    @GetMapping("/payer/{faceId}/register")
    public RedirectView callbackRegisterFace(@Valid @NotNull @PathVariable("faceId") String faceId, @RequestParam String code) {
        if (code == null)
            throw new NoSuchElementException("code param is null");
        if (registrationService.userRegistration(faceId, code)) {
            return new RedirectView("https://murmuring-shore-37556.herokuapp.com/login?status=registered");
        } else {
            return new RedirectView("https://murmuring-shore-37556.herokuapp.com/login?status=error");
        }
    }

    @GetMapping("/collection")
    public ListFacesResult getCollection() {
        return AWSFaceRecognition.getCollection();
    }

    @DeleteMapping("/collection")
    public DeleteFacesResult deleteFaces(@RequestBody String[] faces) {
        return AWSFaceRecognition.delete(faces);
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