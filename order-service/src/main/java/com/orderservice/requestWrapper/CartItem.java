package com.orderservice.requestWrapper;

import jakarta.persistence.*;
import lombok.Data;

@Data
public class CartItem {

    private Integer id;
    private Integer productId;
    private Integer quantity;
    private Integer unitPrice;
    private Integer subTotal;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cartId", nullable = false)
    private Cart cart;
}
