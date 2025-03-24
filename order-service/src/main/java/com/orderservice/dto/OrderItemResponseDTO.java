package com.orderservice.dto;

import lombok.Data;

@Data
public class OrderItemResponseDTO {

    private Integer productId;
    private Integer quantity;
    private Double unitPrice;
    private Double subTotal;
}
