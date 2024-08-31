package com.example.bloomgift.service;

import com.example.bloomgift.model.Account;
import com.example.bloomgift.model.Comment;
import com.example.bloomgift.model.Product;
import com.example.bloomgift.model.Store;
import com.example.bloomgift.repository.AccountRepository;
import com.example.bloomgift.repository.ProductRepository;
import com.example.bloomgift.repository.StoreRepository;
import com.example.bloomgift.utils.GoogleSheetsUtil;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class CommentService {
    @Autowired
    private GoogleSheetsUtil googleSheetsUtil;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private StoreRepository storeRepository;

    private static final String SPREADSHEET_ID = "1PFl-bzRUvPVpjNASFzR-mHioByBmRTx-OsoxL2j3eEo";
    private static final String RANGE = "Sheet1";

    public List<List<Object>> viewComments() throws Exception {
        try {
            Sheets service = googleSheetsUtil.getSheetService();
            ValueRange response = service.spreadsheets().values()
                    .get(SPREADSHEET_ID, RANGE)
                    .execute();
            List<List<Object>> values = response.getValues();
            if (values == null || values.isEmpty()) {
                System.out.println("No comments found.");
                return new ArrayList<>();
            } else {
                return values;
            }
        } catch (Exception e) {
            System.out.println("Error viewing comments: " + e.getMessage());
            throw e;
        }
    }

    public List<List<Object>> getCommentsByProductID(Integer productID) throws Exception {
        try {
            Sheets service = googleSheetsUtil.getSheetService();
            ValueRange response = service.spreadsheets().values()
                    .get(SPREADSHEET_ID, RANGE)
                    .execute();
            List<List<Object>> values = response.getValues();
            List<List<Object>> filteredComments = new ArrayList<>();
            if (values != null && !values.isEmpty()) {
                for (List<Object> row : values) {
                    if (row.size() > 4 && row.get(4) != null && row.get(4).toString().equals(productID.toString())) {
                        String statusValue = row.size() > 13 ? row.get(13).toString() : "FALSE";
                        System.out.println("Status Value: " + statusValue);
                        if ("FALSE".equalsIgnoreCase(statusValue.trim())) {
                            filteredComments.add(row);
                        }
                    }
                }
            }
            return filteredComments;
        } catch (Exception e) {
            System.out.println("Error retrieving comments: " + e.getMessage());
            throw e;
        }
    }

    public void addCommentToSheet(Integer accountID, Integer productID, String commentContent, int rating)
            throws Exception {
        try {
            Account account = accountRepository.findById(accountID)
                    .orElseThrow(() -> new RuntimeException("Account not found"));
            Product product = productRepository.findById(productID)
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            LocalDate currentDate = LocalDate.now();
            String formattedDate = currentDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            Boolean status = false;
            Boolean report = false;
            String reason = "";
            Integer storeID = product.getStoreID().getStoreID();
            String storeName = product.getStoreID().getStoreName();
            Sheets service = googleSheetsUtil.getSheetService();

            String range = RANGE + "!A:A";
            ValueRange response = service.spreadsheets().values()
                    .get(SPREADSHEET_ID, range)
                    .execute();
            List<List<Object>> values = response.getValues();
            int nextCommentID = 1;
            if (values != null && !values.isEmpty()) {
                int lastRowIndex = values.size() - 1;
                List<Object> lastRow = values.get(lastRowIndex);
                if (!lastRow.isEmpty()) {
                    nextCommentID = Integer.parseInt(lastRow.get(0).toString()) + 1;
                }
            }
            List<List<Object>> newComment = Arrays.asList(
                    Arrays.asList(nextCommentID, account.getAccountID(), account.getFullname(), account.getEmail(),
                            product.getProductID(), product.getProductName(), storeID, storeName, commentContent,
                            rating, formattedDate, report, reason, status));
            ValueRange body = new ValueRange().setValues(newComment);
            service.spreadsheets().values()
                    .append(SPREADSHEET_ID, RANGE, body)
                    .setValueInputOption("RAW")
                    .execute();
        } catch (Exception e) {
            System.out.println("Error adding comment to sheet: " + e.getMessage());
            throw e;
        }
    }

    public void report(Integer commentID, Integer storeID, Boolean report, String reason) throws Exception {
        try {
            Sheets service = googleSheetsUtil.getSheetService();
            String range = RANGE + "!A:N";
            ValueRange response = service.spreadsheets().values()
                    .get(SPREADSHEET_ID, range)
                    .execute();
            List<List<Object>> values = response.getValues();
            int rowIndex = -1;
            if (values != null && !values.isEmpty()) {
                for (int i = 1; i < values.size(); i++) {
                    List<Object> row = values.get(i);
                    if (row.size() <= 14) {
                        Integer rowCommentID = Integer.parseInt(row.get(0).toString()); // A
                        Integer rowStoreID = Integer.parseInt(row.get(6).toString()); // B
                        if (rowCommentID.equals(commentID) && rowStoreID.equals(storeID)) {
                            rowIndex = i + 1; // Sheets API is 1-indexed
                            break;
                        }
                    }
                }
            }

            if (rowIndex == -1) {
                throw new Exception("No matching commentID, accountID, and productID found.");
            }
            String updateRange = RANGE + "!L" + rowIndex + ":M" + rowIndex;
            List<List<Object>> updatedValues = Arrays.asList(
                    Arrays.asList(report, reason));

            ValueRange body = new ValueRange().setValues(updatedValues);
            service.spreadsheets().values()
                    .update(SPREADSHEET_ID, updateRange, body)
                    .setValueInputOption("RAW")
                    .execute();

        } catch (Exception e) {
            System.out.println("Error updating report: " + e.getMessage());
            throw e;
        }
    }

    public void updateStatus(Integer commentID, Boolean status) throws Exception {
        try {
            Sheets service = googleSheetsUtil.getSheetService();
            String range = RANGE + "!A:N";
            ValueRange response = service.spreadsheets().values()
                    .get(SPREADSHEET_ID, range)
                    .execute();
            List<List<Object>> values = response.getValues();
            int rowIndex = -1;
            if (values != null && !values.isEmpty()) {
                for (int i = 1; i < values.size(); i++) {
                    List<Object> row = values.get(i);
                    if (row.size() <= 14) {
                        Integer rowCommentID = Integer.parseInt(row.get(0).toString()); // A
                        if (rowCommentID.equals(commentID)) {
                            rowIndex = i + 1; 
                            break;
                        }
                    }
                }
            }

            if (rowIndex == -1) {
                throw new Exception("No matching commentID, accountID, and productID found.");
            }
            String updateRange = RANGE + "!N" + rowIndex;
            List<List<Object>> updatedValues = Arrays.asList(
                    Arrays.asList(status));

            ValueRange body = new ValueRange().setValues(updatedValues);
            service.spreadsheets().values()
                    .update(SPREADSHEET_ID, updateRange, body)
                    .setValueInputOption("RAW")
                    .execute();
        } catch (Exception e) {
            System.out.println("Error adding comment to sheet: " + e.getMessage());
            throw e;
        }
    }

    public void updateRating(Integer commentID, Integer accountID, Integer productID, Float updatedRating)
            throws Exception {
        try {
            Sheets service = googleSheetsUtil.getSheetService();

            String range = RANGE + "!A:N";
            ValueRange response = service.spreadsheets().values()
                    .get(SPREADSHEET_ID, range)
                    .execute();
            List<List<Object>> values = response.getValues();

            // Locate the row based on commentID, accountID, and productID
            int rowIndex = -1;
            if (values != null && !values.isEmpty()) {
                for (int i = 1; i < values.size(); i++) {
                    List<Object> row = values.get(i);
                    if (row.size() <= 14) {
                        Integer rowCommentID = Integer.parseInt(row.get(0).toString()); // A
                        Integer rowAccountID = Integer.parseInt(row.get(1).toString()); // B
                        Integer rowProductID = Integer.parseInt(row.get(4).toString()); // E
                        if (rowCommentID.equals(commentID) && rowAccountID.equals(accountID)
                                && rowProductID.equals(productID)) {
                            rowIndex = i + 1; // Sheets API is 1-indexed
                            break;
                        }
                    }
                }
            }

            if (rowIndex == -1) {
                throw new Exception("No matching commentID, accountID, and productID found.");
            }

            String updateRange = RANGE + "!J" + rowIndex;
            List<List<Object>> updatedValues = Arrays.asList(
                    Arrays.asList(updatedRating));
            ValueRange body = new ValueRange().setValues(updatedValues);
            service.spreadsheets().values()
                    .update(SPREADSHEET_ID, updateRange, body)
                    .setValueInputOption("RAW")
                    .execute();

        } catch (Exception e) {
            System.out.println("Error updating rating: " + e.getMessage());
            throw e;
        }
    }

    public void updateComment(Integer commentID, Integer accountID, Integer productID, String updatedCommentContent)
            throws Exception {
        try {
            Sheets service = googleSheetsUtil.getSheetService();

            String range = RANGE + "!A:N";
            ValueRange response = service.spreadsheets().values()
                    .get(SPREADSHEET_ID, range)
                    .execute();
            List<List<Object>> values = response.getValues();
            int rowIndex = -1;
            if (values != null && !values.isEmpty()) {
                for (int i = 1; i < values.size(); i++) {
                    List<Object> row = values.get(i);
                    if (row.size() <= 14) {
                        Integer rowCommentID = Integer.parseInt(row.get(0).toString()); // A
                        Integer rowAccountID = Integer.parseInt(row.get(1).toString()); // B
                        Integer rowProductID = Integer.parseInt(row.get(4).toString()); // E
                        if (rowCommentID.equals(commentID) && rowAccountID.equals(accountID)
                                && rowProductID.equals(productID)) {
                            rowIndex = i + 1;
                            break;
                        }
                    }
                }
            }

            if (rowIndex == -1) {
                throw new Exception("No matching commentID, accountID, and productID found.");
            }

            String updateRange = RANGE + "!I" + rowIndex;
            List<List<Object>> updatedValues = Arrays.asList(
                    Arrays.asList(updatedCommentContent));
            ValueRange body = new ValueRange().setValues(updatedValues);
            service.spreadsheets().values()
                    .update(SPREADSHEET_ID, updateRange, body)
                    .setValueInputOption("RAW")
                    .execute();

        } catch (Exception e) {
            System.out.println("Error updating comment: " + e.getMessage());
            throw e;
        }
    }

    public void deleteComment(Integer accountID, Integer commentID) throws Exception {
        try {
            Sheets service = googleSheetsUtil.getSheetService();

            // Retrieve data from the sheet to find the correct row
            String range = RANGE + "!A:N"; // Adjust range if your columns differ
            ValueRange response = service.spreadsheets().values()
                    .get(SPREADSHEET_ID, range)
                    .execute();
            List<List<Object>> values = response.getValues();
            int rowIndex = -1;
            if (values != null && !values.isEmpty()) {
                for (int i = 1; i < values.size(); i++) {
                    List<Object> row = values.get(i);
                    if (row.size() <= 9) {
                        Integer rowCommentID = Integer.parseInt(row.get(0).toString()); // A
                        Integer rowAccountID = Integer.parseInt(row.get(1).toString()); // B
                        if (rowCommentID.equals(commentID) && rowAccountID.equals(accountID)) {
                            rowIndex = i + 1;
                            break;
                        }
                    }
                }
            }

            if (rowIndex == -1) {
                throw new Exception("No matching commentID, accountID, and productID found.");
            }
            String deleteRange = "Sheet1!A" + rowIndex + ":N" + rowIndex;
            List<List<Object>> emptyValues = Arrays.asList(
                    Arrays.asList("", "", "", "", "", "", "", "", "", "", "", "", "", ""));
            ValueRange body = new ValueRange().setValues(emptyValues);
            service.spreadsheets().values()
                    .update(SPREADSHEET_ID, deleteRange, body)
                    .setValueInputOption("RAW")
                    .execute();

            System.out.println("Comment deleted successfully.");

        } catch (Exception e) {
            System.out.println("Error delete comment: " + e.getMessage());
            throw e;
        }
    }

}
