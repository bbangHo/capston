package com.example.capstone.item.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ItemResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Item {
        private Long id;
        private String name;
        private String category;
        private Integer stock;
        private Integer price;
        private Integer DiscountPrice;
        private String imageUrl;
    }
}
