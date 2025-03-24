package com.orderservice.requestWrapper;

import com.orderservice.dto.Users;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
@Data
public class Cart {


    private Integer cartId;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<CartItem> items;

    private Double totalPrice;

    @OneToOne
    @JoinColumn(name="userId",nullable=false)
    private Users user;

    public void calculateTotalPrice() {
        this.totalPrice = items.stream()
                .mapToDouble(item -> item.getUnitPrice() * item.getQuantity())
                .sum();
    }
}
