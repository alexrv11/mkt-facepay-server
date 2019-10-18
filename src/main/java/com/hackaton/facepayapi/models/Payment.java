package com.hackaton.facepayapi.models;

import java.math.BigDecimal;

public class Payment {
    private String paymentMethodId = "account_money";
    private String paymentTypeId = "account_money";
    private String processingMode = "aggregator";
    private BigDecimal transactionAmount;


    public BigDecimal getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(BigDecimal transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getProcessingMode() {
        return processingMode;
    }

    public String getPaymentTypeId() {
        return paymentTypeId;
    }

    public String getPaymentMethodId() {
        return paymentMethodId;
    }
}
