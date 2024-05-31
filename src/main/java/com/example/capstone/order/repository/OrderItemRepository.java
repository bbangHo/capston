package com.example.capstone.order.repository;

import com.example.capstone.order.Order;
import com.example.capstone.order.OrderItem;
import com.example.capstone.order.repository.custom.OrderItemRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long>, OrderItemRepositoryCustom {

    List<OrderItem> findByOrderId(Long orderId);
}
