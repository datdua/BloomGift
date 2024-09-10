package com.example.bloomgift.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.bloomgift.model.Category;
import com.example.bloomgift.model.Product;
import com.example.bloomgift.model.ProductImage;
import com.example.bloomgift.model.Size;
import com.example.bloomgift.model.Store;
import com.example.bloomgift.reponse.ProductImageReponse;
import com.example.bloomgift.reponse.ProductReponse;
import com.example.bloomgift.reponse.SizeReponse;
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

        return products.stream().map(this::convertToProductReponse).collect(Collectors.toList());
    }

    public ProductReponse getProductByID(int productID) {
        Product product = productRepository.findById(productID).orElseThrow();
        List<ProductImageReponse> imageResponses = product.getProductImages().stream()
                .map(image -> new ProductImageReponse(image.getImageID(), image.getProductImage()))
                .collect(Collectors.toList());
        List<SizeReponse> sizeReponses = product.getSizes().stream()
                .map(size -> new SizeReponse(size.getSizeID(), size.getPrice(), size.getText(), size.getSizeFloat()))
                .collect(Collectors.toList());
        return new ProductReponse(
                product.getProductID(),
                product.getDiscount(),
                product.getDescription(),
                product.getColour(),
                product.getPrice(),
                product.getFeatured(),
                product.getProductStatus(),
                product.getCreateDate(),
                product.getQuantity(),
                product.getSold(),
                product.getProductName(),
                product.getCategoryName(),
                product.getStoreName(),
                sizeReponses,
                imageResponses);
    }

    private ProductReponse convertToProductReponse(Product product) {
        List<ProductImageReponse> imageResponses = product.getProductImages().stream()
                .map(image -> new ProductImageReponse(image.getImageID(), image.getProductImage()))
                .collect(Collectors.toList());
        List<SizeReponse> sizeReponses = product.getSizes().stream()
                .map(size -> new SizeReponse(size.getSizeID(), size.getPrice(), size.getText(), size.getSizeFloat()))
                .collect(Collectors.toList());

        return new ProductReponse(
                product.getProductID(),
                product.getDiscount(),
                product.getDescription(),
                product.getColour(),
                product.getPrice(),
                product.getFeatured(),
                product.getProductStatus(),
                product.getCreateDate(),
                product.getQuantity(),
                product.getSold(),
                product.getProductName(),
                product.getCategoryName(),
                product.getStoreName(),
                sizeReponses,
                imageResponses);
    }

    public void createProductt(ProductRequest productRequest) {
        checkProduct(productRequest);
        Float discount = productRequest.getDiscount();
        String description = productRequest.getDescription();
        String colour = productRequest.getColour();
        Boolean featured = productRequest.getFeatured();
        Integer quantity = productRequest.getQuantity();
        Float price = productRequest.getPrice();
        String categoryName = productRequest.getCategoryName();
        String productName = productRequest.getProductName();
        Category category = categoryRepository.findByCategoryName(categoryName);

        if (category == null) {
            throw new IllegalArgumentException("Category not found");
        }

        Store store = null;
        if (productRequest.getStoreID() != null) {
            store = storeRepository.findById(productRequest.getStoreID()).orElse(null);
        }
        if (store == null) {
            throw new IllegalArgumentException("Store not found");
        }
        Product product = new Product();
        product.setDiscount(discount);
        product.setDescription(description);
        product.setColour(colour);
        product.setFeatured(featured);
        product.setProductStatus(true);
        product.setProductName(productName);
        product.setCreateDate(new Date());
        product.setQuantity(quantity);
        product.setPrice(price);
        product.setCategoryID(category);
        product.setSold(0);
        product.setStoreID(store);
        List<Size> sizes = productRequest.getSizes().stream()
                .map(sizeRequest -> {
                    Size size = new Size();
                    size.setPrice(sizeRequest.getPrice());
                    size.setText(sizeRequest.getText());
                    size.setSizeFloat(sizeRequest.getSizeFloat());
                    size.setProductID(product);
                    return size;
                })
                .collect(Collectors.toList());
        product.setSizes(sizes);
        List<ProductImage> productImages = productRequest.getImages().stream()
                .map(imageRequest -> {
                    ProductImage image = new ProductImage();
                    image.setProductImage(imageRequest.getProductImage());
                    image.setProductID(product); // set the product reference
                    return image;
                })
                .collect(Collectors.toList());

        product.setProductImages(productImages);
        // productImageRepository.saveAll(productImages);
        productRepository.save(product);

    }

    public void deleteProduct(Integer productID) {
        Product existingProduct = productRepository.findById(productID).orElseThrow();
        productRepository.delete(existingProduct);
    }

    public Product updateProduct(Integer productID, ProductRequest productRequest) {
        checkProduct(productRequest);
        Product existingProduct = productRepository.findById(productID).orElse(null);
        if (existingProduct == null) {
            throw new RuntimeException("Không tìm thấy san pham");
        }

        Float discount = productRequest.getDiscount();
        String description = productRequest.getDescription();
        String colour = productRequest.getColour();
        Boolean featured = productRequest.getFeatured();
        Integer quantity = productRequest.getQuantity();
        String categoryName = productRequest.getCategoryName();
        String productName = productRequest.getProductName();
        Boolean productStatus = productRequest.getProductStatus();
        Float price = productRequest.getPrice();
        Category category = categoryRepository.findByCategoryName(categoryName);
        if (category == null) {
            throw new RuntimeException("Category not found");
        }

        // ----------------------------------------------/
        existingProduct.setDescription(description);
        existingProduct.setDiscount(discount);
        existingProduct.setColour(colour);
        existingProduct.setFeatured(featured);
        existingProduct.setQuantity(quantity);
        existingProduct.setCategoryID(category);
        existingProduct.setProductName(productName);
        existingProduct.setProductStatus(productStatus);
        existingProduct.setPrice(price);
        // List<Size> updatedSizes = productRequest.getSizes().stream()
        //         .map(sizeRequest -> {
        //             Size size = existingProduct.getSizes().stream()
        //                     .filter(s -> s.getSizeID().equals(sizeRequest.getSizeID()))
        //                     .findFirst()
        //                     .orElse(new Size());
        //             size.setPrice(sizeRequest.getPrice());
        //             size.setText(sizeRequest.getText());
        //             size.setSizeFloat(sizeRequest.getSizeFloat());
        //             size.setProductID(existingProduct);
        //             return size;
        //         })
        //         .collect(Collectors.toList());
        // existingProduct.setSizes(updatedSizes);
        return productRepository.save(existingProduct);

    }

    public void checkProduct(ProductRequest productRequest) {
        Float discount = productRequest.getDiscount();
        String description = productRequest.getDescription();
        String colour = productRequest.getColour();
        Boolean featured = productRequest.getFeatured();
        Integer quantity = productRequest.getQuantity();
        String categoryName = productRequest.getCategoryName();
        String productName = productRequest.getProductName();
        Float price = productRequest.getPrice();

        if (!StringUtils.hasText(description)
                || !StringUtils.hasText(colour)
                || !StringUtils.hasText(categoryName)
                || !StringUtils.hasText(productName)
                || quantity == null
                || price == null) {
            throw new RuntimeException("Vui lòng nhập đầy đủ thông tin");
        }
        if (discount < 0 || quantity < 0 || price <0) {
            throw new RuntimeException("Vui lòng nhập đung thông tin");

        }

    }

    public Product getProductById(Integer productID) {
        return productRepository.findById(productID)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm: " + productID));
    }




}