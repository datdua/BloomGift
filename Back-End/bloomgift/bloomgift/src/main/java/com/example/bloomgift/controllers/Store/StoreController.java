package com.example.bloomgift.controllers.Store;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.bloomgift.model.Store;
import com.example.bloomgift.request.StoreRequest;
import com.example.bloomgift.request.putRequest.StorePutRequest;
import com.example.bloomgift.reponse.StoreResponse;
import com.example.bloomgift.service.StoreService;

@RestController
@RequestMapping("/api/store/store-management")
public class StoreController {

    @Autowired
    private StoreService storeService;

    @GetMapping("/get-all")
    public ResponseEntity<List<StoreResponse>> getAllStores() {
        return storeService.getAllStores();
    }

    @GetMapping("/get-by-name")
    public ResponseEntity<?> getStoreByName(@RequestParam String storeName) {
        return storeService.getStoreByName(storeName);
    }

    @PostMapping(value = "/add", produces = "application/json;charset=UTF-8")
    public ResponseEntity<?> addStore(@RequestBody StoreRequest storeRequest) {
        return storeService.addStore(storeRequest);
    }

    @PutMapping(value = "/update/{storeID}", produces = "application/json;charset=UTF-8")
    public ResponseEntity<?> updateStore(@PathVariable Integer storeID, @RequestBody StorePutRequest storePutRequest) {
        return storeService.updateStore(storeID, storePutRequest);
    }

    @DeleteMapping(value = "/delete", produces = "application/json;charset=UTF-8")
    public ResponseEntity<?> deleteStore(@RequestBody List<Integer> storeIDs) {
        return storeService.deleteStore(storeIDs);
    }

    @GetMapping(value = "/search/get-paging", produces = "application/json;charset=UTF-8")
    public ResponseEntity<Page<StoreResponse>> searchStores(
            @RequestParam(required = false) String storeName,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String storePhone,
            @RequestParam(required = false) String storeAddress,
            @RequestParam(required = false) String storeEmail,
            @RequestParam(required = false) String bankAccountName,
            @RequestParam(required = false) String bankNumber,
            @RequestParam(required = false) String bankAddress,
            @RequestParam(required = false) String taxNumber,
            @RequestParam(required = false) String storeStatus,
            @RequestParam(required = false) String storeAvatar,
            @RequestParam(required = false) String identityCard,
            @RequestParam(required = false) String identityName,
            @RequestParam(required = false) String accountFullName,
            @RequestParam(required = false) String categoryName,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        Page<StoreResponse> stores = storeService.searchStoreWithFilterPage(
                storeName, type, storePhone, storeAddress, storeEmail, bankAccountName, bankNumber, bankAddress,
                taxNumber, storeStatus, storeAvatar, identityCard, identityName, accountFullName, categoryName, page,
                size);
        return ResponseEntity.ok(stores);
    }

    @GetMapping("/get-by-id")
    public ResponseEntity<?> getStoreById(@RequestParam Integer storeID) {
        return storeService.getStoreById(storeID);
    }

    @GetMapping("/get-all-paging")
    public ResponseEntity<Page<StoreResponse>> getAllStoresByPage(@RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        return ResponseEntity.ok(storeService.getAllStoreByPage(page, size));
    }
}