package com.QuizApp.Firebase;

import javax.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
//import org.springframework.core.io.Resource;

@Service
public class firebaseInitialization {

	@Value("classpath:serviceAccountKey.json")
    private Resource resource;

    @PostConstruct
    public void Initialization() {

        try {
            FirebaseOptions options = new FirebaseOptions.Builder()
                  .setCredentials(GoogleCredentials.fromStream(resource.getInputStream()))
                  .build();

            FirebaseApp.initializeApp(options);
        } catch (Exception e) {
            System.out.println("something went wrong in firebase initialization ...");
            System.out.println(e);
        }

    } 
}