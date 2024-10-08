package com.example.bloomgift.service;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;
import java.io.InputStreamReader;
import com.google.gson.JsonParser;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ctc.wstx.shaded.msv_core.util.Uri;
import com.example.bloomgift.model.Account;
import com.example.bloomgift.model.Delivery;
import com.example.bloomgift.model.DistanceFee;
import com.example.bloomgift.model.Order;
import com.example.bloomgift.model.OrderDetail;
import com.example.bloomgift.model.Payment;
import com.example.bloomgift.model.Product;
import com.example.bloomgift.model.Promotion;
import com.example.bloomgift.model.Size;
import com.example.bloomgift.model.Store;
import com.example.bloomgift.reponse.OrderDetailReponse;
import com.example.bloomgift.reponse.OrderReponse;
import com.example.bloomgift.repository.AccountRepository;
import com.example.bloomgift.repository.DeliveryRepository;
import com.example.bloomgift.repository.DistanceFeeRepository;
import com.example.bloomgift.repository.OrderDetailRepository;
import com.example.bloomgift.repository.OrderRepository;
import com.example.bloomgift.repository.PaymentRepository;
import com.example.bloomgift.repository.ProductRepository;
import com.example.bloomgift.repository.PromotionRepository;
import com.example.bloomgift.repository.SizeRepository;
import com.example.bloomgift.repository.StoreRepository;
import com.example.bloomgift.request.DeliveryRequest;
import com.example.bloomgift.request.OrderDetailRequest;
import com.example.bloomgift.request.OrderRequest;
import com.example.bloomgift.request.OrderRequestNew;

import java.io.BufferedReader;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;

@Service
public class OrderService {
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

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private CartService cartService;

    @Autowired
    private DeliveryRepository deliveryRepository;


    
    @Autowired
    private DistanceFeeRepository distanceFeeRepository;
    public Double makeMoneyDeliveryWithManyStore(DeliveryRequest deliveryRequest, List<Integer> storeIDs) {
        CheckAddress(deliveryRequest);
        Double totalCost = 0.0;
        String specificAddress = deliveryRequest.getSpecificAddress();
        String deliveryProvince = deliveryRequest.getDeliveryProvince();
        String deliveryDistrict = deliveryRequest.getDeliveryDistrict();
        String deliveryWard = deliveryRequest.getDeliveryWard();
        String deliveryAddress = specificAddress + ", " + deliveryWard + ", " + deliveryDistrict + ", " + deliveryProvince;
    
        if (!isValidAddress(deliveryAddress)) {
            throw new RuntimeException("Invalid delivery address");
        }
    
        for (Integer storeID : storeIDs) {
            Store store = storeRepository.findById(storeID).orElseThrow(() -> new RuntimeException("Invalid store with ID: " + storeID));
            
            DistanceFee distanceFee = distanceFeeRepository.findByStore_StoreID(storeID);
            if (distanceFee == null) {
                throw new RuntimeException("No distance fee found for store with ID: " + storeID);
            }
            
            String storeAddress = store.getStoreAddress();
            Double deliveryDistance = haversineDistance(deliveryAddress, storeAddress);
            if (deliveryDistance != null) {
                Double deliveryCost = (deliveryDistance / distanceFee.getRange()) * distanceFee.getFee();
                totalCost += deliveryCost;
            }
        }
        
        return totalCost; 
    }
    public Double makeMoneyDelivery(DeliveryRequest deliveryRequest, Integer storeID) {
        CheckAddress(deliveryRequest);
        Store store = storeRepository.findById(storeID).orElseThrow();
        DistanceFee distanceFee = distanceFeeRepository.findByStore_StoreID(storeID);
        if (store == null) {
            throw new RuntimeException("invalid store");
        }
        String specificAddress = deliveryRequest.getSpecificAddress();
        String deliveryProvince = deliveryRequest.getDeliveryProvince();
        String deliveryDistrict = deliveryRequest.getDeliveryDistrict();
        String deliveryWard = deliveryRequest.getDeliveryWard();
        String deliveryAddress = specificAddress + ", " + deliveryWard + ", " + deliveryDistrict + ", "
                + deliveryProvince;
        if (!isValidAddress(deliveryAddress)) {
            throw new RuntimeException("Invalid delivery address");
        }
        String storeAddress = store.getStoreAddress();
        Double delivery = haversineDistance(deliveryAddress, storeAddress);
        Double money = (delivery / distanceFee.getRange()) * distanceFee.getFee();
        return money;
    }

