package com.example.capstone.seller.converter;

import com.example.capstone.item.Item;
import com.example.capstone.order.OrderItem;
import com.example.capstone.seller.dto.SellerResponseDTO;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class SellerManagementConverter {

    public static SellerResponseDTO.OrderItemStatus toOrderStatus(OrderItem orderItem) {
        Item item = orderItem.getItem();

        return SellerResponseDTO.OrderItemStatus.builder()
                .orderNumber(orderItem.getId())
                .itemName(item.getName())
                .itemPreviewImageUrl(null)
                .orderedDate(orderItem.getCreatedAt())
                .consumerName(orderItem.getOrder().getMember().getName())
                .price(item.getPrice())
                .quantity(orderItem.getQuantity())
                .status(orderItem.getOrder().getStatus())
                .build();
    }

    public static SellerResponseDTO.OrderStatusList toOrderStatusList(Page<OrderItem> orderItemPage) {
        List<SellerResponseDTO.OrderItemStatus> orderItemStatusList = orderItemPage.stream()
                .map(SellerManagementConverter::toOrderStatus)
                .collect(Collectors.toList());

        return SellerResponseDTO.OrderStatusList.builder()
                .listSize(orderItemPage.getSize())
                .page(orderItemPage.getTotalPages())
                .totalElement(orderItemPage.getTotalElements())
                .isFirst(orderItemPage.isFirst())
                .isLast(orderItemPage.isLast())
                .orderItemStatusList(orderItemStatusList)
                .build();
    }
}
