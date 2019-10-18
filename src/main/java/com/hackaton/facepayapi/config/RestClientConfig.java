package com.hackaton.facepayapi.config;

import com.mercadolibre.restclient.RESTPool;
import com.mercadolibre.restclient.RestClient;
import com.mercadolibre.restclient.cache.local.RESTLocalCache;
import com.mercadolibre.restclient.retry.SimpleRetryStrategy;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.*;


@Configuration
@EnableAutoConfiguration
public class RestClientConfig {
    private Map<String, RESTPool> restPoolHashMap;

    @Bean
    public RestClient restClient() throws IOException {
        List<RESTPool> restPools = new ArrayList<>();
        restPoolHashMap = new HashMap<>();

        RESTPool.Builder restPoolBuilder = RESTPool
                .builder()
                .withName("default")
                .withSocketTimeout(10000)
                .withConnectionTimeout(10000)
                .withMaxTotal(80)
                .withMaxPerRoute(80)
                .withMaxPoolWait(100)
                .withRetryStrategy(new SimpleRetryStrategy(2, 30))
                .withBaseURL("https://api.mercadopago.com/");

        restPools.add(restPoolBuilder.build());

        return RestClient.builder().withPool(restPools.toArray(new RESTPool[restPools.size()])).build();
    }


}
