package com.hackaton.facepayapi.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Payer {
    @JsonProperty("id")
    private String payerId;

    public String getPayerId() {
        return payerId;
    }

    public void setPayerId(String payerId) {
        this.payerId = payerId;
    }
}
