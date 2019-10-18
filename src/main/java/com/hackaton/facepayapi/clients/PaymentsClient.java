package com.hackaton.facepayapi.clients;

import com.hackaton.facepayapi.models.PaymentRequest;
import com.hackaton.facepayapi.models.PaymentResponse;
import com.hackaton.facepayapi.utils.RestRequestUtils;
import com.mercadolibre.json.JsonUtils;
import com.mercadolibre.json.exception.JsonException;
import com.mercadolibre.restclient.RESTPool;
import com.mercadolibre.restclient.Response;
import com.mercadolibre.restclient.RestClient;
import com.mercadolibre.restclient.exception.RestException;
import com.mercadolibre.restclient.http.Headers;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParseException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import java.util.NoSuchElementException;

@Component
public class PaymentsClient {

    protected final Logger log = LoggerFactory.getLogger(PaymentsClient.class);
    @Autowired
    private RestClient restClient;

    /**
     * @param paymentId
     * @param callerId  this param can be the payerId or collectorId
     * @return
     * @throws NoSuchMethodException
     */
    public PaymentResponse getPayment(Long paymentId, String callerId) {
        String url = String.format("/v1/payments/%d", paymentId);
        RestRequestUtils restRequestUtils = RestRequestUtils.builder()
                .addCallerId(callerId)
                .withPath(url).build();
        //return super.getResource(restRequestUtils.getUri().toString(), restRequestUtils.getHeaders());
        throw new NoSuchElementException();
    }

    /**
     * @param paymentRequest <see> PaymentRequest</see> request
     * @return PaymentResponse
     * @throws NoSuchMethodException
     */
    public PaymentResponse createPayment(PaymentRequest paymentRequest) throws JsonException {
        Headers headers = new Headers();
        Response paymentResponse = postData(headers, paymentRequest);
        if (paymentResponse.getStatus() == HttpStatus.SC_CREATED || paymentResponse.getStatus() == HttpStatus.SC_OK)
            return JsonUtils.INSTANCE.toObject(paymentResponse.getString(), PaymentResponse.class);
        if (paymentResponse.getStatus() == HttpStatus.SC_BAD_REQUEST) {
            throw new NoSuchElementException(String.format("Bad request exception %s", paymentResponse.getString()));
        }
        throw new NoSuchElementException(String.format("status:%d , message %s", paymentResponse.getStatus(), paymentResponse.getString()));
    }


    private Response postData(Headers headers, PaymentRequest paymentRequest) {
        String url = String.format("/v1/split_payments?access_token=%s", paymentRequest.getAccessToken());
        try {
            return restClient.withPool("default").post(url, headers, JsonUtils.INSTANCE.toJsonString(paymentRequest).getBytes());
        } catch (JsonException jsonException) {
            throw new JsonParseException(jsonException);
        } catch (RestException e) {
            log.error("[method:post_data][exception:{}]", e.getMessage());
            throw new InternalError(String.format("[type:rest_request_exception][message:%s]", e.getMessage()), e);
        }
    }
}
