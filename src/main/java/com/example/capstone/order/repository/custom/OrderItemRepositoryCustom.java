package com.example.capstone.order.repository.custom;

import com.example.capstone.order.OrderItem;
import com.example.capstone.order.common.DateType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepositoryCustom {
    Page<OrderItem> getSellerOrderItemStatus(Long sellerId, Pageable pageable);

    List<Integer> getSalesVolume(Long sellerId, DateType dateType);
}
