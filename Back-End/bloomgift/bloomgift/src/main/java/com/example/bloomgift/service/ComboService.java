package com.example.bloomgift.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bloomgift.DTO.AddProductToComboDTO;
import com.example.bloomgift.DTO.ComboDTO;
import com.example.bloomgift.model.Combo;
import com.example.bloomgift.model.ComboProduct;
import com.example.bloomgift.model.ComboProductKey;
import com.example.bloomgift.model.Product;
import com.example.bloomgift.repository.ComboProductRepository;
import com.example.bloomgift.repository.ComboRepository;
import com.example.bloomgift.repository.ProductRepository;

@Service
public class ComboService {
    @Autowired
    private ComboRepository comboRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ComboProductRepository comboProductRepository;

    // Get all combos
    public List<Combo> getAllCombos() {
        return comboRepository.findAll();
    }

    // Get a specific combo by ID
    public Optional<Combo> getComboById(Integer id) {
        return comboRepository.findById(id);
    }

    // Create a new combo
    public Combo createCombo(Combo combo) {
        return comboRepository.save(combo);
    }

    // Update an existing combo
    public Optional<Combo> updateCombo(Integer id, ComboDTO comboDTO) {
        return comboRepository.findById(id).map(existingCombo -> {
            existingCombo.setComboName(comboDTO.getComboName());
            existingCombo.setComboDescription(comboDTO.getComboDescription());
            existingCombo.setComboPrice(comboDTO.getComboPrice());
            existingCombo.setComboStatus(comboDTO.getComboStatus());
            return comboRepository.save(existingCombo);
        });
    }

    // Delete a combo by ID
    public boolean deleteCombo(Integer id) {
        if (comboRepository.existsById(id)) {
            comboRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Method to add a product to a combo and return a message as a Map
    public Map<String, String> addProductToCombo(AddProductToComboDTO dto) {
        Optional<Combo> comboOptional = comboRepository.findById(dto.getComboID());
        Optional<Product> productOptional = productRepository.findById(dto.getProductID());

        if (comboOptional.isEmpty()) {
            return Collections.singletonMap("message", "Combo không tìm thấy!");
        }

        if (productOptional.isEmpty()) {
            return Collections.singletonMap("message", "Sản phẩm không tìm thấy!");
        }

        Combo combo = comboOptional.get();
        Product product = productOptional.get();

        // Create ComboProductKey (composite key)
        ComboProductKey comboProductKey = new ComboProductKey(dto.getComboID(), dto.getProductID());

        // Create ComboProduct entity
        ComboProduct comboProduct = new ComboProduct();
        comboProduct.setId(comboProductKey);
        comboProduct.setCombo(combo);
        comboProduct.setProduct(product);
        comboProduct.setQuantity(dto.getQuantity());

        // Save the combo product relationship
        comboProductRepository.save(comboProduct);

        return Collections.singletonMap("message", "Thêm sản phẩm vào combo thành công!");
    }
    

    // Method to update quantity of a product in a combo
    public Map<String, String> updateProductQuantityInCombo(Integer comboID, Integer productID, Integer quantity) {
        ComboProductKey comboProductKey = new ComboProductKey(comboID, productID);
        Optional<ComboProduct> comboProductOptional = comboProductRepository.findById(comboProductKey);

        if (comboProductOptional.isEmpty()) {
            return Collections.singletonMap("message", "Sản phẩm trong combo không tồn tại!");
        }

        ComboProduct comboProduct = comboProductOptional.get();
        comboProduct.setQuantity(quantity);
        comboProductRepository.save(comboProduct);

        return Collections.singletonMap("message", "Cập nhật số lượng sản phẩm trong combo thành công!");
    }

    // Method to remove a product from a combo
    public Map<String, String> removeProductFromCombo(Integer comboID, Integer productID) {
        ComboProductKey comboProductKey = new ComboProductKey(comboID, productID);
        Optional<ComboProduct> comboProductOptional = comboProductRepository.findById(comboProductKey);

        if (comboProductOptional.isEmpty()) {
            return Collections.singletonMap("message", "Sản phẩm trong combo không tồn tại!");
        }

        comboProductRepository.deleteById(comboProductKey);
        return Collections.singletonMap("message", "Xóa sản phẩm khỏi combo thành công!");
    }

    // Method to update combo status if a product is removed from it
    public void updateComboStatusIfProductRemoved(Integer productID) {
        List<ComboProduct> comboProducts = comboProductRepository.findByProduct_ProductID(productID);

        for (ComboProduct comboProduct : comboProducts) {
            Combo combo = comboProduct.getCombo();
            combo.setComboStatus(false); // Set combo status to inactive
            comboRepository.save(combo);
        }
    }
}
