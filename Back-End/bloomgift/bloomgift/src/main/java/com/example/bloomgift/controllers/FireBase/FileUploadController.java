package com.example.bloomgift.controllers.FireBase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.bloomgift.service.FirebaseService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.IOException;

@RestController
@RequestMapping("/api/files")
public class FileUploadController {

    @Autowired
    private FirebaseService firebaseService;

    @Operation(summary = "Upload a file to Firebase Storage")
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadFile(
            @Parameter(description = "File to upload", content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE)) @RequestPart("file") MultipartFile file,
            @Parameter(description = "Folder name in Firebase Storage") @RequestPart("folderName") String folderName) {
        try {
            String fileUrl = firebaseService.uploadFile(file, folderName);
            return ResponseEntity.ok("File uploaded successfully: " + fileUrl);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("File upload failed: " + e.getMessage());
        }
    }

    @Operation(summary = "Create a folder in Firebase Storage")
    @PostMapping("/create-folder")
    public ResponseEntity<String> createFolder(
            @Parameter(description = "Folder name to create") @RequestParam("folderName") String folderName) {
        firebaseService.createFolder(folderName);
        return ResponseEntity.ok("Folder created successfully");
    }
}