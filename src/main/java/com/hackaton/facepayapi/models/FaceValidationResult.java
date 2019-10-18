package com.hackaton.facepayapi.models;

public class FaceValidationResult {

    private String faceId;

    public FaceValidationResult(String faceId) {
        this.faceId = faceId;
    }

    public FaceValidationResult() {
    }

    public String getFaceId() {
        return faceId;
    }

    public void setFaceId(String faceId) {
        this.faceId = faceId;
    }
}
