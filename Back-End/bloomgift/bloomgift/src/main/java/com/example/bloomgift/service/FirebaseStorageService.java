package com.example.bloomgift.service;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageException;
import com.google.cloud.storage.StorageOptions;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;
import java.util.logging.Level;

import java.util.logging.Logger;
@Service
public class FirebaseStorageService {
    private static final Logger logger = Logger.getLogger(FirebaseStorageService.class.getName());
    private final String BUCKET_NAME = "hello-f97a5.appspot.com";

    public String uploadFileByAdmin(MultipartFile file, String email) throws IOException {
        Storage storage = StorageOptions.getDefaultInstance().getService();
        Bucket bucket = storage.get(BUCKET_NAME);

        String sanitizedEmail = email.replaceAll("[@.]", "_");
        String folderPath = "Admin-Profile/" + sanitizedEmail +"/";
        String fileName = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();
        String fullPath = folderPath + fileName;

        try {
            Blob blob = bucket.create(fullPath, file.getBytes(), file.getContentType() != null ? file.getContentType() : "application/octet-stream");
            logger.info("File " + fullPath + " uploaded successfully to bucket: " + BUCKET_NAME);
            return blob.getMediaLink();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error uploading file", e);
            throw new IOException("Failed to upload file", e);
        }

    }
    public String uploadFileByCustomer(MultipartFile file, String email) throws IOException {
        Storage storage = StorageOptions.getDefaultInstance().getService();
        Bucket bucket = storage.get(BUCKET_NAME);

        String sanitizedEmail = email.replaceAll("[@.]", "_");
        String folderPath = "Customer-Profile/" + sanitizedEmail +"/";
        String fileName = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();
        String fullPath = folderPath + fileName;

        try {
            Blob blob = bucket.create(fullPath, file.getBytes(), file.getContentType() != null ? file.getContentType() : "application/octet-stream");
            logger.info("File " + fullPath + " uploaded successfully to bucket: " + BUCKET_NAME);
            return blob.getMediaLink();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error uploading file", e);
            throw new IOException("Failed to upload file", e);
        }
    }
    public void deleteFile(String filePath) {
        Storage storage = StorageOptions.getDefaultInstance().getService();
        Bucket bucket = storage.get(BUCKET_NAME);

        try {
            Blob blob = bucket.get(filePath);
            if (blob != null) {
                blob.delete();
                logger.info("File " + filePath + " deleted successfully from bucket: " + BUCKET_NAME);
            } else {
                logger.warning("File " + filePath + " not found in bucket: " + BUCKET_NAME);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error deleting file", e);
        }
    }

    public ByteArrayResource downloadFile(String fileName) throws IOException {
        Storage storage = StorageOptions.getDefaultInstance().getService();
        Bucket bucket = storage.get(BUCKET_NAME);

        Blob blob = bucket.get(fileName);

        if (blob == null || !blob.exists()) {
            logger.warning("File " + fileName + " not found in bucket: " + BUCKET_NAME);
            throw new IOException("File not found: " + fileName);
        }

        try {
            byte[] content = blob.getContent();
            logger.info("File " + fileName + " downloaded successfully.");
            return new ByteArrayResource(content);
        } catch (StorageException e) {
            logger.log(Level.SEVERE, "Error downloading file: " + fileName, e);
            throw new IOException("Failed to download file: " + fileName, e);
        }
    }
}
