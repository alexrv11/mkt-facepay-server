package com.hackaton.facepayapi.clients;

import com.hackaton.facepayapi.models.AccessTokenResponse;
import com.hackaton.facepayapi.models.AccessTokenRequest;
import com.hackaton.facepayapi.utils.RestRequestUtils;
import com.mercadolibre.json.JsonUtils;
import com.mercadolibre.json.exception.JsonException;
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

import java.util.NoSuchElementException;

@Component
public class AccessTokenClient {

    protected final Logger log = LoggerFactory.getLogger(AccessTokenClient.class);

    @Autowired
    private RestClient restClient;

    public AccessTokenResponse getPayerAccessToken(AccessTokenRequest accessTokenRequest) throws JsonException {
        RestRequestUtils restRequestUtils = RestRequestUtils.builder().withPath("/oauth/token").build();
        Response response = postData(restRequestUtils.getUri().toString(), restRequestUtils.getHeaders(), accessTokenRequest);
        if (response.getStatus() == HttpStatus.SC_OK || response.getStatus() == HttpStatus.SC_CREATED) {
            return JsonUtils.INSTANCE.toObject(response.getString(), AccessTokenResponse.class);
        }
        throw new NoSuchElementException(String.format("status:%d , message %s", response.getStatus(), response.getString()));
    }

    private Response postData(String url, Headers headers, AccessTokenRequest accessTokenRequest) {
        try {
            return restClient.withPool("default").post(url, headers, JsonUtils.INSTANCE.toJsonString(accessTokenRequest).getBytes());
        } catch (JsonException jsonException) {
            throw new JsonParseException(jsonException);
        } catch (RestException e) {
            log.error("[method:post_data][exception:{}]", e.getMessage());
            throw new InternalError(String.format("[type:rest_request_exception][message:%s]", e.getMessage()), e);
        }
    }
}
