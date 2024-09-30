package com.example.bloomgift.config;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.FirebaseDatabase;

@Configuration
public class FirebaseConfig {
    private static final String SERCURITY_FIREBASE = "serviceAccountKey.json";
    private static final String DATABASE_URL = "https://bloom-gift-67f83-default-rtdb.firebaseio.com";

    @SuppressWarnings("deprecation")
    @Bean
    public FirebaseApp firebaseApp() throws Exception {
        InputStream serviceAccount;

        try {
            // Truy xuất tệp từ Azure Blob Storage
            BlobClient blobClient = new BlobClientBuilder()
                    .connectionString(CONNECTION_STRING)
                    .containerName(CONTAINER_NAME)
                    .blobName(BLOB_NAME)
                    .buildClient();
            serviceAccount = blobClient.openInputStream();
        } catch (Exception e) {
            // Nếu không truy cập được từ Azure Blob Storage, thử từ file trong resources
            // của ứng dụng
            serviceAccount = new FileInputStream(new ClassPathResource("serviceAccountKey.json").getFile());
        }

        // Cấu hình Firebase từ tệp serviceAccountKey.json
        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl(DATABASE_URL)
                .build();

        if (FirebaseApp.getApps().isEmpty()) {
            return FirebaseApp.initializeApp(options);
        } else {
            return FirebaseApp.getInstance();
        }
    }

    @Bean
    public FirebaseDatabase firebaseDatabase(FirebaseApp firebaseApp) {
        return FirebaseDatabase.getInstance(firebaseApp);
    }
}