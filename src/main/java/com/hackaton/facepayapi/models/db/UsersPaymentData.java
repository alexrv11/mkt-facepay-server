package com.hackaton.facepayapi.models.db;

public class UsersPaymentData {
    String mpUserId;
    String faceId;
    String token;

    public UsersPaymentData(String mpUserId, String faceId, String token) {
        this.mpUserId = mpUserId;
        this.faceId = faceId;
        this.token = token;
    }

    public String getMpUserId() {
        return mpUserId;
    }

    public void setMpUserId(String mpUserId) {
        this.mpUserId = mpUserId;
    }

    public String getFaceId() {
        return faceId;
    }

    public void setFaceId(String faceId) {
        this.faceId = faceId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
