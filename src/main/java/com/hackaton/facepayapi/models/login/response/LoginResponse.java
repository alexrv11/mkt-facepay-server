package com.hackaton.facepayapi.models.login.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginResponse {

    @JsonProperty("status")
    Integer status;
    @JsonProperty("user_type")
    String userType;

    @JsonProperty("user_name")
    String userName;

    public LoginResponse(Integer status) {
        this.status = status;
    }

    public LoginResponse(Integer status, String userType, String userName) {
        this.status = status;
        this.userType = userType;
        this.userName = userName;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
