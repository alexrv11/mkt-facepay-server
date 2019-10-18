package com.hackaton.facepayapi.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AccessTokenRequest {
    @JsonProperty("client_secret")
    private String clientSecret;
    @JsonProperty("client_id")
    private String applicationId;
    @JsonProperty("grant_type")
    private String grantType = "authorization_code";
    private String code;
    @JsonProperty("redirect_uri")
    private String redirectUri;

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getGrantType() {
        return grantType;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }
}
