package com.hackaton.facepayapi.models.login;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hackaton.facepayapi.models.login.response.LoginResponse;

public class LoginEntity {
   LoginResponse loginResponse;
   long sessionId;

    public LoginEntity(LoginResponse loginResponse, long sessionId) {
        this.loginResponse = loginResponse;
        this.sessionId = sessionId;
    }

    public LoginEntity() {
    }

    public LoginResponse getLoginResponse() {
        return loginResponse;
    }

    public void setLoginResponse(LoginResponse loginResponse) {
        this.loginResponse = loginResponse;
    }

    public long getSessionId() {
        return sessionId;
    }

    public void setSessionId(long sessionId) {
        this.sessionId = sessionId;
    }
}
