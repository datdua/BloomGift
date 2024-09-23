package com.example.bloomgift.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bloomgift.model.Product;
import com.example.bloomgift.model.Size;
import com.example.bloomgift.repository.SizeRepository;
import com.example.bloomgift.request.SizeRequest;

@Service
public class SizeSerivce {
    @Autowired
    private SizeRepository sizeRepository;

    @Autowired
    private ProductService productService;

    public void createProductBySize(Integer productID,SizeRequest sizeRequest){
        Product product = productService.getProductById(productID); 
        if (product == null) {
            throw new RuntimeException("Product not found with ID: " + productID);
        }
        Float price = sizeRequest.getPrice();
        String text = sizeRequest.getText();
        Integer sizeQuantity = sizeRequest.getSizeQuanity();
        Size size = new Size();
        size.setPrice(price);
        size.setText(text);
        size.setSizeQuantity(sizeQuantity);
        size.setProductID(product);
        sizeRepository.save(size);
    }
    public void updateProductBySize(Integer SizeID,SizeRequest sizeRequest){
        Size size = sizeRepository.findById(SizeID).orElseThrow(); 
        size.setPrice(sizeRequest.getPrice());
        size.setText(sizeRequest.getText());
        size.setSizeQuantity(sizeRequest.getSizeQuanity());
        sizeRepository.save(size);

    }
    public void deleteProductBySize(Integer sizeID){
        Size size = sizeRepository.findById(sizeID).orElseThrow();
        sizeRepository.delete(size);
    }
    // public void updateProductBySizee(Integer sizeID, SizeRequest sizeRequest) {
    //     Size size = sizeRepository.findById(SizeID).orElseThrow(); 
    //     size.setPrice(sizeRequest.getPrice());
    //     size.setText(sizeRequest.getText());
    //     size.setSizeFloat(sizeRequest.getSizeFloat());
    //     sizeRepository.save(size);
    // }
}