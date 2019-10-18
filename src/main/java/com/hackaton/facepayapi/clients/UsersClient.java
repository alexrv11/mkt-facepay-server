package com.hackaton.facepayapi.clients;

import com.hackaton.facepayapi.models.AccessTokenResponse;
import com.hackaton.facepayapi.models.UsersResponse;
import com.hackaton.facepayapi.utils.RestRequestUtils;
import com.mercadolibre.json.exception.JsonException;
import com.mercadolibre.restclient.Response;
import com.mercadolibre.restclient.RestClient;
import com.mercadolibre.restclient.exception.RestException;
import com.mercadolibre.restclient.http.Headers;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
public class UsersClient {
    @Autowired
    private RestClient restClient;

    protected final Logger log = LoggerFactory.getLogger(UsersClient.class);

    public UsersResponse getUserById(AccessTokenResponse accessTokenResponse) {

        RestRequestUtils restRequestUtils = RestRequestUtils.builder()
                .withPath(String.format("/users/%s", accessTokenResponse.getUserId())).addQueryParam("access_token", accessTokenResponse.getAccessToken()).build();
        Response response = getResource(restRequestUtils.getUri().toString(), restRequestUtils.getHeaders());
        try {
            if (response.getStatus() == HttpStatus.SC_OK)
                return com.mercadolibre.json.JsonUtils.INSTANCE.toObject(response.getString(), UsersResponse.class);
        } catch (JsonException jsonException) {
            throw new NoSuchElementException();
        }
        throw new NoSuchElementException();
    }

    public Response getResource(String url, Headers headers) {
        try {
            return restClient.withPool("default").get(url, headers);
        } catch (RestException restException) {
            log.error("[method:post_data][exception:{}]", restException.getMessage());
            throw new InternalError(String.format("[type:rest_request_exception][message:%s]", restException.getMessage()), restException);
        }
    }
}
