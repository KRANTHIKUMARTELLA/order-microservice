package com.orderservice.repository;

import com.orderservice.model.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface OrderRepository extends CrudRepository<Order, Integer> {

  List<Order> findByUserId(Integer userId);
  List<Order> findByProductId(Integer productId);
}
