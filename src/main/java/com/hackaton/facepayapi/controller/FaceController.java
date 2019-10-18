package com.hackaton.facepayapi.controller;

import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.rekognition.model.CreateCollectionRequest;
import com.amazonaws.services.rekognition.model.CreateCollectionResult;
import com.hackaton.facepayapi.models.FaceLogin;
import com.hackaton.facepayapi.models.FaceValidationResult;
import com.hackaton.facepayapi.service.AddFacesToCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Base64;
import java.util.Optional;

@RestController
public class FaceController {

    @Autowired
    private AddFacesToCollection addFacesToCollection;


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
    public ResponseEntity<FaceValidationResult> validateLogin(@RequestBody FaceLogin login) {

        Optional<String> faceID = addFacesToCollection.validateFace(login.getFace());
        if (!faceID.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.accepted().body(new FaceValidationResult(faceID.get()));

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