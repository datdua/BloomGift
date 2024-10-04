package com.example.bloomgift.utils;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponseException;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;

@Component
public class GoogleSheetsUtil {

    private static final Logger logger = LoggerFactory.getLogger(GoogleSheetsUtil.class);

    private static final String APPLICATION_NAME = "Google Sheets API Java Quickstart";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens/path";
    private static final List<String> SCOPES = Arrays.asList(SheetsScopes.SPREADSHEETS, SheetsScopes.DRIVE);
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";

    private GoogleAuthorizationCodeFlow flow;

    public GoogleSheetsUtil() throws IOException {
        NetHttpTransport HTTP_TRANSPORT;
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            InputStream in = GoogleSheetsUtil.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
            if (in == null) {
                throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
            }
            GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

            flow = new GoogleAuthorizationCodeFlow.Builder(
                    HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                    .setDataStoreFactory(new FileDataStoreFactory(
                            new java.io.File(System.getProperty("user.home"), TOKENS_DIRECTORY_PATH)))
                    .setAccessType("offline")
                    .build();
        } catch (GeneralSecurityException e) {
            throw new RuntimeException("Error initializing Google Sheets util", e);
        }
    }

    public String getAuthorizationUrl() {
        String authorizationUrl = flow.newAuthorizationUrl()
                .setRedirectUri("https://bloomgift-bloomgift.azuremicroservices.io/Callback")
                .build();
        logger.info("Generated authorization URL: {}", authorizationUrl);
        return authorizationUrl;
    }

    public void storeCredential(String code) throws IOException {
        try {
            logger.info("Attempting to store credential with authorization code: {}", code);
            flow.createAndStoreCredential(flow.newTokenRequest(code)
                    .setRedirectUri("https://bloomgift-bloomgift.azuremicroservices.io/Callback").execute(), "user");
            logger.info("Credential stored successfully");
        } catch (TokenResponseException e) {
            logger.error("TokenResponseException: {}", e.getDetails().getErrorDescription(), e);
            throw new IOException("Failed to store credential: " + e.getDetails().getErrorDescription(), e);
        } catch (IOException e) {
            logger.error("IOException occurred while storing credential", e);
            throw e;
        }
    }

    public Credential getCredentials() throws IOException {
        logger.info("Loading credentials for user");
        Credential credential = flow.loadCredential("user");
        if (credential == null) {
            logger.warn("No credentials found for user");
        } else {
            logger.info("Credentials loaded successfully");
        }
        return credential;
    }

    public Sheets getSheetService() throws IOException {
        logger.info("Creating Sheets service");
        return new Sheets.Builder(flow.getTransport(), JSON_FACTORY, getCredentials())
                .setApplicationName(APPLICATION_NAME)
                .build();
    }
}