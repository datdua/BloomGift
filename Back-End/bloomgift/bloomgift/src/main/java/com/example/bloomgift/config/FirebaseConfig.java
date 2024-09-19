package com.example.bloomgift.config;

import java.io.FileInputStream;
import java.io.InputStream;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobClientBuilder;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.StorageClient;
import com.google.firebase.database.FirebaseDatabase;

@Configuration
public class FirebaseConfig {

    // Constants for Azure Blob Storage
    private static final String CONNECTION_STRING = "DefaultEndpointsProtocol=https;AccountName=bloomgift;AccountKey=FBqNhWFAiDzeDNZwVxiktTq/dO3o9XbyX/fcoCmQE3vl+n2BO+NisvoUMPuNdHS+LD3BqeFx1gaC+AStUKzi1A==;EndpointSuffix=core.windows.net";
    private static final String CONTAINER_NAME = "bloomgift";
    private static final String BLOB_NAME = "serviceAccountKey.json";

    // URL của Firebase Realtime Database
    private static final String DATABASE_URL = "https://bloom-gift-67f83-default-rtdb.firebaseio.com";

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
                .setStorageBucket("bloom-gift-67f83.appspot.com") // Thêm Firebase Storage bucket
                .setDatabaseUrl(DATABASE_URL) // Firebase Realtime Database URL
                .build();

        // Khởi tạo FirebaseApp
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

    @Bean
    public StorageClient firebaseStorageClient(FirebaseApp firebaseApp) {
        return StorageClient.getInstance(firebaseApp);
    }

}