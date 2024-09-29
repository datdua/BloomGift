package com.example.bloomgift.controllers.FireBase;

import java.io.IOException;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.bloomgift.service.FirebaseStorageService;

@RestController
@RequestMapping("/api/upload-files/file-management")
public class FirebaseController {
    private final FirebaseStorageService firebaseStorageService;

    public FirebaseController(FirebaseStorageService firebaseStorageService) {
        this.firebaseStorageService = firebaseStorageService;
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "folderPath", defaultValue = "") String folderPath) {
        try {
            // Upload the file to Firebase Storage with the specified folder path
            String fileUrl = firebaseStorageService.uploadFileByCustomer(file, folderPath);
            return ResponseEntity.ok(fileUrl);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("File upload failed: " + e.getMessage());
        }
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable String fileName) {
        try {
            ByteArrayResource resource = firebaseStorageService.downloadFile(fileName);

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                    .body(resource);
        } catch (IOException e) {
            return ResponseEntity.status(404).body(null);
        }
    }
}
