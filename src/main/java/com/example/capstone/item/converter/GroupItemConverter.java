package com.example.capstone.item.converter;

import com.example.capstone.item.GroupPurchaseItem;
import com.example.capstone.item.dto.GroupItemResponseDTO;
import org.springframework.data.domain.Page;

import java.util.List;

import static com.example.capstone.item.converter.ItemConverter.*;


public class GroupItemConverter {


    public static GroupItemResponseDTO.GroupItem toGroupItemResponseDTO(GroupPurchaseItem groupPurchaseItem) {
        return GroupItemResponseDTO .GroupItem.builder()
                .id(groupPurchaseItem.getId())
                .item(toItemResponseDTO(groupPurchaseItem.getItem()))
                .targetQuantity(groupPurchaseItem.getTargetQuantity())
                .discountPrice(groupPurchaseItem.getDiscountPrice())
                .build();
    }

    public static GroupItemResponseDTO.GroupItemList toGroupItemList (Page<GroupPurchaseItem> groupItemPage) {
        List<GroupItemResponseDTO.GroupItem> groupItemList = groupItemPage.stream()
                .map(GroupItemConverter::toGroupItemResponseDTO)
                .toList();


        return GroupItemResponseDTO.GroupItemList.builder()
                .listSize(groupItemPage.getSize())
                .page(groupItemPage.getTotalPages())
                .totalElement(groupItemPage.getTotalElements())
                .isFirst(groupItemPage.isFirst())
                .isLast(groupItemPage.isLast())
                .groupItemList(groupItemList)
                .build();
    }

    public static GroupItemResponseDTO.GroupItemWithSellerAndRemains toGroupItemSellerAndRemainsResponseDTO(Integer orderSum, GroupPurchaseItem groupPurchaseItem) {
        return GroupItemResponseDTO.GroupItemWithSellerAndRemains.builder()
                .id(groupPurchaseItem.getId())
                .item(toDetailsOfItemWithSellerResponseDTO(groupPurchaseItem.getItem()))
                .orderSum(orderSum)
                .targetQuantity(groupPurchaseItem.getTargetQuantity())
                .discountPrice(groupPurchaseItem.getDiscountPrice())
                .build();
    }

}
