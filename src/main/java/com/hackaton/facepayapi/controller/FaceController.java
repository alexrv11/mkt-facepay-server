package com.hackaton.facepayapi.controller;

import com.hackaton.facepayapi.models.FaceLogin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Base64;

@RestController
public class FaceController {

    @PostMapping("/facelogin")
    public String faceLogin(@RequestBody FaceLogin login) {
        String image = login.getFace();
        byte[] decodedBytes = Base64.getDecoder().decode(image);
        writeByte(decodedBytes);

        return "pong";
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
