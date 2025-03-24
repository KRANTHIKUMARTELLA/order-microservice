package com.orderservice.controller;
import com.orderservice.dto.OrderResponseDTO;
import com.orderservice.model.Order;
import com.orderservice.requestWrapper.Cart;
import com.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<OrderResponseDTO> createOrder(Cart request){

        return new ResponseEntity<>(this.orderService.createOrder(request), HttpStatus.CREATED);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponseDTO> getOrderById(@PathVariable Integer orderId) {
        OrderResponseDTO response = orderService.getOrderById(orderId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderResponseDTO>> getOrdersByUserId(@PathVariable Integer userId) {
        List<OrderResponseDTO> response = orderService.getOrdersByUserId(userId);
        return ResponseEntity.ok(response);
    }
}
