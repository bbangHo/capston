package com.example.capstone.seller.dto;

import com.example.capstone.item.common.ItemType;
import com.example.capstone.order.common.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class SellerResponseDTO {

    /**
     * 판매자의 상품 주문 현황을 응답해 줄때 사용 하는 객체
     */
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderItemStatus {
        private Long orderNumber;   // 주문번호
        private String itemName;
        private String itemPreviewImageUrl; // 상품 미리보기 이미지
        private LocalDateTime orderedDate;  // 주문 일자
        private String consumerName;
        private Integer price;
        private Integer quantity;
        private OrderStatus status;    //
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderStatusList {
        private Integer listSize;
        private Integer page;
        private Long totalElement;
        private Boolean isFirst;
        private Boolean isLast;
        private List<OrderItemStatus> orderItemStatusList;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SalesItem {
        private String itemName;
        private ItemType itemType;
        private String itemPreviewImageUrl; // 상품 미리보기 이미지
        private String categoryName;
        private Integer stock;
        private Integer price;
        private LocalDateTime deadLine;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SalesItemList {
        private Integer listSize;
        private Integer page;
        private Long totalElement;
        private Boolean isFirst;
        private Boolean isLast;
        private List<SalesItem> orderItemStatusList;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Dashboard {
        private Integer todaySalesVolume;
        private String todaySalesVolumePercent;
        private Integer dayBeforeSalesVolume;
        private Integer monthSalesVolume;
        private String monthSalesVolumePercent;
        private Integer orderStatusNumber;    // 추가된 주문 현황 개수
    }
}
