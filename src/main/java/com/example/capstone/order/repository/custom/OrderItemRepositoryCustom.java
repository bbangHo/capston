package com.example.capstone.order.repository.custom;

import com.example.capstone.order.OrderItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepositoryCustom {
    Page<OrderItem> getSellerOrderItemStatus(Long sellerId, Pageable pageable);
}
