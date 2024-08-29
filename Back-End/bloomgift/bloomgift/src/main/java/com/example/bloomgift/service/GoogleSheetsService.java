package com.example.bloomgift.service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bloomgift.DTO.GoogleSheetsDTO;
import com.example.bloomgift.utils.GoogleSheetsUtil;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.Sheet;
import com.google.api.services.sheets.v4.model.SheetProperties;
import com.google.api.services.sheets.v4.model.Spreadsheet;
import com.google.api.services.sheets.v4.model.SpreadsheetProperties;
import com.google.api.services.sheets.v4.model.ValueRange;

@Service
public class GoogleSheetsService {

    @Autowired
    private GoogleSheetsUtil googleSheetsUtil;

    public List<List<Object>> getSheetData(String spreadsheetId, String range) throws IOException, GeneralSecurityException {
        Sheets service = googleSheetsUtil.getSheetService();
        ValueRange response = service.spreadsheets().values().get(spreadsheetId, range).execute();
        return response.getValues();
    }

    public String createSheet(GoogleSheetsDTO request) throws GeneralSecurityException, IOException {
        Sheets service = googleSheetsUtil.getSheetService();

        SpreadsheetProperties properties = new SpreadsheetProperties();
        properties.setTitle(request.getSheetName());

        SheetProperties sheetProperties = new SheetProperties();
        sheetProperties.setTitle(request.getSheetName());

        Sheet sheet = new Sheet().setProperties(sheetProperties);

        Spreadsheet spreadsheet = new Spreadsheet()
                .setProperties(properties)
                .setSheets(Collections.singletonList(sheet));

        return service.spreadsheets().create(spreadsheet).execute().getSpreadsheetUrl();
    }

    // public String updateSheet(GoogleSheetsDTO request) throws GeneralSecurityException, IOException {
    //     Sheets service = googleSheetsUtil.getSheetService();

    //     ValueRange body = new ValueRange()
    //             .setValues(request.getValues());

    //     service.spreadsheets().values()
    //             .update(request.getSpreadsheetId(), request.getRange(), body)
    //             .setValueInputOption("RAW")
    //             .execute();

    //     return "Sheet updated successfully";
    // }
}
