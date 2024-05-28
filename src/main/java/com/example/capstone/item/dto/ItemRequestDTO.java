package com.example.capstone.item.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.joda.time.DateTime;

import java.time.LocalDateTime;

public class ItemRequestDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ItemUpload {
        @NotNull(message = "상품 이름은 필수입니다.")
        private String itemName;
        private String simpleExplanation;

        @Positive
        @Range(min = 1, max = 7, message = "카테고리 id는 1 ~ 7 사이입니다. (과일 = 1, 채소 = 2, 축산 = 3, 쌀/잡곡 = 4, 가공 = 5, 김치 = 6, 기타 = 7")
        private Long categoryId;

        @Min(value = 0, message = "상품 가격은 0원 이상입니다")
        private Integer price;

        @Min(value = 1, message = "상품 재고는 1개 이상입니다.")
        private Integer stock;

        @Min(value = 0, message = "배달비는 0원 이상입니다.")
        private Integer deliveryPrice;

        @NotNull(message = "공동 구매 여부는 필수입니다. (false = 일반 상품, true = 공동구매 상품)")
        private Boolean isGroupPurchase;

        @Min(value = 1, message = "공동 구매 목표 인원은 1명 이상입니다.")
        private Integer targetQuantity;

        @Min(value = 0, message = "상품 가격은 0원 이상입니다")
        private Integer groupPurchasePrice;

        private String detailExplanation;

        private LocalDateTime deadLine;
    }
}
