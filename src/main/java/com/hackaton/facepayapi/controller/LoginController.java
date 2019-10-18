package com.hackaton.facepayapi.controller;

import com.hackaton.facepayapi.models.login.User;
import com.hackaton.facepayapi.models.login.response.LoginResponse;
import com.hackaton.facepayapi.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    LoginService loginService;

    @PostMapping(value = "/login", produces = "application/json")
    public ResponseEntity<LoginResponse> login(@RequestBody User user) {
        LoginResponse loginResponse = loginService.validateUser(user);
        return ResponseEntity.status(loginResponse.getStatus()).body(loginResponse);
    }
}
