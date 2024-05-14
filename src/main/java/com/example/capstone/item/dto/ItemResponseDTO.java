package com.example.capstone.item.dto;

import com.example.capstone.seller.Seller;
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

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DetailsOfItem {
        private Long id;
        private String name;
        private String category;
        private Integer stock;
        private Integer price;
        private Integer discountPrice;
        private String ItemDetailsImageUrl;
        private List<ItemImageResponseDTO.ItemImage> imageUrl;
        private LocalDateTime deadline;
    }
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DetailsOfItemWithSeller {

        private Long id;
        private String name;
        private Long sellerId;
        private String category;
        private Integer stock;
        private Integer price;
        private Integer discountPrice;
        private String ItemDetailsImageUrl;
        private List<ItemImageResponseDTO.ItemImage> imageUrl;
        private LocalDateTime deadline;
    }
}
