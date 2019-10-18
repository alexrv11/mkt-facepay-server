package com.hackaton.facepayapi.services;

import com.hackaton.facepayapi.clients.AccessTokenClient;
import com.hackaton.facepayapi.clients.UsersClient;
import com.hackaton.facepayapi.daos.UsersEntity;
import com.hackaton.facepayapi.models.AccessTokenRequest;
import com.hackaton.facepayapi.models.AccessTokenResponse;
import com.hackaton.facepayapi.models.UsersResponse;
import com.hackaton.facepayapi.repositories.UsersRepository;
import com.mercadolibre.json.exception.JsonException;
import net.sf.json.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class RegistrationService {
    @Autowired
    private AccessTokenClient accessTokenClient;
    @Autowired
    private UsersClient usersClient;

    @Autowired
    private UsersRepository usersRepository;

    public Boolean userRegistration(String faceId, String mpConnectCode) {

        AccessTokenRequest accessTokenRequest = new AccessTokenRequest();
        accessTokenRequest.setClientSecret("qoD69E68L2Pfw4hk6FwoZJA1XnTABrae");
        accessTokenRequest.setCode(mpConnectCode);
        accessTokenRequest.setApplicationId("6078376556362919");
//        accessTokenRequest.setRedirectUri("https://www.google.com");
        accessTokenRequest.setRedirectUri(String.format("https://thawing-wildwood-80127.herokuapp.com/payer/%s/register", faceId));

        try {
            AccessTokenResponse accessTokenResponse = accessTokenClient.getPayerAccessToken(accessTokenRequest);
            UsersResponse usersResponse = usersClient.getUserById(accessTokenResponse);
            UsersEntity usersEntity = new UsersEntity();
            usersEntity.setId(usersResponse.getId());
            usersEntity.setUserName(usersResponse.getNickname());
            usersEntity.setAccessToken(accessTokenResponse.getAccessToken());
            usersEntity.setFaceId(faceId);
            usersEntity.setPassword("123");
            usersEntity.setUserType("payer");
            usersEntity.setUserId(accessTokenResponse.getUserId());
            usersRepository.save(usersEntity);
            return true;
        } catch (JsonException jsonException) {
            return false;
        }
    }
}
