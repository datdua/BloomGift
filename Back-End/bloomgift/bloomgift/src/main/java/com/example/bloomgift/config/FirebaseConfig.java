// package com.example.bloomgift.config;

// import com.example.bloomgift.BloomgiftApplication;
// import com.google.auth.oauth2.GoogleCredentials;
// import com.google.firebase.FirebaseApp;
// import com.google.firebase.FirebaseOptions;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.core.io.ClassPathResource;

// import java.io.File;
// import java.io.FileInputStream;
// import java.io.IOException;

// @Configuration
// public class FirebaseConfig {
//     private static final String SERCURITY_FIREBASE = "serviceAccountKey.json";

//     @SuppressWarnings("deprecation")
//     @Bean
//     public FirebaseApp firebaseApp() throws IOException {
//         FileInputStream serviceAccount = new FileInputStream(new ClassPathResource(SERCURITY_FIREBASE).getFile());
        
//         FirebaseOptions options = new FirebaseOptions.Builder()
//                 .setCredentials(GoogleCredentials.fromStream(serviceAccount))
//                 .build();

//         return FirebaseApp.initializeApp(options);
//     }
// }