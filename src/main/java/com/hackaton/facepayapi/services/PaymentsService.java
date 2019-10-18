package com.hackaton.facepayapi.services;

import com.hackaton.facepayapi.clients.PaymentsClient;
import com.hackaton.facepayapi.daos.UsersEntity;
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

    public PaymentResponse makePayment(UsersEntity sellerUser, UsersEntity payerUser,PaymentFrontRequest request) {

        Disbursement disbursement = new Disbursement();
        disbursement.setApplicationFee(new BigDecimal(1L));
        disbursement.setExternalReference(request.getDescription());
        disbursement.setCollectorId(Integer.valueOf(sellerUser.getUserId()));
        disbursement.setAmount(request.getAmount());

        Payer payer = new Payer();
        payer.setPayerId(payerUser.getUserId());

        Payment payment = new Payment();
        payment.setTransactionAmount(request.getAmount());


        PaymentRequest paymentRequest = new PaymentRequest();
        //"APP_USR-6078376556362919-101721-6cedd433dc351340ce300ed6fe6879b1-480929876"
        paymentRequest.setAccessToken(payerUser.getAccessToken());
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
