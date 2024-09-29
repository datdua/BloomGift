package com.example.bloomgift.controllers.Combo;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import com.example.bloomgift.DTO.AddProductToComboDTO;
import com.example.bloomgift.DTO.ComboDTO;
import com.example.bloomgift.model.Combo;
import com.example.bloomgift.service.ComboService;

@RestController
@RequestMapping("/api/combos")
public class ComboController {
    @Autowired
    private ComboService comboService;

    // Get all combos
    @GetMapping(value="/getAll", produces = "application/json; charset=UTF-8")
    public ResponseEntity<List<Combo>> getAllCombos() {
        List<Combo> combos = comboService.getAllCombos();
        return ResponseEntity.ok(combos);
    }

    // Get a specific combo by ID
    @GetMapping(value="/get/{id}", produces = "application/json; charset=UTF-8")
    public ResponseEntity<Combo> getComboById(@PathVariable Integer id) {
        Optional<Combo> combo = comboService.getComboById(id);
        return combo.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping(value = "/create", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> createCombo(@RequestBody ComboDTO comboDTO) {
        try {
            // Chuyển từ ComboDTO sang Combo entity để lưu vào DB
            Combo newCombo = new Combo();
            newCombo.setComboName(comboDTO.getComboName());
            newCombo.setComboDescription(comboDTO.getComboDescription());
            newCombo.setComboPrice(comboDTO.getComboPrice());
            newCombo.setCreateDate(comboDTO.getCreateDate());
            newCombo.setComboStatus(comboDTO.getComboStatus());

            comboService.createCombo(newCombo);  // Giả định bạn đã có service để lưu Combo

            return ResponseEntity.ok(newCombo);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to create combo", HttpStatus.BAD_REQUEST);
        }
    }

    // Update an existing combo
    @PutMapping(value="/update/{id}", produces = "application/json; charset=UTF-8")
    public ResponseEntity<Combo> updateCombo(@PathVariable Integer id, @RequestBody ComboDTO comboDTO) {
        Optional<Combo> updatedCombo = comboService.updateCombo(id, comboDTO);
        return updatedCombo.map(ResponseEntity::ok)
                           .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Delete a combo by ID
    @DeleteMapping(value="/delete/{id}", produces = "application/json; charset=UTF-8")
    public ResponseEntity<Void> deleteCombo(@PathVariable Integer id) {
        boolean isDeleted = comboService.deleteCombo(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoint to add a product to a combo
    @PostMapping(value = "/addProduct", produces = "application/json; charset=UTF-8")
    public ResponseEntity<Map<String, String>> addProductToCombo(@RequestBody AddProductToComboDTO dto) {
        try {
            Map<String, String> response = comboService.addProductToCombo(dto);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return new ResponseEntity<>(Collections.singletonMap("message", "Lỗi khi thêm sản phẩm vào combo!"), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/updateProductQuantity", produces = "application/json; charset=UTF-8")
    public ResponseEntity<Map<String, String>> updateProductQuantityInCombo(
            @RequestParam Integer comboID,
            @RequestParam Integer productID,
            @RequestParam Integer quantity) {
        Map<String, String> result = comboService.updateProductQuantityInCombo(comboID, productID, quantity);
        return ResponseEntity.ok(result);
    }

    // DELETE endpoint to remove a product from a combo
    @DeleteMapping(value = "/removeProduct", produces = "application/json; charset=UTF-8")
    public ResponseEntity<Map<String, String>> removeProductFromCombo(
            @RequestParam Integer comboID,
            @RequestParam Integer productID) {
        Map<String, String> result = comboService.removeProductFromCombo(comboID, productID);
        return ResponseEntity.ok(result);
    }
}
