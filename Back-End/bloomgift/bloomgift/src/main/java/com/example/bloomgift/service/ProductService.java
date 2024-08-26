package com.example.bloomgift.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.bloomgift.model.Category;
import com.example.bloomgift.model.Product;
import com.example.bloomgift.model.ProductImage;
import com.example.bloomgift.model.Store;
import com.example.bloomgift.reponse.ProductReponse;
import com.example.bloomgift.repository.CategoryRepository;
import com.example.bloomgift.repository.ProductImageRepository;
import com.example.bloomgift.repository.ProductRepository;
import com.example.bloomgift.repository.StoreRepository;
import com.example.bloomgift.request.ProductRequest;


@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired 
    private StoreRepository storeRepository;

    @Autowired
    private ProductImageRepository productImageRepository;

    public List<ProductReponse> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
        .map(product -> new ProductReponse(
        product.getProductID(),
        product.getPrice(),
        product.getDiscount(),
        product.getDescription(),
        product.getColour(),
        product.getSize(),
        product.getFeatured(),
        product.getProductStatus(),
        product.getCreateDate(),
        product.getQuantity(),
        product.getSold(),
        product.getProductName(),
        product.getCategoryName(),
        product.getStoreName()
    ))
    .collect(Collectors.toList());
    }


    
    public void createProduct(ProductRequest productRequest){
        checkProduct(productRequest);
        Float price = productRequest.getPrice();
        Float discount = productRequest.getDiscount();
        String description = productRequest.getDescription();
        String colour = productRequest.getColour();
        Float size = productRequest.getSize();
        Boolean featured = productRequest.getFeatured();
        Integer quantity = productRequest.getQuantity();
        String categoryName = productRequest.getCategoryName();
        String storeName = productRequest.getStoreName();
        String productImage = productRequest.getProductImage();
        String productName = productRequest.getProductName();

       Category category = categoryRepository.findByCategoryName(categoryName);
        if (category == null) {
            throw new RuntimeException("Category not found");
        }
        Store store = storeRepository.findByStoreName(storeName);
        if (store == null) {
            throw new RuntimeException("Store not found");
        }
        Product product = new Product();
        product.setPrice(price);
        product.setDiscount(discount);
        product.setDescription(description);
        product.setColour(colour);
        product.setSize(size);
        product.setFeatured(featured);
        product.setProductStatus(true);
        product.setProductName(productName);
        product.setCreateDate(new Date());
        product.setQuantity(quantity);
        product.setCategoryID(category);
        product.setSold(0);
        product.setStoreID(store);
        productRepository.save(product);

        ProductImage productImageEntity = new ProductImage();
        productImageEntity.setProductImage(productImage);
        productImageEntity.setProductID(product);

        productImageRepository.save(productImageEntity);


    }
    public void deleteProduct(Integer productID){
        Product existingProduct = productRepository.findById(productID).orElseThrow();
        productRepository.delete(existingProduct);
    }
    public Product updateProduct(Integer productID ,ProductRequest productRequest){
        checkProduct(productRequest);
         Product existingProduct = productRepository.findById(productID).orElse(null);
        if (existingProduct == null) {
           throw new RuntimeException("Không tìm thấy san pham"); 
        }

        Float price = productRequest.getPrice();
        Float discount = productRequest.getDiscount();
        String description = productRequest.getDescription();
        String colour = productRequest.getColour();
        Float size = productRequest.getSize();
        Boolean featured = productRequest.getFeatured();
        Integer quantity = productRequest.getQuantity();
        String categoryName = productRequest.getCategoryName();
        String productImage = productRequest.getProductImage();
        String productName = productRequest.getProductName();
        Boolean productStatus = productRequest.getProductStatus();
        Category category = categoryRepository.findByCategoryName(categoryName);
        if (category == null) {
            throw new RuntimeException("Category not found");
        }
    
        //----------------------------------------------/
        existingProduct.setPrice(price);
        existingProduct.setDescription(description);
        existingProduct.setDiscount(discount);
        existingProduct.setColour(colour);
        existingProduct.setSize(size);
        existingProduct.setFeatured(featured);
        existingProduct.setQuantity(quantity);
        existingProduct.setCategoryID(category);
        existingProduct.setProductName(productName);
        existingProduct.setProductStatus(productStatus);
        existingProduct.setSold(0);
        return productRepository.save(existingProduct);

    }


    public void checkProduct(ProductRequest productRequest){
        Float price = productRequest.getPrice();
        Float discount = productRequest.getDiscount();
        String description = productRequest.getDescription();
        String colour = productRequest.getColour();
        Float size = productRequest.getSize();
        Boolean featured = productRequest.getFeatured();
        Integer quantity = productRequest.getQuantity();
        String categoryName = productRequest.getCategoryName();
        String storeName = productRequest.getStoreName();
        String productImage = productRequest.getProductImage();
        String productName = productRequest.getProductName();
    
         if(!StringUtils.hasText(description)
        || !StringUtils.hasText(colour)
            || !StringUtils.hasText(categoryName)
                ||!StringUtils.hasText(storeName)
                    ||!StringUtils.hasText(productName)
                        ||price == null 
                             ||quantity ==null){
        throw new RuntimeException("Vui lòng nhập đầy đủ thông tin");
        }
        if(price < 0 || discount < 0 || size < 0 || quantity<0){
            throw new RuntimeException("Vui lòng nhập đung thông tin");

        }
    
    }


}
