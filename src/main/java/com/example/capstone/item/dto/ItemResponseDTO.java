package com.example.capstone.item.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

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
        private Integer discountPrice;
        private String imageUrl;
        private LocalDateTime deadline;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ItemList {
        private Integer listSize;
        private Integer page;
        private Long totalElement;
        private Boolean isFirst;
        private Boolean isLast;
        private List<Item> itemList;
    }
}
