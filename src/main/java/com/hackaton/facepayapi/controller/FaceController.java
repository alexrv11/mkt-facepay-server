package com.hackaton.facepayapi.controller;

import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.rekognition.model.CreateCollectionRequest;
import com.amazonaws.services.rekognition.model.CreateCollectionResult;
import com.hackaton.facepayapi.models.FaceLogin;
import com.hackaton.facepayapi.service.AddFacesToCollection;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class FaceController {

    @Autowired
    private AddFacesToCollection addFacesToCollection;

    @PostMapping("/payer/face")
    public String registerFace(@RequestBody FaceLogin login) {
        try {
            String faceId = addFacesToCollection.uploadFace(login.getFace());
            String applicationId = "2330173696820881";
            String redirectUrl = "https%3A%2F%2Fthawing-wildwood-80127.herokuapp.com/payer/" + faceId + "/register";
            return "https://auth.mercadopago.com.ar/authorization?client_id=" + applicationId + "&response_type=token&platform_id=mp&state=iframe&display=popup&interactive=1&scopes=wallet-payments&redirect_uri=" + redirectUrl;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return StringUtils.EMPTY;
    }


    // Only used to add a new face to the collection
    @PostMapping("/facelogin")
    public String faceLogin(@RequestBody FaceLogin login) {

        try {
            return addFacesToCollection.uploadFace(login.getFace());
        }catch (Exception e) {
            e.printStackTrace();
        }

        //writeByte(decodedBytes);

        return "no cargue nada";
    }

    //Deprecated
    @GetMapping("/facelogin")
    public ResponseEntity<String> validateLogin(@RequestBody FaceLogin login) {

        Optional<String> faceID = addFacesToCollection.validateFace(login.getFace());
        if (!faceID.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.accepted().body(faceID.get());

    }

    // Internal method to create a new collection.
    public String createCollection(){
        AmazonRekognition rekognitionClient = AmazonRekognitionClientBuilder.standard().withRegion("us-east-1").build();//.defaultClient();

        //Replace collectionId with the name of the collection that you want to create.

        String collectionId = "FacePayCollection";
        System.out.println("Creating collection: " +
                collectionId );

        CreateCollectionRequest request = new CreateCollectionRequest()
                .withCollectionId(collectionId);

        CreateCollectionResult createCollectionResult = rekognitionClient.createCollection(request);
        System.out.println("CollectionArn : " +
                createCollectionResult.getCollectionArn());
        System.out.println("Status code : " +
                createCollectionResult.getStatusCode().toString());
        return "ok";

    }

}