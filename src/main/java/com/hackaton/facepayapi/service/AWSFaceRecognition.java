package com.hackaton.facepayapi.service;

import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.rekognition.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.nio.ByteBuffer;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class AWSFaceRecognition {
    // replace bucket, collectionId, and photo with your values.
    public static final String collectionId = "FacePayCollection";
    private AmazonRekognition rekognitionClient;

    public AWSFaceRecognition() {
        rekognitionClient = AmazonRekognitionClientBuilder.standard().withRegion("us-east-1").build();
    }

    public ListFacesResult getCollection() {
        ListFacesRequest listFacesRequest = new ListFacesRequest()
                .withCollectionId(collectionId);
        return rekognitionClient.listFaces(listFacesRequest);


    }

    public DeleteFacesResult delete(String faces[]) {
        DeleteFacesRequest deleteFacesRequest = new DeleteFacesRequest()
                .withCollectionId(collectionId)
                .withFaceIds(faces);

        return rekognitionClient.deleteFaces(deleteFacesRequest);

    }

    public String uploadFace(String imageBase64) throws Exception {



        Image image = new Image().withBytes(getBytesFromImage(imageBase64));

        IndexFacesRequest indexFacesRequest = new IndexFacesRequest()
                .withImage(image)
                .withQualityFilter(QualityFilter.AUTO)
                .withMaxFaces(1)
                .withCollectionId(collectionId)
                .withDetectionAttributes("DEFAULT");

        IndexFacesResult indexFacesResult = rekognitionClient.indexFaces(indexFacesRequest);

        System.out.println("Faces indexed:");
        List<FaceRecord> faceRecords = indexFacesResult.getFaceRecords();
        for (FaceRecord faceRecord : faceRecords) {
            System.out.println("  Face ID: " + faceRecord.getFace().getFaceId());
            ObjectMapper map = new ObjectMapper();
            System.out.println(map.writeValueAsString(faceRecord.getFaceDetail()));

        }

        List<UnindexedFace> unindexedFaces = indexFacesResult.getUnindexedFaces();
        System.out.println("Faces not indexed:");
        for (UnindexedFace unindexedFace : unindexedFaces) {
            System.out.println("  Location:" + unindexedFace.getFaceDetail().getBoundingBox().toString());
            System.out.println("  Reasons:");
            for (String reason : unindexedFace.getReasons()) {
                System.out.println("   " + reason);
            }
        }
        return faceRecords.get(0).getFace().getFaceId();
    }

    public Optional<String> validateFace(String imageBase64) {

        Optional<String> res = Optional.empty();


        Image image = new Image().withBytes(getBytesFromImage(imageBase64));

        SearchFacesByImageRequest searchFacesByImageRequest = new SearchFacesByImageRequest()
                .withImage(image)
                .withFaceMatchThreshold(85F)
                .withMaxFaces(1)
                .withCollectionId(collectionId);

        SearchFacesByImageResult searchFacesByImageResult =
                rekognitionClient.searchFacesByImage(searchFacesByImageRequest);
        List < FaceMatch > faceImageMatches = searchFacesByImageResult.getFaceMatches();
        for (FaceMatch face: faceImageMatches) {
            System.out.println(face.getFace().getFaceId());
            System.out.println(face.getFace().getImageId());
            System.out.println(face.getFace().getConfidence());
            System.out.println(face.getSimilarity());
        }
        if (faceImageMatches.size() > 0) {
            res = Optional.of(faceImageMatches.get(0).getFace().getFaceId());
        }
        return res;
    }

    public Optional<String> createCollection() {

        System.out.println("Createing collection: " + collectionId);
        CreateCollectionRequest request = new CreateCollectionRequest()
                .withCollectionId(collectionId);

        CreateCollectionResult createCollectionResult = rekognitionClient.createCollection(request);
        System.out.println("CollectionArn : " +
                createCollectionResult.getCollectionArn());
        System.out.println("Status code : " +
                createCollectionResult.getStatusCode().toString());
        if (createCollectionResult.getStatusCode().intValue() == 200) {
            return Optional.of(collectionId);
        }
        return Optional.empty();
    }

    private ByteBuffer getBytesFromImage(String imageBase64){
        String image = imageBase64;
        return ByteBuffer.wrap(Base64.getDecoder().decode(image));
    }

}
