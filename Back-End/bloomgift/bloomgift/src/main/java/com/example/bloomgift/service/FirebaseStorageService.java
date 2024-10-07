package com.example.bloomgift.service;

import com.google.cloud.storage.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class FirebaseStorageService {
    private static final Logger logger = Logger.getLogger(FirebaseStorageService.class.getName());
    private static final String BUCKET_NAME = "bloomgift2.appspot.com";

    private final Storage storage;

    @Autowired
    public FirebaseStorageService(Storage storage) {
        this.storage = storage;
    }

    public String getDownloadUrl(String filePath) throws IOException {
        Blob blob = storage.get(BlobId.of(BUCKET_NAME, filePath));
        if (blob != null) {
            return blob.getMediaLink();
        } else {
            throw new IOException("File not found: " + filePath);
        }
    }

    private String uploadFile(MultipartFile file, String folderPath) throws IOException {
        String fileName = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();
        String fullPath = folderPath + fileName;

        BlobId blobId = BlobId.of(BUCKET_NAME, fullPath);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                .setContentType(file.getContentType())
                .build();

        storage.create(blobInfo, file.getBytes());
        storage.createAcl(blobId, Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER));

        logger.info("File " + fullPath + " uploaded successfully to bucket: " + BUCKET_NAME);
        return "https://storage.googleapis.com/" + BUCKET_NAME + "/" + fullPath;
    }

    public String uploadFileByAdmin(MultipartFile file, String email) throws IOException {
        String sanitizedEmail = email.replaceAll("[@.]", "_");
        String folderPath = "authen/user-admin/avatar/" + sanitizedEmail + "/";
        return uploadFile(file, folderPath);
    }

    public String uploadFileByCustomer(MultipartFile file, String email) throws IOException {
        String sanitizedEmail = email.replaceAll("[@.]", "_");
        String folderPath = "authen/user-customer/avatar/" + sanitizedEmail + "/";
        return uploadFile(file, folderPath);
    }

    public String uploadFileByStore(MultipartFile file, String email) throws IOException {
        String sanitizedStoreName = email.replaceAll("[@.]", "_");
        String folderPath = "authen/seller/avatar" + sanitizedStoreName + "/";
        return uploadFile(file, folderPath);
    }

    public String uploadFileByProduct(MultipartFile file, String storeName, String productName) throws IOException {
        String sanitizedStoreName = storeName.replaceAll("[^a-zA-Z0-9]", "_");
        String folderPath = "products/" + sanitizedStoreName + "/" + productName + "/";
        return uploadFile(file, folderPath);
    }

    public String updateImagetoFireBase(MultipartFile file, String folder) throws IOException {
        return uploadFile(file, folder);
    }

    public void deleteFileFromFirebase(String fileUrl, String productName, String storeName) {
        try {
            String sanitizedStoreName = storeName.replaceAll("[^a-zA-Z0-9]", "_");
            String folderPath = "products/" + sanitizedStoreName + "/" + productName + "/";
            String decodedUrl = URLDecoder.decode(fileUrl, StandardCharsets.UTF_8);
            String fileName = decodedUrl.substring(decodedUrl.lastIndexOf("/") + 1, decodedUrl.indexOf("?"));
            String fullPath = folderPath + fileName;

            BlobId blobId = BlobId.of(BUCKET_NAME, fullPath);
            boolean deleted = storage.delete(blobId);

            if (deleted) {
                logger.info("File deleted successfully: " + fileName);
            } else {
                logger.warning("File not found or not deleted: " + fileName);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error deleting file", e);
        }
    }

    public void deleteFile(String fileUrl) {
        try {
            URL url = new URL(fileUrl);
            String path = url.getPath();
            String decodedPath = URLDecoder.decode(path, StandardCharsets.UTF_8);
            String objectName = decodedPath.replaceFirst("/" + BUCKET_NAME + "/", "");
            BlobId blobId = BlobId.of(BUCKET_NAME, objectName);
            boolean deleted = storage.delete(blobId);

            if (deleted) {
                logger.info("File deleted successfully: " + fileUrl);
            } else {
                logger.warning("File not found or not deleted: " + fileUrl);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error deleting file: " + fileUrl, e);
        }
    }

    public ByteArrayResource downloadFile(String fileName) throws IOException {
        Blob blob = storage.get(BlobId.of(BUCKET_NAME, fileName));

        if (blob == null) {
            logger.warning("File " + fileName + " not found in bucket: " + BUCKET_NAME);
            throw new IOException("File not found: " + fileName);
        }

        byte[] content = blob.getContent();
        logger.info("File " + fileName + " downloaded successfully.");
        return new ByteArrayResource(content);
    }

    public void deleteFileFromURL(String imageUrl) {
        try {
            String fileName = URLDecoder.decode(imageUrl.substring(imageUrl.lastIndexOf("/") + 1),
                    StandardCharsets.UTF_8);
            BlobId blobId = BlobId.of(BUCKET_NAME, fileName);
            boolean deleted = storage.delete(blobId);
            if (!deleted) {
                throw new RuntimeException("Failed to delete file: " + fileName);
            }
        } catch (StorageException e) {
            throw new RuntimeException("Failed to delete file from Firebase: " + imageUrl, e);
        }
    }
}