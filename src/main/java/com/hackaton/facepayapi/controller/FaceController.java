package com.hackaton.facepayapi.controller;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.rekognition.model.CreateCollectionRequest;
import com.amazonaws.services.rekognition.model.CreateCollectionResult;
import com.hackaton.facepayapi.models.FaceLogin;
import com.hackaton.facepayapi.service.AddFacesToCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Base64;

@RestController
public class FaceController {

    @Autowired
    private AddFacesToCollection addFacesToCollection;

    @PostMapping("/facetest")
    public String facetest(@RequestBody FaceLogin login) {
        String image = login.getFace();
        byte[] decodedBytes = Base64.getDecoder().decode(image);
        writeByte(decodedBytes);

        return "pong";
    }

    @PostMapping("/facelogin")
    public String faceLogin(@RequestBody FaceLogin login) {
        String image = login.getFace();
        byte[] decodedBytes = Base64.getDecoder().decode(image);
        try {
            return addFacesToCollection.loadImage(decodedBytes);
        }catch (Exception e) {
            e.printStackTrace();
        }

        //writeByte(decodedBytes);

        return "no cargue nada";
    }

    @GetMapping("/facelogin")
    public String validateLogin(@RequestBody FaceLogin login) {
        String image = login.getFace();
        byte[] decodedBytes = Base64.getDecoder().decode(image);
        return addFacesToCollection.validateIfImageIsInCollection(decodedBytes);

    }

    @PostMapping("/createcollection")
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

    static void writeByte(byte[] bytes)
    {
        try {

            // Initialize a pointer
            // in file using OutputStream
            String FILEPATH = "demo.jpeg";
            File file = new File(FILEPATH);
            OutputStream
                    os
                    = new FileOutputStream(file);

            // Starts writing the bytes in it
            os.write(bytes);
            System.out.println("Successfully"
                    + " byte inserted");

            // Close the file
            os.close();
        }

        catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }

}
