package com.orderservice.requestWrapper;

import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {

    private Long userId;
    private List<OrderItemRequest> items;



}
