package com.example.capstone.order.dto;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class CartResponseDTO {

    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class cart {

        private Long id;

        private Long itemId;

        private Long memberId;

        private Integer quantity;

    }


}
