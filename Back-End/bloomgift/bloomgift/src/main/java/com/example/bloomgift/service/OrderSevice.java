package com.example.bloomgift.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.bloomgift.model.Account;
import com.example.bloomgift.model.Order;
import com.example.bloomgift.model.OrderDetail;
import com.example.bloomgift.model.Product;
import com.example.bloomgift.model.Promotion;
import com.example.bloomgift.model.Size;
import com.example.bloomgift.model.Store;
import com.example.bloomgift.reponse.OrderDetailReponse;
import com.example.bloomgift.reponse.OrderReponse;
import com.example.bloomgift.reponse.ProductReponse;
import com.example.bloomgift.repository.AccountRepository;
import com.example.bloomgift.repository.OrderDetailRepository;
import com.example.bloomgift.repository.OrderRepository;
import com.example.bloomgift.repository.ProductRepository;
import com.example.bloomgift.repository.PromotionRepository;
import com.example.bloomgift.repository.SizeRepository;
import com.example.bloomgift.repository.StoreRepository;
import com.example.bloomgift.request.OrderDetailRequest;
import com.example.bloomgift.request.OrderRequest;

import ch.qos.logback.core.util.StringUtil;

@Service
public class OrderSevice {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private PromotionRepository promotionRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private SizeRepository sizeRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    public void createOrder(Integer accountID, OrderRequest orderRequest) {
        checkOrder(orderRequest);
        Account account = accountRepository.findById(accountID).orElseThrow();
        Integer promotionID = orderRequest.getPromotionID();
        Integer point = orderRequest.getPoint();
        if (point > account.getPoint()) {
            throw new RuntimeException("Insufficient points");
        }

        Promotion promotion = promotionRepository.findById(promotionID).orElseThrow();
        LocalDateTime currentDate = LocalDateTime.now();
        if (promotion.getEndDate().isBefore(currentDate)) {
            throw new RuntimeException("Khuyến mãi đã hết hạn");
        }
        Order order = new Order();
        List<OrderDetail> orderDetails = new ArrayList<>();
        float totalProductPrice = 0.0f;
        for (OrderDetailRequest orderDetailRequests : orderRequest.getOrderDetailRequests()) {
            Product product = productRepository.findById(orderDetailRequests.getProductID()).orElseThrow();
            if (product.getQuantity() < orderDetailRequests.getQuantity()) {
                throw new RuntimeException("Khuyến mãi đã hết hạn");
            }
            Store store = storeRepository.findByProducts(product);

            Integer quantity = orderDetailRequests.getQuantity();
            OrderDetail orderDetail = new OrderDetail();
            Size size = sizeRepository.findById(orderDetailRequests.getSizeID()).orElseThrow();
            if (size.getProductID() != product) {
                throw new RuntimeException("Khuyến mãi đã hết hạn");
            }
            float productTotalPrice;
            if (size.getSizeID() != null) {
                productTotalPrice = size.getPrice() * orderDetailRequests.getQuantity();

            } else {
                productTotalPrice = product.getPrice() * orderDetailRequests.getQuantity();

            }
            totalProductPrice += productTotalPrice;
            orderDetail.setProductTotalPrice(productTotalPrice);
            orderDetail.setSizeID(null);
            orderDetail.setAccountID(account);
            orderDetail.setProductID(product);
            orderDetail.setQuantity(quantity);
            orderDetail.setSizeID(size);
            orderDetail.setOrderID(order);
            orderDetail.setStoreID(store);

            Integer newProductQuantity = product.getQuantity() - quantity;
            product.setQuantity(newProductQuantity);
            product.setSold(quantity);
            orderDetails.add(orderDetail);
        }

        order.setAccountID(account);
        order.setOrderStatus("Xác nhận đơn hàng");
        order.setDeliveryAddress(orderRequest.getDeliveryAddress());
        order.setNote(orderRequest.getNote());
        order.setBanner(orderRequest.getBanner());
        order.setPromotionID(promotion);
        order.setStartDate(new Date());
        order.setPoint(point);
        float promotionValue = promotion.getPromotionDiscount().floatValue();
        float orderPrice = totalProductPrice - point - promotionValue;
        order.setOrderPrice(orderPrice); // Use float
        order.setDeliveryDateTime(orderRequest.getDeliveryDateTime());
        order.setOrderDetail(orderDetails);
        Integer newPoint = account.getPoint() - point;
        account.setPoint(newPoint);
        Integer newPromotionQuantity = promotion.getQuantity() - 1;
        promotion.setQuantity(newPromotionQuantity);
        orderRepository.save(order);
    }

