package com.example.bloomgift.service;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class FirebaseService {

    private final Storage storage;
    private final String bucketName;

    @Autowired
    public FirebaseService(Storage storage) {
        this.storage = storage;
        this.bucketName = "bloomgift2.appspot.com";
    }

    public String uploadFile(MultipartFile file, String folderName) throws IOException {
        String fileName = folderName + "/" + file.getOriginalFilename();
        BlobId blobId = BlobId.of(bucketName, fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(file.getContentType()).build();

        Blob blob = storage.create(blobInfo, file.getBytes());

        return blob.getMediaLink();
    }

    public void createFolder(String folderName) {
        BlobId blobId = BlobId.of(bucketName, folderName + "/.placeholder");
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();

        storage.create(blobInfo, new byte[0]);
    }
}