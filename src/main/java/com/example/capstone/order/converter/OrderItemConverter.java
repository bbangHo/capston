package com.example.capstone.order.converter;

import com.example.capstone.item.converter.ItemConverter;
import com.example.capstone.item.dto.ItemResponseDTO;
import com.example.capstone.order.Cart;
import com.example.capstone.order.Order;
import com.example.capstone.order.OrderItem;
import com.example.capstone.order.dto.OrderItemResponseDTO;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.capstone.item.converter.ItemConverter.toItemResponseDTO;


public class OrderItemConverter {

    public static OrderItemResponseDTO.OrderItem toOrderItemResponseDTO(OrderItem orderItem) {
        return OrderItemResponseDTO.OrderItem.builder()
                .id(orderItem.getId())
                .orderId(orderItem.getOrder().getId())
                .item(toItemResponseDTO(orderItem.getItem()))
                .status(orderItem.getStatus())
                .quantity(orderItem.getQuantity())
                .createdAt(orderItem.getCreatedAt())
                .build();
    }

    public static OrderItemResponseDTO.OrderList toItemStatusListDTO(Page<OrderItem> orderItems) {
        List<OrderItemResponseDTO.OrderItem> orderItemList = orderItems.getContent().stream()
                .map(OrderItemConverter::toOrderItemResponseDTO)
                .collect(Collectors.toList());

        return OrderItemResponseDTO.OrderList.builder()
                .listSize(orderItems.getSize())
                .totalElement(orderItems.getTotalElements())
                .isFirst(orderItems.isFirst())
                .isLast(orderItems.isLast())
                .orderItemList(orderItemList)
                .build();
    }

    public static OrderItemResponseDTO.OrderItemWithSum toOrderGroupItemResponseDTO(OrderItem orderItem, Integer currentSUm) {
        return OrderItemResponseDTO.OrderItemWithSum.builder()
                .id(orderItem.getId())
                .orderId(orderItem.getOrder().getId())
                .item(toItemResponseDTO(orderItem.getItem()))
                .status(orderItem.getStatus())
                .quantity(orderItem.getQuantity())
                .createdAt(orderItem.getCreatedAt())
                .targetQuantity(orderItem.getItem().getGroupPurchaseItem().getTargetQuantity())
                .currentSum(currentSUm)
                .build();
    }
}
