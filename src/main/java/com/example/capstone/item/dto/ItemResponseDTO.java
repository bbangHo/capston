package com.example.capstone.item.dto;

import com.example.capstone.seller.dto.SellerResponseDTO;
import com.example.capstone.seller.dto.TempSellerResponseDTO;
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
    public static class ItemStatus {
        private Long orderItemId;
        private Long itemId;
        private String itemName;
        private Integer itemPrice;
        private Integer quantity;
        private Integer deliveryCharge;
        private Integer totalPrice;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ItemStatusList {
        private Long orderId;
        private List<ItemStatus> itemStatusList;
    }

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
    public static class GroupPurchaseInfo {
        private Integer targetQuantity;
        private Integer discountPrice;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ItemUpload {
        private DetailsOfItem item;
        private GroupPurchaseInfo groupPurchaseInfo;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DetailsOfItem {
        private Long id;
        private Long memberId;  // 판매자의 id (판재마 소개페이지 이동용)
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
    public static class ItemWithSeller {
        private Long id;
        private String name;
        private String category;
        private Integer stock;
        private Integer price;
        private Integer discountPrice;
        private String imageUrl;
        private LocalDateTime deadline;
        private Integer deliveryCharge;
        private TempSellerResponseDTO.Seller seller;
    }
}
