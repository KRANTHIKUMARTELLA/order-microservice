package com.orderservice.client;

import com.orderservice.dto.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name="product-service")
public interface ProductClient {
    @GetMapping("/get/{id}")
    Product getProductById(@PathVariable Integer id);

    @GetMapping("/available/{id}/{quantity}")
    boolean isProductAvailable(@PathVariable Integer id,@PathVariable Integer quantity );

    @PostMapping("/deduct/{id}/{quantity}")
    void deductStock(@PathVariable Integer id,@PathVariable Integer quantity);

}
