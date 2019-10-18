package com.hackaton.facepayapi.controller;

import com.hackaton.facepayapi.models.login.LoginEntity;
import com.hackaton.facepayapi.models.login.User;
import com.hackaton.facepayapi.models.login.response.LoginResponse;
import com.hackaton.facepayapi.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RestController
public class LoginController {

    @Autowired
    LoginService loginService;

    @PostMapping(value = "/login", produces = "application/json")
    public LoginResponse login(@RequestBody User user, HttpServletResponse response) {
        LoginEntity login = loginService.validateUser(user);
        response.addCookie(new Cookie("sessionID", String.valueOf(login.getSessionId())));
        response.setStatus(login.getLoginResponse().getStatus());
        return login.getLoginResponse();
    }
}
