package com.example.bloomgift.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.bloomgift.reponse.AccountReponse;
import com.example.bloomgift.reponse.ProductReponse;
import com.example.bloomgift.repository.ProductRepository;

import io.swagger.v3.oas.annotations.servers.Server;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public Page<ProductReponse> getAllProducts(Pageable pageable) {
    return productRepository.findAll(pageable)
            .map(product -> new ProductReponse());
    }


}
