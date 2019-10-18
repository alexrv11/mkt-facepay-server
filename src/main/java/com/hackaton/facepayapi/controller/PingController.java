package com.hackaton.facepayapi.controller;

import com.hackaton.facepayapi.services.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingController {
    @Autowired
    private RegistrationService registrationService;

    @RequestMapping("/ping")
    public String ping() {
        return "pong";
    }

    @RequestMapping("/test/get_access_token")
    public void testAccessToken() {
        Boolean result = registrationService.userRegistration("123", "TG-5da949e318d41500062d5e74-481050359");
        System.out.println(result);
    }
}
