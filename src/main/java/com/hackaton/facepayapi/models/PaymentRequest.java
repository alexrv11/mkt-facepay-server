package com.hackaton.facepayapi.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class PaymentRequest {
    @JsonProperty("application_id")
    private String applicationId;
    private List<Payment> payments;
    private List<Disbursement> disbursements;
    private Payer payer = new Payer();
    private String externalReference = "Test sube payment request";
    @JsonIgnore
    private String accessToken;

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    public List<Disbursement> getDisbursements() {
        return disbursements;
    }

    public void setDisbursements(List<Disbursement> disbursements) {
        this.disbursements = disbursements;
    }

    public String getExternalReference() {
        return externalReference;
    }

    public void setExternalReference(String externalReference) {
        this.externalReference = externalReference;
    }

    public Payer getPayer() {
        return payer;
    }

    public void setPayer(Payer payer) {
        this.payer = payer;
    }


    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}