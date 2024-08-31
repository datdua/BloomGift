package com.example.bloomgift.DTO;

import java.util.List;

public class GoogleSheetsDTO {

    private String sheetName;

    private List<List<Object>> dataToBeUpdated;

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public List<List<Object>> getDataToBeUpdated() {
        return dataToBeUpdated;
    }

    public void setDataToBeUpdated(List<List<Object>> dataToBeUpdated) {
        this.dataToBeUpdated = dataToBeUpdated;
    }
}
