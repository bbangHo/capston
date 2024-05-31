package com.example.capstone.order.dto;

import lombok.*;

import java.util.List;

public class PaymentDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class itemInfo {
        private Long itemId;
        private Integer quantity;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {
        private List<itemInfo> itemInfoList;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private Long orderId;   // 주문 번호
    }
}