    public void createOrder(Integer accountID, OrderRequest orderRequest) {
        checkOrder(orderRequest);
        Account account = accountRepository.findById(accountID).orElseThrow();
        Integer promotionID = orderRequest.getPromotionID();
        Integer point = orderRequest.getPoint();
        String deliveryProvince = orderRequest.getDeliveryProvince();
        String deliveryDistrict = orderRequest.getDeliveryDistrict();
        String deliveryWard = orderRequest.getDeliveryWard();
        String specificAddress = orderRequest.getSpecificAddress();
        Boolean transfer = orderRequest.getTransfer();
        String deliveryAddress;

        deliveryAddress = specificAddress + ", " + deliveryWard + ", " + deliveryDistrict + ", " + deliveryProvince;
        if (!isValidAddress(deliveryAddress)) {
            throw new RuntimeException("Invalid delivery address");
        }
        if (point > account.getPoint()) {
            throw new RuntimeException("Insufficient points");
        }
        Promotion promotion = null;
        float promotionValue = 0.0f;
        if (promotionID != null && promotionID != 0) {
            promotion = promotionRepository.findById(promotionID)
                    .orElseThrow(() -> new RuntimeException("Khuyến mãi không tồn tại"));

            LocalDateTime currentDate = LocalDateTime.now();
            if (promotion.getEndDate().isBefore(currentDate)) {
                throw new RuntimeException("Khuyến mãi đã hết hạn");
            }

            promotionValue = promotion.getPromotionDiscount().floatValue();

            Integer newPromotionQuantity = promotion.getQuantity() - 1;
            promotion.setQuantity(newPromotionQuantity);
        }
        Order order = new Order();
        List<OrderDetail> orderDetails = new ArrayList<>();
        float totalProductPrice = 0.0f;
        Store firstStore = null;
        Map<String, Object> cart = cartService.getCart(account);
        List<Map<String, Object>> cartItems = (List<Map<String, Object>>) cart.get("cartItems");
        List<Delivery> deliveries = new ArrayList<>();
        for (Map<String, Object> cartItem : cartItems) {
            Integer productID = (Integer) cartItem.get("productID");
            Integer quantity = (Integer) cartItem.get("quantity");
            Product product = productRepository.findById(productID).orElseThrow();
            if (product.getQuantity() < quantity) {
                throw new RuntimeException("hết sản phẩm");
            }
            Store store = storeRepository.findByProducts(product);
            DistanceFee distanceFee = distanceFeeRepository.findByStore(store);
            Double delivery = haversineDistance(deliveryAddress, store.getStoreAddress());
            Float range = distanceFee.getRange();
            Float fee = distanceFee.getFee();
            Double moneyFee = (delivery / range) * fee;


            if (firstStore == null) {
                firstStore = store;
            }
            Integer sizeID = cartItem.containsKey("sizeID") ? (Integer) cartItem.get("sizeID") : null;
            OrderDetail orderDetail = new OrderDetail();
            float productTotalPrice = 0.0f;
            Double productTotalPriceWithFee = 0.0d;
            Size size = null;

            if (sizeID != null && sizeID != 0) {
                size = sizeRepository.findById(sizeID)
                        .orElseThrow(() -> new RuntimeException("Không tồn tại size này"));

                if (!size.getProductID().equals(product)) {
                    throw new RuntimeException("Size không khớp với sản phẩm");
                }

                if (size.getSizeQuantity() < quantity) {
                    throw new RuntimeException("Hết size này");
                }
                productTotalPrice = size.getPrice() * quantity;
                productTotalPriceWithFee = productTotalPrice + moneyFee;
            } else {
                float discount = product.getDiscount() != null ? product.getDiscount() : 0;
                productTotalPrice = (product.getPrice() * (1 - discount / 100)) * quantity ;
                productTotalPriceWithFee = productTotalPrice + moneyFee;
            }

            if (size != null) {
                size.setSizeQuantity(size.getSizeQuantity() - quantity);
            } else {
                product.setQuantity(product.getQuantity() - quantity);
            }

            totalProductPrice += productTotalPriceWithFee;
            orderDetail.setProductTotalPrice(productTotalPrice);
            orderDetail.setSizeID(size);
            orderDetail.setAccountID(account);
            orderDetail.setProductID(product);
            orderDetail.setQuantity(quantity);
            orderDetail.setSizeID(size);
            orderDetail.setOrderID(order);
            orderDetail.setStoreID(store);
            Integer newSold = product.getSold() + quantity;
            product.setSold(newSold);
            orderDetails.add(orderDetail);

            Delivery delivery2 = new Delivery();
            delivery2.setFreeShip(false);
            delivery2.setOrderID(order);
            delivery2.setOrderCode("");
            delivery2.setShip(moneyFee.floatValue());
            delivery2.setStatus(false);
            delivery2.setStoreID(store);
            deliveries.add(delivery2);
        }
        deliveryRepository.saveAll(deliveries);
        order.setAccountID(account);
        order.setOrderStatus("Xác nhận đơn hàng");
        order.setDeliveryAddress(deliveryAddress);
        order.setNote(orderRequest.getNote());
        order.setBanner(orderRequest.getBanner());
        order.setPromotionID(promotion);
        order.setStartDate(new Date());
        order.setPoint(point);
        order.setPhone(orderRequest.getPhone());
        float orderPrice = totalProductPrice - point - promotionValue;
        order.setOrderPrice(orderPrice);
        order.setDeliveryDateTime(orderRequest.getDeliveryDateTime());
        order.setOrderDetail(orderDetails);
        order.setTransfer(transfer);
        Integer newPoint = account.getPoint() - point;
        account.setPoint(newPoint);
        orderRepository.save(order);
        if (transfer == true) {
            Payment payment = new Payment();
            payment.setOrderID(order);
            payment.setAccountID(accountID);
            payment.setMethod("chuyển khoản");
            // payment.setStoreID(firstStore);
            payment.setBankName("TP BANK");
            payment.setPaymentStatus(false);
            payment.setTotalPrice(orderPrice);
            payment.setBankNumber("09154473727");
            payment.setMomoNumber("********135");
            // payment.setPaymentCode();
            payment.setBankAccountName("TRAN THI PHUONG THAO");
            payment.setFormat("text");
            payment.setTemplate("compact");
            // payment.setAcqId("12312");
            paymentRepository.save(payment);
        }
        cartService.clearCart(account);
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
                                    orderDetail.getSizeID().getText()))
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
                            order.getPhone(),
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
                                    orderDetail.getSizeID() != null ? orderDetail.getSizeID().getText() : null))
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
                            order.getPhone(),
                            orderDetailReponses);
                }).collect(Collectors.toList());

        return orderReponses;
    }

    public List<OrderReponse> getOrderByOrderID(int orderID) {
        Order order = orderRepository.findById(orderID).orElseThrow();
        List<OrderReponse> orderReponses = order.getOrderDetail().stream()
                .map(orderDetail -> {
                    List<OrderDetailReponse> orderDetailReponses = order.getOrderDetail().stream()
                            .map(orderDetail1 -> new OrderDetailReponse(
                                    orderDetail1.getOrderDetailID(),
                                    orderDetail1.getProductTotalPrice(),
                                    orderDetail1.getQuantity(),
                                    orderDetail1.getProductID().getProductName(),
                                    orderDetail1.getStoreID().getStoreName(),
                                    orderDetail1.getSizeID() != null ? orderDetail1.getSizeID().getText() : null))
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
                            order.getPhone(),
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
                                    orderDetail.getSizeID() != null ? orderDetail.getSizeID().getText() : null))
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
                            order.getPhone(),
                            orderDetailReponses);
                }).collect(Collectors.toList());

        return orderReponses;
    }

    private boolean isSameDay(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date1);
        cal2.setTime(date2);
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
    }

    public List<OrderReponse> getOrderByOrderStatus(String orderStatus) {
        List<Order> orders = orderRepository.findByOrderStatus(orderStatus);
        List<OrderReponse> orderReponses = orders.stream()
                .map(order -> {
                    List<OrderDetailReponse> orderDetailReponses = order.getOrderDetail().stream()
                            .map(orderDetail -> new OrderDetailReponse(
                                    orderDetail.getOrderDetailID(),
                                    orderDetail.getProductTotalPrice(),
                                    orderDetail.getQuantity(),
                                    orderDetail.getProductID().getProductName(),
                                    orderDetail.getStoreID().getStoreName(),
                                    orderDetail.getSizeID() != null ? orderDetail.getSizeID().getText() : null))
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
                            order.getPhone(),
                            orderDetailReponses);
                }).collect(Collectors.toList());

        return orderReponses;
    }

    public Map<String, Double> GetCoordinatesFromAddress(String address) {
        try {
            Map<String, Double> map = new HashMap<>();
            String encodedAddress = URLEncoder.encode(address, StandardCharsets.UTF_8.toString());
            String urlString = "https://nominatim.openstreetmap.org/search?q=" + encodedAddress
                    + "&format=json&addressdetails=1";

            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0");
            // Read the response
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }

            // Close connections
            in.close();
            conn.disconnect();
            JsonElement jsonElement = JsonParser.parseString(content.toString());
            JsonArray jsonArray = jsonElement.getAsJsonArray();

            if (jsonArray.size() > 0) {
                JsonObject firstObject = jsonArray.get(0).getAsJsonObject();
                Double lat = firstObject.get("lat").getAsDouble();
                Double lng = firstObject.get("lon").getAsDouble();
                map.put("lat", lat);
                map.put("lon", lng);
            }
            return map;
        } catch (Exception e) {
            throw new RuntimeException("fail");
        }
    }

    public Double haversineDistance(String deliveryAddress, String storeAddress) {
        int Radius = 6371; // Bán kính Trái Đất tính bằng km
        Map<String, Double> deliveryCoordinates = GetCoordinatesFromAddress(deliveryAddress);
        Map<String, Double> storeCoordinates = GetCoordinatesFromAddress(storeAddress);

        if (deliveryCoordinates == null || storeCoordinates == null ||
                deliveryCoordinates.isEmpty() || storeCoordinates.isEmpty()) {
            return null; // Trả về null nếu không tìm thấy tọa độ
        }

        Double lat1 = deliveryCoordinates.get("lat");
        Double lon1 = deliveryCoordinates.get("lon");
        Double lat2 = storeCoordinates.get("lat");
        Double lon2 = storeCoordinates.get("lon");

        // Chuyển đổi độ sang radian
        Double dLat = Math.toRadians(lat2 - lat1);
        Double dLon = Math.toRadians(lon2 - lon1);
        Double la1ToRad = Math.toRadians(lat1);
        Double la2ToRad = Math.toRadians(lat2);

        // Công thức Haversine
        Double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(la1ToRad) * Math.cos(la2ToRad)
                        * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        Double distance = Radius * c; // Khoảng cách tính bằng km

        return distance;
    }

    private boolean isValidAddress(String address) {
        try {
            // Mã hóa địa chỉ
            String encodedAddress = URLEncoder.encode(address, StandardCharsets.UTF_8.toString());

            // Construct the API URL
            String urlString = "https://nominatim.openstreetmap.org/search?q=" + encodedAddress
                    + "&format=json&limit=1";
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0"); // Nominatim requires a user-agent

            // Read the response
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }

            // Close connections
            in.close();
            conn.disconnect();

            // Parse the JSON response
            JsonElement jsonElement = JsonParser.parseString(content.toString());
            JsonArray jsonArray = jsonElement.getAsJsonArray();

            // If the array is empty, the address is invalid
            return jsonArray.size() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false; // In case of an error, treat the address as invalid
        }
    }

    public void CheckAddress(DeliveryRequest deliveryRequest) {
        String specificAddress = deliveryRequest.getSpecificAddress();
        String deliveryProvince = deliveryRequest.getDeliveryProvince();
        String deliveryDistrict = deliveryRequest.getDeliveryDistrict();
        String deliveryWard = deliveryRequest.getDeliveryWard();

        List<String> validDistricts = Arrays.asList("Quận 1", "Quận 2", "Quận 3", "Quận 4", "Quận 5", "Quận 6",
                "Quận 7", "Quận 8", "Quận 9", "Quận 10", "Quận 11",
                "Quận 12", "Quận Bình Thạnh", "Quận Gò Vấp", "Quận Phú Nhuận",
                "Quận Tân Bình", "Quận Tân Phú", "Quận Bình Tân",
                "Huyện Củ Chi", "Huyện Hóc Môn", "Huyện Bình Chánh", "Huyện Nhà Bè",
                "Huyện Cần Giờ", "Thành phố Thủ Đức");

        if (!validDistricts.contains(deliveryDistrict)) {
            throw new RuntimeException("Invalid district for Hồ Chí Minh");
        }
        if (!StringUtils.hasText(deliveryProvince)
                || !StringUtils.hasText(specificAddress)
                || !StringUtils.hasText(deliveryDistrict)
                || !StringUtils.hasText(deliveryWard)) {
            throw new RuntimeException("Vui lòng nhập đầy đủ thông tin");
        }
    }

    public void checkOrder(OrderRequest orderRequest) {
        Date deliveryDateTime = orderRequest.getDeliveryDateTime();
        String deliveryProvince = orderRequest.getDeliveryProvince();
        String specificAddress = orderRequest.getSpecificAddress();
        String deliveryDistrict = orderRequest.getDeliveryDistrict();
        Integer phone = orderRequest.getPhone();
        List<String> validDistricts = Arrays.asList("Quận 1", "Quận 2", "Quận 3", "Quận 4", "Quận 5", "Quận 6",
                "Quận 7", "Quận 8", "Quận 9", "Quận 10", "Quận 11",
                "Quận 12", "Quận Bình Thạnh", "Quận Gò Vấp", "Quận Phú Nhuận",
                "Quận Tân Bình", "Quận Tân Phú", "Quận Bình Tân",
                "Huyện Củ Chi", "Huyện Hóc Môn", "Huyện Bình Chánh", "Huyện Nhà Bè",
                "Huyện Cần Giờ", "Thành phố Thủ Đức");
        // if (!validDistricts.contains(deliveryDistrict)) {
        // throw new RuntimeException("Invalid district for Hồ Chí Minh");
        // }
        if (!StringUtils.hasText(deliveryProvince)
                || !StringUtils.hasText(specificAddress)
                || !StringUtils.hasText(deliveryDistrict)
                || deliveryDateTime == null
                || phone == null) {
            throw new RuntimeException("Vui lòng nhập đầy đủ thông tin");
        }

        Date currentDate = new Date();

        if (deliveryDateTime.before(currentDate) || isSameDay(deliveryDateTime, currentDate)) {
            throw new RuntimeException("Ngày giao hàng phải là ngày trong tương lai");
        }
    }

    public void checkOrderNew(OrderRequestNew orderRequestNew) {
        Integer phone = orderRequestNew.getPhone();
        Date deliveryDateTime = orderRequestNew.getDeliveryDateTime();
        if (deliveryDateTime == null || phone == null) {
            throw new RuntimeException("Vui lòng nhập đầy đủ thông tin");
        }
        Date currentDate = new Date();

        if (deliveryDateTime.before(currentDate) || isSameDay(deliveryDateTime, currentDate)) {
            throw new RuntimeException("Ngày giao hàng phải là ngày trong tương lai");
        }
    }

}