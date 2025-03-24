package com.orderservice.client;

import com.orderservice.dto.Product;
import com.orderservice.dto.Users;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="user-service")
public interface  UserClient {

    @GetMapping("/products/{id}")
    Users getUserById(@PathVariable Long id);
}
