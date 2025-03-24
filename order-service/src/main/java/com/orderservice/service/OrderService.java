package com.orderservice.service;
import com.orderservice.client.CartClient;
import com.orderservice.client.ProductClient;
import com.orderservice.client.UserClient;
import com.orderservice.dto.OrderResponseDTO;
import com.orderservice.model.Order;
import com.orderservice.model.OrderItems;
import com.orderservice.repository.OrderItemsRepository;
import com.orderservice.repository.OrderRepository;
import com.orderservice.requestWrapper.Cart;
import com.orderservice.requestWrapper.CartItem;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartClient cartClient;

    @Autowired
    private ProductClient productClient;

    @Autowired
    private UserClient userClient;

    @Autowired
    private OrderItemsRepository orderItemsRepository;

    @Autowired
    private ModelMapper modelMapper;

    public OrderResponseDTO createOrder(Integer userId) {

        Cart cart=cartClient.getCart(userId);

        // Validate and fetch product details for each item
        List<OrderItems> orderItemsList = new ArrayList<>();
        for (CartItem itemRequest : cart.getItems()) {
            validateProductAvailability(itemRequest.getProductId(), itemRequest.getQuantity());
            orderItemsList.add(mapToOrderItem(itemRequest));
        }

        //Calculate total price
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (OrderItems orderItem : orderItemsList) {
            totalPrice = totalPrice.add(orderItem.getSubtotal());
        }

        // Create Order entity
        Order order = new Order();
        order.setUserId(userId);
        order.setOrderItemsList(orderItemsList);
        order.setTotalPrice(totalPrice);
        order.setOrderDate(new Date());
        order.setStatus(Order.OrderStatus.PENDING);

        // Save order and order items
        Order savedOrder = orderRepository.save(order);
        for (OrderItems orderItem : orderItemsList) {
            orderItem.setOrder(savedOrder);
        }
        orderItemsRepository.saveAll(orderItemsList);

        return modelMapper.map(savedOrder,OrderResponseDTO.class);
    }

    private void validateProductAvailability(Integer productId, Integer quantity) {
        if (!productClient.isProductAvailable(productId, quantity)) {
            throw new IllegalArgumentException("Product with ID " + productId + " is not available in the required quantity.");
        }
        productClient.deductStock(productId, quantity);// Deduct stock from the inventory

    }

    private OrderItems mapToOrderItem(CartItem itemRequest) {
        BigDecimal unitPrice = productClient.getProductById(itemRequest.getProductId()).getPrice(); // Fetch price from ProductService
        OrderItems orderItem = new OrderItems();
        orderItem.setProductId(itemRequest.getProductId());
        orderItem.setQuantity(itemRequest.getQuantity());
        orderItem.setUnitPrice(unitPrice);
        orderItem.setSubtotal(unitPrice.multiply(BigDecimal.valueOf(itemRequest.getQuantity())));
        return orderItem;
    }

    public OrderResponseDTO getOrderById(Integer orderId) {

        Order order = orderRepository.findById(orderId).get();

        return modelMapper.map(order,OrderResponseDTO.class);
    }

    public List<OrderResponseDTO> getOrdersByUserId(Integer userId) {

         List<Order> orders= orderRepository.findByUserId(userId);

         return orders.stream()
                 .map(order -> modelMapper.map(order, OrderResponseDTO.class))
                 .collect(Collectors.toList());
    }


    public void deleteOrder(Integer orderId) {
        if (orderRepository.existsById(orderId)) {
            orderRepository.deleteById(orderId);
        } else {
            throw new RuntimeException("Order not found with ID: " + orderId);
        }
    }
}
