package com.hackaton.facepayapi.services;

import com.hackaton.facepayapi.clients.PaymentsClient;
import com.hackaton.facepayapi.models.*;
import com.mercadolibre.json.exception.JsonException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;

@Service
public class PaymentsService {

    protected final Logger log = LoggerFactory.getLogger(PaymentsService.class);
    @Autowired
    private PaymentsClient paymentsClient;

    public PaymentResponse makePayment(PaymentFrontRequest request) {

        Disbursement disbursement = new Disbursement();
        disbursement.setAmount(new BigDecimal(10L));
        disbursement.setApplicationFee(new BigDecimal(1L));
        disbursement.setExternalReference("test_sube_pay");
        disbursement.setCollectorId(480928194);
        disbursement.setAmount(request.getAmount());

        Payer payer = new Payer();
        payer.setPayerId("480929876");

        Payment payment = new Payment();
        payment.setTransactionAmount(request.getAmount());


        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setAccessToken("APP_USR-6078376556362919-101721-6cedd433dc351340ce300ed6fe6879b1-480929876");
        paymentRequest.setApplicationId("6078376556362919");
        paymentRequest.setDisbursements(Arrays.asList(disbursement));
        paymentRequest.setPayer(payer);
        paymentRequest.setPayments(Arrays.asList(payment));
        try {
            return paymentsClient.createPayment(paymentRequest);
        } catch (JsonException jsonException) {
            log.error("Error on create payment", jsonException);
            throw new RuntimeException();
        }
    }

}
