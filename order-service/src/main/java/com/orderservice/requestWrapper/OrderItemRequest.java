package com.orderservice.requestWrapper;

import lombok.Data;

@Data
public class OrderItemRequest {

    private Long productId;
    private Integer quantity;
}
