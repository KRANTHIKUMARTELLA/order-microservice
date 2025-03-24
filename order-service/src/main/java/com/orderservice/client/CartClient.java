package com.orderservice.client;

import com.orderservice.requestWrapper.Cart;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="cart-service",url = "http://localhost:8080/cart")
public interface CartClient {
    @GetMapping("/{userId}")
    Cart getCart(@PathVariable Integer userId) ;

}
