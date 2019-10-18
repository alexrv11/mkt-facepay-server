package com.hackaton.facepayapi.models.login.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginResponse {

    @JsonProperty("status")
    Integer status;
    @JsonProperty("user_type")
    String userType;

    public LoginResponse(Integer status) {
        this.status = status;
    }

    public LoginResponse(Integer status, String userType) {
        this.status = status;
        this.userType = userType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
