package com.example.bloomgift.service;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageException;
import com.google.cloud.storage.StorageOptions;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.net.URLDecoder;
import java.io.UnsupportedEncodingException;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.logging.Level;

import java.util.logging.Logger;

@Service
public class FirebaseStorageService {
    private static final Logger logger = Logger.getLogger(FirebaseStorageService.class.getName());
    private final String BUCKET_NAME = "bloom-gift-67f83.appspot.com";

    public String uploadFileByAdmin(MultipartFile file, String email) throws IOException {
        Storage storage = StorageOptions.getDefaultInstance().getService();
        Bucket bucket = storage.get(BUCKET_NAME);

        String sanitizedEmail = email.replaceAll("[@.]", "_");
        String folderPath = "authen/" + "user-admin/" + "avatar/" + sanitizedEmail + "/";
        String fileName = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();
        String fullPath = folderPath + fileName;

        try {
            Blob blob = bucket.create(fullPath, file.getBytes(),
                    file.getContentType() != null ? file.getContentType() : "application/octet-stream");
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
        String folderPath = "authen/" + "user-customer/" + "avatar/" + sanitizedEmail + "/";
        String fileName = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();
        String fullPath = folderPath + fileName;

        try {
            Blob blob = bucket.create(fullPath, file.getBytes(),
                    file.getContentType() != null ? file.getContentType() : "application/octet-stream");
            logger.info("File " + fullPath + " uploaded successfully to bucket: " + BUCKET_NAME);
            return blob.getMediaLink();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error uploading file", e);
            throw new IOException("Failed to upload file", e);
        }
    }

    public String uploadFileByProduct(MultipartFile file, String storeName, String productName) throws IOException {
        Storage storage = StorageOptions.getDefaultInstance().getService();
        Bucket bucket = storage.get(BUCKET_NAME);
        String sanitizedStoreName = storeName.replaceAll("[^a-zA-Z0-9]", "_");
        String folderPath = "products/" + sanitizedStoreName + "/" + productName + "/";
        String fileName = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();

        String fullPath = folderPath + fileName;
        try {
            Blob blob = bucket.create(fullPath, file.getBytes(),
                    file.getContentType() != null ? file.getContentType() : "application/octet-stream");
            logger.info("File " + fullPath + " uploaded successfully to bucket: " + BUCKET_NAME);
            
            return blob.getMediaLink();
     
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error uploading file", e);
            throw new IOException("Failed to upload file", e);
        }
    }
 


    public String updateImagetoFireBase(MultipartFile file, String folder) throws IOException {
        Storage storage = StorageOptions.getDefaultInstance().getService();
        Bucket bucket = storage.get(BUCKET_NAME);
        String fileName = folder + UUID.randomUUID().toString() + "-" + file.getOriginalFilename();
        Blob blob = bucket.create(fileName, file.getBytes(), file.getContentType());
        return blob.getMediaLink();
    }

    public void deleteFileFromFirebase(String fileUrl,String productName, String storeName) throws UnsupportedEncodingException  {
        String sanitizedStoreName = storeName.replaceAll("[^a-zA-Z0-9]", "_");
        String folderPath = "products/" + sanitizedStoreName + "/" + productName + "/";
        String decodedUrl = URLDecoder.decode(fileUrl, "UTF-8");
        System.out.println("Decoded URL: " + decodedUrl);
        
        String fileName = decodedUrl.substring(decodedUrl.lastIndexOf("/") + 1, decodedUrl.indexOf("?"));
        System.out.println("File Name: " + fileName);
        String fullPath = folderPath + fileName;

        Storage storage = StorageOptions.getDefaultInstance().getService();
        
        Bucket bucket = storage.get(BUCKET_NAME);
        if (bucket == null) {
            System.out.println("Bucket not found: " + BUCKET_NAME);
            return;
        }
        
        Blob blob = bucket.get(fullPath);
        if (blob == null) {
            System.out.println("Blob not found for file: " + fileName);
            return;
        }
        
        blob.delete();
        System.out.println("File deleted successfully: " + fileName);
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
