package com.example.capstone.order.dto;

import com.example.capstone.item.Item;
import com.example.capstone.item.Review;
import com.example.capstone.item.dto.ItemResponseDTO;
import com.example.capstone.order.Order;
import com.example.capstone.order.common.OrderStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.List;

public class OrderItemResponseDTO {

    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderItem {

        private Long id;

        private Long orderId;

        private ItemResponseDTO.Item item;

        private Integer quantity;

        private OrderStatus status;

        private LocalDateTime createdAt;

    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderList {
        private Integer listSize;
        private Integer page;
        private Long totalElement;
        private Boolean isFirst;
        private Boolean isLast;
        private List<OrderItemResponseDTO.OrderItem> orderItemList;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderListWithSum {
        private Integer listSize;
        private Integer page;
        private Long totalElement;
        private Boolean isFirst;
        private Boolean isLast;
        private List<OrderItemResponseDTO.OrderItemWithSum> orderGroupItemList;
    }

    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderItemWithSum {

        private Long id;

        private Long orderId;

        private ItemResponseDTO.Item item;

        private Integer quantity;

        private OrderStatus status;

        private LocalDateTime createdAt;

        private Integer targetQuantity;

        private Integer currentSum;

    }
}
