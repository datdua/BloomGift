package com.example.bloomgift.service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.Pageable;
import com.example.bloomgift.model.Category;
import com.example.bloomgift.model.OrderDetail;
import com.example.bloomgift.model.Product;
import com.example.bloomgift.model.ProductImage;
import com.example.bloomgift.model.Size;
import com.example.bloomgift.model.Store;
import com.example.bloomgift.model.Order;
import com.example.bloomgift.reponse.ProductImageReponse;
import com.example.bloomgift.reponse.ProductReponse;
import com.example.bloomgift.reponse.SizeReponse;
import com.example.bloomgift.repository.CategoryRepository;
import com.example.bloomgift.repository.ProductImageRepository;
import com.example.bloomgift.repository.OrderDetailRepository;
import com.example.bloomgift.repository.ProductRepository;
import com.example.bloomgift.repository.SizeRepository;
import com.example.bloomgift.repository.StoreRepository;
import com.example.bloomgift.repository.OrderRepository;
import com.example.bloomgift.request.ProductRequest;
import com.example.bloomgift.specification.ProductSpecification;

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

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private FirebaseStorageService firebaseStorageService;

    @Autowired
    private SizeRepository sizeRepository;

    @Autowired
    private OrderRepository orderRepository;

    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ProductService.class);

    public List<ProductReponse> getAllProducts() {
        List<Product> products = productRepository.findAll();

        return products.stream().map(this::convertToProductReponse).collect(Collectors.toList());
    }

    private ProductReponse convertToProductReponse(Product product) {
        List<ProductImageReponse> imageResponses = product.getProductImages().stream()
                .map(image -> new ProductImageReponse(image.getImageID(), image.getProductImage()))
                .collect(Collectors.toList());
        List<SizeReponse> sizeReponses = product.getSizes().stream()
                .map(size -> new SizeReponse(size.getSizeID(), size.getPrice(), size.getText(), size.getSizeQuantity()))
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

    public List<ProductReponse> getAllProductByCustomer() {
        List<Product> products = productRepository.findAll();

        return products.stream().map(this::convertToProductReponseByCustomer).collect(Collectors.toList());
    }

    private ProductReponse convertToProductReponseByCustomer(Product product) {
        ProductImageReponse firstImageResponse = product.getProductImages().stream()
                .findFirst()
                .map(image -> new ProductImageReponse(image.getImageID(), image.getProductImage()))
                .orElse(null);
        List<SizeReponse> sizeReponses = product.getSizes().stream()
                .map(size -> new SizeReponse(size.getSizeID(), size.getPrice(), size.getText(), size.getSizeQuantity()))
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
                firstImageResponse != null ? List.of(firstImageResponse) : List.of() // Wrap the first image in a list
        );
    }

    public ProductReponse getProductByID(int productID) {
        Product product = productRepository.findById(productID).orElseThrow();
        List<ProductImageReponse> imageResponses = product.getProductImages().stream()
                .map(image -> new ProductImageReponse(image.getImageID(), image.getProductImage()))
                .collect(Collectors.toList());
        List<SizeReponse> sizeReponses = product.getSizes().stream()
                .map(size -> new SizeReponse(size.getSizeID(), size.getPrice(), size.getText(), size.getSizeQuantity()))
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

    public List<ProductReponse> ListNewProduct() {
        List<Product> products = productRepository.findAll();

        LocalDate today = LocalDate.now(ZoneId.systemDefault());

        @SuppressWarnings("deprecation")
        List<Product> todaysProducts = products.stream()
                .filter(product -> product.getCreateDate().toLocaleString().equals(today))
                .collect(Collectors.toList());

        List<ProductReponse> productResponses = todaysProducts.stream()
                .map(product -> {
                    List<ProductImageReponse> imageResponses = product.getProductImages().stream()
                            .map(image -> new ProductImageReponse(image.getImageID(), image.getProductImage()))
                            .collect(Collectors.toList());
                    List<SizeReponse> sizeReponses = product.getSizes().stream()
                            .map(size -> new SizeReponse(size.getSizeID(), size.getPrice(), size.getText(),
                                    size.getSizeQuantity()))
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
                })
                .collect(Collectors.toList());

        return productResponses;
    }

    public List<ProductReponse> getProductByStoreAndFeatured(int storeID) {
        Store store = storeRepository.findById(storeID).orElseThrow();
        List<Product> products = productRepository.findProductByStoreID(store);
        List<Product> featureProducts = products.stream().filter(Product::getFeatured).collect(Collectors.toList());
        List<ProductReponse> productResponses = featureProducts.stream()
                .map(product -> {
                    List<ProductImageReponse> imageResponses = product.getProductImages().stream()
                            .map(image -> new ProductImageReponse(image.getImageID(), image.getProductImage()))
                            .collect(Collectors.toList());
                    List<SizeReponse> sizeReponses = product.getSizes().stream()
                            .map(size -> new SizeReponse(size.getSizeID(), size.getPrice(), size.getText(),
                                    size.getSizeQuantity()))
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
                })
                .collect(Collectors.toList());
        return productResponses;
    }

    public List<ProductReponse> getProductFeatured() {
        List<Product> products = productRepository.findAll();
        List<Product> featureProducts = products.stream().filter(Product::getFeatured).collect(Collectors.toList());
        List<ProductReponse> productResponses = featureProducts.stream()
                .map(product -> {
                    List<ProductImageReponse> imageResponses = product.getProductImages().stream()
                            .map(image -> new ProductImageReponse(image.getImageID(), image.getProductImage()))
                            .collect(Collectors.toList());
                    List<SizeReponse> sizeReponses = product.getSizes().stream()
                            .map(size -> new SizeReponse(size.getSizeID(), size.getPrice(), size.getText(),
                                    size.getSizeQuantity()))
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
                })
                .collect(Collectors.toList());
        return productResponses;
    }

    public void quickSort(List<Product> products, int low, int high) {
        if (low < high) {
            int pi = partition(products, low, high);
            quickSort(products, low, pi - 1);
            quickSort(products, pi + 1, high);

        }
    }

    public List<ProductReponse> getProductsByProductStatus(Boolean productStatus) {
        List<Product> products = productRepository.findByProductStatus(productStatus);
        List<ProductReponse> productResponses = products.stream()
                .map(product -> {
                    List<ProductImageReponse> imageResponses = product.getProductImages().stream()
                            .map(image -> new ProductImageReponse(image.getImageID(), image.getProductImage()))
                            .collect(Collectors.toList());
                    List<SizeReponse> sizeReponses = product.getSizes().stream()
                            .map(size -> new SizeReponse(size.getSizeID(), size.getPrice(), size.getText(),
                                    size.getSizeQuantity()))
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
                })
                .collect(Collectors.toList());
        return productResponses;

    }

    private int partition(List<Product> products, int low, int high) {
        Product pivot = products.get(high);
        int i = (low - 1);
        for (int j = low; j < high; j++) {
            if (products.get(j).getSold() > pivot.getSold()) {
                i++;
                Product temp = products.get(i);
                products.set(i, products.get(j));
                products.set(j, temp);
            }
        }
        Product temp = products.get(i + 1);
        products.set(i + 1, products.get(high));
        products.set(high, temp);
        return i + 1;
    }

    public List<ProductReponse> getProductBySold(int top) {
        List<Product> products = productRepository.findAll();
        quickSort(products, 0, products.size() - 1);
        List<Product> topSoldProducts = products.subList(0, top);
        List<ProductReponse> productResponses = topSoldProducts.stream()
                .map(product -> {
                    List<ProductImageReponse> imageResponses = product.getProductImages().stream()
                            .map(image -> new ProductImageReponse(image.getImageID(), image.getProductImage()))
                            .collect(Collectors.toList());

                    List<SizeReponse> sizeReponses = product.getSizes().stream()
                            .map(size -> new SizeReponse(size.getSizeID(), size.getPrice(), size.getText(),
                                    size.getSizeQuantity()))
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
                })
                .collect(Collectors.toList());

        return productResponses;
    }

    public List<ProductReponse> getProductByStoreID(int storeID) {
        Store store = storeRepository.findById(storeID).orElseThrow();
        List<Product> products = productRepository.findProductByStoreID(store);
        List<ProductReponse> productResponses = products.stream()
                .map(product -> {
                    List<ProductImageReponse> imageResponses = product.getProductImages().stream()
                            .map(image -> new ProductImageReponse(image.getImageID(), image.getProductImage()))
                            .collect(Collectors.toList());
                    List<SizeReponse> sizeReponses = product.getSizes().stream()
                            .map(size -> new SizeReponse(size.getSizeID(), size.getPrice(), size.getText(),
                                    size.getSizeQuantity()))
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
                })
                .collect(Collectors.toList());
        return productResponses;

    }

    public void createProduct(ProductRequest productRequest, List<MultipartFile> imageFiles) {
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
                    size.setSizeQuantity(sizeRequest.getSizeQuanity());
                    size.setProductID(product);
                    return size;
                })
                .collect(Collectors.toList());
        product.setSizes(sizes);

        List<String> imageUrls = uploadImagesByStoreAndProduct(imageFiles, store.getStoreName(),
                product.getProductName());
        List<ProductImage> productImages = imageUrls.stream()
                .map(imageUrl -> {
                    ProductImage image = new ProductImage();
                    image.setProductImage(imageUrl);
                    image.setProductID(product);
                    return image;
                })
                .collect(Collectors.toList());

        product.setProductImages(productImages);
        productRepository.save(product);

    }

    public List<String> uploadImagesByStoreAndProduct(List<MultipartFile> imageFiles, String storeName,
            String productName) {
        List<String> imageUrls = new ArrayList<>();

        for (MultipartFile imageFile : imageFiles) {
            try {
                String imageUrl = firebaseStorageService.uploadFileByProduct(imageFile, storeName, productName);
                imageUrls.add(imageUrl);
            } catch (IOException e) {
                throw new RuntimeException("Failed to upload image for product " + productName, e);
            }
        }

        return imageUrls;
    }

    @Transactional
    public void deleteProduct(Integer productID) {
        Product product = productRepository.findById(productID)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm với ID: " + productID));

        // Xóa hình ảnh sản phẩm từ Firebase
        for (ProductImage image : product.getProductImages()) {
            firebaseStorageService.deleteFile(image.getProductImage());
        }

        // Xóa các OrderDetail liên quan
        List<OrderDetail> orderDetails = orderDetailRepository.findByProductID(product);
        for (OrderDetail orderDetail : orderDetails) {
            // Cập nhật Order tổng nếu cần
            Order order = orderDetail.getOrderID();
            if (order != null) {
                order.setOrderPrice(order.getOrderPrice() - orderDetail.getProductTotalPrice());
                orderRepository.save(order);
            }
            orderDetailRepository.delete(orderDetail);
        }

        // Xóa các Size liên quan
        sizeRepository.deleteAll(product.getSizes());

        // Xóa các ProductImage liên quan
        productImageRepository.deleteAll(product.getProductImages());

        // Xóa sản phẩm
        productRepository.delete(product);
    }

    public Product updateProduct(Integer productID, ProductRequest productRequest, List<MultipartFile> newImageFiles) {
        checkProduct(productRequest);
        Product existingProduct = productRepository.findById(productID).orElse(null);
        if (existingProduct == null) {
            throw new RuntimeException("Không tìm thấy sản phẩm");
        }

        // Update basic product information
        existingProduct.setDescription(productRequest.getDescription());
        existingProduct.setDiscount(productRequest.getDiscount());
        existingProduct.setColour(productRequest.getColour());
        existingProduct.setFeatured(productRequest.getFeatured());
        existingProduct.setQuantity(productRequest.getQuantity());
        existingProduct.setProductName(productRequest.getProductName());
        existingProduct.setProductStatus(productRequest.getProductStatus());
        existingProduct.setPrice(productRequest.getPrice());

        // Update category
        Category category = categoryRepository.findByCategoryName(productRequest.getCategoryName());
        if (category == null) {
            throw new RuntimeException("Category not found");
        }
        existingProduct.setCategoryID(category);

        // Handle new images
        if (newImageFiles != null && !newImageFiles.isEmpty()) {
            // Delete old images from Firebase
            for (ProductImage oldImage : existingProduct.getProductImages()) {
                firebaseStorageService.deleteFile(oldImage.getProductImage());
            }

            // Clear old image records
            existingProduct.getProductImages().clear();

            // Upload new images
            List<String> newImageUrls = uploadImagesByStoreAndProduct(newImageFiles,
                    existingProduct.getStoreID().getStoreName(), existingProduct.getProductName());

            // Create new ProductImage entities
            List<ProductImage> newProductImages = newImageUrls.stream()
                    .map(imageUrl -> {
                        ProductImage image = new ProductImage();
                        image.setProductImage(imageUrl);
                        image.setProductID(existingProduct);
                        return image;
                    })
                    .collect(Collectors.toList());

            // Set new images
            existingProduct.setProductImages(newProductImages);
        }

        // Save and return the updated product
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
        if (discount < 0 || quantity < 0 || price < 0) {
            throw new RuntimeException("Vui lòng nhập đung thông tin");

        }

    }

    public Product getProductById(Integer productID) {
        return productRepository.findById(productID)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm: " + productID));
    }

    public Page<Product> searchProductWithFilterPage(
            String descriptionProduct,
            String colourProduct,
            Float priceProduct,
            String productName,
            String categoryName,
            Date createDate,
            String storeName,
            int page,
            int size) {

        Specification<Product> spec = Specification.where(null);

        if (descriptionProduct != null && !descriptionProduct.isEmpty()) {
            spec = spec.and(ProductSpecification.hasDescriptionProduct(descriptionProduct));
        }
        if (colourProduct != null && !colourProduct.isEmpty()) {
            spec = spec.and(ProductSpecification.hasColourProduct(colourProduct));
        }
        if (priceProduct != null) {
            spec = spec.and(ProductSpecification.hasPriceProduct(priceProduct));
        }
        if (productName != null && !productName.isEmpty()) {
            spec = spec.and(ProductSpecification.hasProductName(productName));
        }
        if (categoryName != null && !categoryName.isEmpty()) {
            spec = spec.and(ProductSpecification.hasCategoryName(categoryName));
        }
        if (createDate != null) {
            spec = spec.and(ProductSpecification.hasCreateDate(createDate));
        }
        if (storeName != null && !storeName.isEmpty()) {
            spec = spec.and(ProductSpecification.hasStoreName(storeName));
        }

        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findAll(spec, pageable);
    }

}