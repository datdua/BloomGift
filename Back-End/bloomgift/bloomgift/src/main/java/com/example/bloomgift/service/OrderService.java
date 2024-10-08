package com.example.bloomgift.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.bloomgift.model.Account;
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
import com.example.bloomgift.repository.OrderDetailRepository;
import com.example.bloomgift.repository.OrderRepository;
import com.example.bloomgift.repository.PaymentRepository;
import com.example.bloomgift.repository.ProductRepository;
import com.example.bloomgift.repository.PromotionRepository;
import com.example.bloomgift.repository.SizeRepository;
import com.example.bloomgift.repository.StoreRepository;
import com.example.bloomgift.request.OrderRequest;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

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
        if (isValidAddress(deliveryAddress)) {
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
            promotion.setQuantity(promotion.getQuantity() - 1);
        }

        Map<Store, List<OrderDetail>> storeOrderDetailsMap = new HashMap<>();
        float totalProductPrice = 0.0f;

        // Sử dụng CartService để lấy thông tin giỏ hàng
        Map<String, Object> cart = cartService.getCart(account);
        List<Map<String, Object>> cartItems = (List<Map<String, Object>>) cart.get("cartItems");

        for (Map<String, Object> cartItem : cartItems) {
            Integer productID = (Integer) cartItem.get("productID");
            Integer quantity = (Integer) cartItem.get("quantity");
            Product product = productRepository.findById(productID).orElseThrow();
            if (product.getQuantity() < quantity) {
                throw new RuntimeException("Hết sản phẩm");
            }

            Store store = storeRepository.findByProducts(product);
            Integer sizeID = cartItem.containsKey("sizeID") ? (Integer) cartItem.get("sizeID") : null;
            OrderDetail orderDetail = new OrderDetail();
            float productTotalPrice = 0.0f;
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
            } else {
                float discount = product.getDiscount() != null ? product.getDiscount() : 0;
                productTotalPrice = (product.getPrice() * (1 - discount / 100)) * quantity;
            }

            if (size != null) {
                size.setSizeQuantity(size.getSizeQuantity() - quantity);
            } else {
                product.setQuantity(product.getQuantity() - quantity);
            }

            totalProductPrice += productTotalPrice;
            orderDetail.setProductTotalPrice(productTotalPrice);
            orderDetail.setSizeID(size);
            orderDetail.setAccountID(account);
            orderDetail.setProductID(product);
            orderDetail.setQuantity(quantity);
            orderDetail.setSizeID(size);
            orderDetail.setOrderID(null); // Sẽ gán sau khi tạo đơn hàng
            orderDetail.setStoreID(store);

            // Thêm chi tiết đơn hàng vào bản đồ dựa trên cửa hàng
            storeOrderDetailsMap.computeIfAbsent(store, k -> new ArrayList<>()).add(orderDetail);
        }

        // Tạo một đơn hàng cho mỗi cửa hàng
        for (Map.Entry<Store, List<OrderDetail>> entry : storeOrderDetailsMap.entrySet()) {
            Store store = entry.getKey();
            List<OrderDetail> orderDetails = entry.getValue();

            Order order = new Order();
            order.setAccountID(account);
            order.setOrderStatus("Xác nhận đơn hàng");
            order.setDeliveryAddress(deliveryAddress);
            order.setNote(orderRequest.getNote());
            order.setBanner(orderRequest.getBanner());
            order.setPromotionID(promotion);
            order.setStartDate(new Date());
            order.setPoint(point);
            order.setPhone(orderRequest.getPhone());
            order.setOrderDetail(orderDetails);
            order.setTransfer(transfer);

            double orderPriceDouble = orderDetails.stream()
                    .mapToDouble(OrderDetail::getProductTotalPrice)
                    .sum() - point - promotionValue;
            float orderPrice = (float) orderPriceDouble;
            order.setOrderPrice(orderPrice);
            order.setDeliveryDateTime(orderRequest.getDeliveryDateTime());

            // Gán đơn hàng cho từng chi tiết đơn hàng
            for (OrderDetail orderDetail : orderDetails) {
                orderDetail.setOrderID(order);
            }

            orderRepository.save(order);

            // Xử lý thanh toán nếu có
            if (transfer) {
                Payment payment = new Payment();
                payment.setOrderID(order);
                payment.setAccountID(accountID);
                payment.setMethod("chuyển khoản");
                payment.setStoreID(store);
                payment.setBankName(store.getBankAddress());
                payment.setPaymentStatus(false);
                payment.setTotalPrice(orderPrice);
                payment.setBankNumber(store.getBankNumber());
                payment.setMomoNumber(store.getStorePhone());
                payment.setPaymentCode();
                payment.setBankAccountName(store.getBankAccountName());
                payment.setFormat("text");
                payment.setTemplate("compact");
                payment.setAcqId(store.getAcqId());
                paymentRepository.save(payment);
            }
        }

        // Xóa giỏ hàng sau khi đặt hàng thành công
        cartService.clearCart(account);
    }

    // public void deliveryMoney(Integer accountId, Integer storeId, DeliveryRequest
    // deliveryRequest) {
    // Optional<Account> account = accountRepository.findById(accountId);
    // if (account == null) {
    // return null;
    // }
    // }
    // API để lấy tất cả đơn hàng (đã có)
    public List<OrderReponse> getAllOrder() {
        List<Order> orders = orderRepository.findAll();
        List<OrderReponse> orderReponses = orders.stream()
                .map(this::mapOrderToOrderResponse)
                .collect(Collectors.toList());
        return orderReponses;
    }

    // API để lấy lịch sử đơn hàng của một khách hàng (đã có)
    public List<OrderReponse> getHistoryOrderByCustomer(int accountID) {
        Account account = accountRepository.findById(accountID).orElseThrow();
        List<Order> orders = orderRepository.findByAccountID(account);
        List<OrderReponse> orderReponses = orders.stream()
                .map(this::mapOrderToOrderResponse)
                .collect(Collectors.toList());
        return orderReponses;
    }

    // API mới để lấy chi tiết đơn hàng theo orderID
    public OrderReponse getOrderByOrderID(int orderID) {
        Order order = orderRepository.findById(orderID)
                .orElseThrow(() -> new RuntimeException("Order không tồn tại"));
        return mapOrderToOrderResponse(order);
    }

    // Phương thức chung để map Order thành OrderReponse
    private OrderReponse mapOrderToOrderResponse(Order order) {
        List<OrderDetailReponse> orderDetailReponses = order.getOrderDetail().stream()
                .map(orderDetail -> new OrderDetailReponse(
                        orderDetail.getOrderDetailID(),
                        orderDetail.getProductTotalPrice(),
                        orderDetail.getQuantity(),
                        orderDetail.getProductID().getProductName(),
                        orderDetail.getStoreID().getStoreName(),
                        orderDetail.getSizeID() != null ? orderDetail.getSizeID().getText() : null,
                        orderDetail.getProductID().getProductID(),
                        orderDetail.getStoreID().getStoreID()))
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
    }

    public List<OrderReponse> getOrderByStore(int storeID) {
        Store store = storeRepository.findById(storeID).orElseThrow(() -> new RuntimeException("Store không tồn tại"));
        List<Order> orders = orderRepository.findByOrderDetailStoreID(store);
        List<OrderReponse> orderReponses = orders.stream()
                .map(this::mapOrderToOrderResponse)
                .collect(Collectors.toList());
        return orderReponses;
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

    private boolean isSameDay(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date1);
        cal2.setTime(date2);
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
                && cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
    }

    public List<OrderReponse> getOrderByOrderStatus(String orderStatus) {
        List<Order> orders = orderRepository.findByOrderStatus(orderStatus);
        List<OrderReponse> orderReponses = orders.stream()
                .map(this::mapOrderToOrderResponse)
                .collect(Collectors.toList());
        return orderReponses;
    }

}
