package com.hackaton.facepayapi.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class PaymentFrontResponse {
    private Payment payment;
    private String reason;
    private String status;

    public PaymentFrontResponse.Payment getPayment() {
        return payment;
    }

    public String getReason() {
        return reason;
    }

    public String getStatus() {
        return status;
    }

    public static final class Builder {
        private PaymentFrontResponse.Payment payment;
        private String reason;
        private String status;

        private Builder() {
        }

        public static Builder aPaymentFrontResponse() {
            return new Builder();
        }

        public Builder withPayment(PaymentFrontResponse.Payment payment) {
            this.payment = payment;
            return this;
        }

        public Builder withReason(String reason) {
            this.reason = reason;
            return this;
        }

        public Builder withStatus(String status) {
            this.status = status;
            return this;
        }

        public PaymentFrontResponse build() {
            PaymentFrontResponse paymentFrontResponse = new PaymentFrontResponse();
            paymentFrontResponse.reason = this.reason;
            paymentFrontResponse.payment = this.payment;
            paymentFrontResponse.status = this.status;
            return paymentFrontResponse;
        }
    }

    public static class Payment {
        private String userName;
        private BigDecimal amount;
        private String description;

        public Payment(String userName, BigDecimal amount, String description) {
            this.userName = userName;
            this.amount = amount;
            this.description = description;
        }

        public String getUserName() {
            return userName;
        }

        public BigDecimal getAmount() {
            return amount;
        }

        public String getDescription() {
            return description;
        }
    }
}
