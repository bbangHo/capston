package com.example.capstone.item.dto;

import com.example.capstone.item.Item;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class GroupItemResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GroupItem {
        private Long id;
        private ItemResponseDTO.Item item;
        private Integer targetQuantity;
        private Integer discountPrice;

    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GroupItemList {
        private Integer listSize;
        private Integer page;
        private Long totalElement;
        private Boolean isFirst;
        private Boolean isLast;
        private List<GroupItemResponseDTO.GroupItem> groupItemList;
    }
}
