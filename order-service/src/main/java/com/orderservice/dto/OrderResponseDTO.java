package com.orderservice.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderResponseDTO {

    private Long orderId;
    private Integer userId;
    private Double totalPrice;
    private String status;
    private List<OrderItemResponseDTO> items;
}