    public List<OrderReponse> getAllOrder() {
        List<Order> orders = orderRepository.findAll();
        List<OrderReponse> orderReponses = orders.stream()
                .map(order -> {
                    List<OrderDetailReponse> orderDetailReponses = order.getOrderDetail().stream()
                            .map(orderDetail -> new OrderDetailReponse(
                                    orderDetail.getOrderDetailID(),
                                    orderDetail.getProductTotalPrice(),
                                    orderDetail.getQuantity(),
                                    orderDetail.getProductID().getProductName(),
                                    orderDetail.getStoreID().getStoreName(),
                                    orderDetail.getSizeID().getSizeFloat()))
                            .collect(Collectors.toList());

                    return new OrderReponse(
                            order.getOrderID(),
                            order.getOrderPrice(),
                            order.getOrderStatus(),
                            order.getPoint(),
                            order.getBanner(),
                            order.getNote(),
                            order.getStartDate(),
                            order.getDeliveryDateTime(),
                            order.getDeliveryAddress(),
                            order.getAccountID().getFullname(),
                            order.getPromotionID().getPromotionCode(),
                            orderDetailReponses);
                }).collect(Collectors.toList());

        return orderReponses;
    }

    public List<OrderReponse> getHistoryOrderByCustomer(int accountID) {
        Account account = accountRepository.findById(accountID).orElseThrow();
        List<Order> orders = orderRepository.findByAccountID(account);

        List<OrderReponse> orderReponses = orders.stream()
                .map(order -> {
                    List<OrderDetailReponse> orderDetailReponses = order.getOrderDetail().stream()
                            .map(orderDetail -> new OrderDetailReponse(
                                    orderDetail.getOrderDetailID(),
                                    orderDetail.getProductTotalPrice(),
                                    orderDetail.getQuantity(),
                                    orderDetail.getProductID().getProductName(),
                                    orderDetail.getStoreID().getStoreName(),
                                    orderDetail.getSizeID().getSizeFloat()))
                            .collect(Collectors.toList());

                    return new OrderReponse(
                            order.getOrderID(),
                            order.getOrderPrice(),
                            order.getOrderStatus(),
                            order.getPoint(),
                            order.getBanner(),
                            order.getNote(),
                            order.getStartDate(),
                            order.getDeliveryDateTime(),
                            order.getDeliveryAddress(),
                            order.getAccountID().getFullname(),
                            order.getPromotionID().getPromotionCode(),
                            orderDetailReponses);
                }).collect(Collectors.toList());

        return orderReponses;
    }

    public List<OrderReponse> getOrderByStore(int storeID) {
        Store stores = storeRepository.findById(storeID).orElseThrow();
        List<OrderDetail> orderDetails = orderDetailRepository.findByStoreID(stores);
        Set<Order> orders = orderDetails.stream()
                .map(OrderDetail::getOrderID) 
                .collect(Collectors.toSet()); 

        List<OrderReponse> orderReponses = orders.stream()
                .map(order -> {
                    List<OrderDetailReponse> orderDetailReponses = order.getOrderDetail().stream()
                            .map(orderDetail -> new OrderDetailReponse(
                                    orderDetail.getOrderDetailID(),
                                    orderDetail.getProductTotalPrice(),
                                    orderDetail.getQuantity(),
                                    orderDetail.getProductID().getProductName(),
                                    orderDetail.getStoreID().getStoreName(),
                                    orderDetail.getSizeID() != null ? orderDetail.getSizeID().getSizeFloat() : null))
                            .collect(Collectors.toList());

                    return new OrderReponse(
                            order.getOrderID(),
                            order.getOrderPrice(),
                            order.getOrderStatus(),
                            order.getPoint(),
                            order.getBanner(),
                            order.getNote(),
                            order.getStartDate(),
                            order.getDeliveryDateTime(),
                            order.getDeliveryAddress(),
                            order.getAccountID().getFullname(),
                            order.getPromotionID() != null ? order.getPromotionID().getPromotionCode() : null,
                            orderDetailReponses);
                }).collect(Collectors.toList());

        return orderReponses;
    }

    public void checkOrder(OrderRequest orderRequest) {
        String deliveryAddress = orderRequest.getDeliveryAddress();
        Date deliveryDateTime = orderRequest.getDeliveryDateTime();
        if (!StringUtils.hasText(deliveryAddress)
                || deliveryDateTime == null) {
            throw new RuntimeException("Vui lòng nhập đầy đủ thông tin");
        }

        Date currentDate = new Date();

        if (deliveryDateTime.before(currentDate) || isSameDay(deliveryDateTime, currentDate)) {
            throw new RuntimeException("Ngày giao hàng phải là ngày trong tương lai");
        }
    }

    private boolean isSameDay(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date1);
        cal2.setTime(date2);
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
    }
}
