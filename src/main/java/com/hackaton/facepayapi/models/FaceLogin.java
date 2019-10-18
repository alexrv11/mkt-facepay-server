package com.hackaton.facepayapi.models;

public class FaceLogin {
    private String face;

    public FaceLogin(String face) {
        this.face = face;
    }

    public FaceLogin() {
    }

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }
}
