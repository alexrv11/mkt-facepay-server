package com.hackaton.facepayapi.utils;

import com.mercadolibre.restclient.http.Headers;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.utils.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.NoSuchElementException;

public class RestRequestUtils {

    private Headers headers;
    private URI uri;
    private URIBuilder uriBuilder;

    private RestRequestUtils() {
    }

    public static RestRequestUtils.Builder builder() {
        return new RestRequestUtils.Builder(new RestRequestUtils());
    }

    public Headers getHeaders() {
        return headers;
    }

    public URI getUri() {
        return uri;
    }

    public URIBuilder getUriBuilder() {
        return uriBuilder;
    }

    public static class Builder {
        private final Logger log = LoggerFactory.getLogger(RestRequestUtils.Builder.class);
        private Headers headers;
        private RestRequestUtils request;
        private URIBuilder uriBuilder;
        static final String CALLER_ID = "caller.id";
        static final String CLIENT_ID = "client.id";
        static final String CONTENT_TYPE = "Content-Type";
        static final String ACCEPT = "Accept";
        static final String X_IDEMPOTENCY_ID = "X-Idempotency-Key";
        static final String X_PRODUCT_ID = "X-Product-Id";

        private Builder(RestRequestUtils restRequestUtils) {
            this.headers = new Headers();
            this.headers.add(ACCEPT, "application/json");
            this.headers.add(CONTENT_TYPE, "application/json");
            this.uriBuilder = new URIBuilder();
            this.request = restRequestUtils;
        }

        public RestRequestUtils.Builder addHeader(String key, String value) {
            this.headers.add(key, value);
            return this;
        }

        public RestRequestUtils.Builder setContentType(String value) {
            this.headers.add(CONTENT_TYPE, value);
            return this;
        }

        public RestRequestUtils.Builder setAccept(String value) {
            this.headers.add(ACCEPT, value);
            return this;
        }


        public RestRequestUtils.Builder addQueryParam(String key, String value) {
            if (StringUtils.isNotEmpty(value))
                this.uriBuilder.addParameter(key, value);
            return this;
        }

        public RestRequestUtils.Builder addCallerId(String value) {
            if (StringUtils.isNotEmpty(value))
                this.uriBuilder.addParameter(CALLER_ID, value);
            else
                throw new NoSuchElementException("Caller id is invalid");
            return this;
        }

        public RestRequestUtils.Builder addClientId(String value) {
            if (StringUtils.isNotEmpty(value))
                this.uriBuilder.addParameter(CLIENT_ID, value);
            else
                throw new NoSuchElementException("Client id is invalid");
            return this;
        }

        public RestRequestUtils.Builder addIdempotency(String value) {
            if (StringUtils.isNotEmpty(value))
                this.uriBuilder.addParameter(X_IDEMPOTENCY_ID, value);
            else
                throw new NoSuchElementException("Idempotency key is null");
            return this;
        }

        public RestRequestUtils.Builder withPath(String path) {
            this.uriBuilder.setPath(path);
            return this;
        }

        public RestRequestUtils.Builder addProductId(String value) throws InternalError {
            if (StringUtils.isNotEmpty(value))
                this.headers.add(X_PRODUCT_ID, value);
            else
                throw new InternalError("Product Id is null");
            return this;
        }

        public RestRequestUtils.Builder addTestToken(Boolean value) {
            this.headers.add("X-Test-Token", value.toString());
            return this;
        }

        public RestRequestUtils build() {
            try {
                this.request.headers = this.headers;
                this.request.uri = this.uriBuilder.build();
                this.request.uriBuilder = this.uriBuilder;
            } catch (URISyntaxException e) {
                log.error("[uri_path:][headers:{}] [query_params:{}]Uri resource malformed ", this.uriBuilder.getPath(), this.request.headers);
                throw new NoSuchElementException(String.format("URI resource malformed [exception_message:%s]", e.getMessage()));
            }
            return this.request;
        }
    }
}
