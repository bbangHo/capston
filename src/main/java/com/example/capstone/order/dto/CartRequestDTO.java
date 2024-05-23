package com.example.capstone.order.dto;



import jakarta.validation.constraints.NotNull;
import lombok.*;

public class CartRequestDTO {


    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class requestedCart {

        @NotNull
        private Long itemId;

        private Long memberId;

        @NotNull
        private Integer quantity;

    }


}
