package com.example.bloomgift.controllers.Google;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.bloomgift.DTO.GoogleSheetsDTO;
import com.example.bloomgift.service.GoogleSheetsService;

@RestController
@RequestMapping("/api/google-sheets")
public class GoogleSheetsController {

    @Autowired
    private GoogleSheetsService sheetsService;

    @GetMapping("/sheets/getData")
    public List<List<Object>> getSheetData(@RequestParam String spreadsheetId, @RequestParam String range) throws IOException, GeneralSecurityException {
        return sheetsService.getSheetData(spreadsheetId, range);
    }

    @PostMapping("/sheets/createSheet")
	public String createGoogleSheet(@RequestBody GoogleSheetsDTO request)
			throws GeneralSecurityException, IOException {
		return sheetsService.createSheet(request);
	}
}
