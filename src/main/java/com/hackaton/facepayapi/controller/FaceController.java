package com.hackaton.facepayapi.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FaceController {

    @RequestMapping("/ping")
    public String Ping() {
        return "pong";
    }
}
