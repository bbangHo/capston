package com.example.capstone.item.converter;

import com.example.capstone.item.Item;
import com.example.capstone.item.dto.ItemResponseDTO;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class ItemConverter {

    public static ItemResponseDTO.Item toItemResponseDTO(Item item) {
        return ItemResponseDTO.Item.builder()
                .id(item.getId())
                .name(item.getName())
                .category(item.getCategory().getName())
                .stock(item.getStock())
                .price(item.getPrice())
                .DiscountPrice(0)   // TODO: 할인 어떻게?
                .imageUrl(null)
                .build();
    }

    public static List<ItemResponseDTO.Item> toItemList (Page<Item> itemPage) {
        return itemPage.stream()
                .map(ItemConverter::toItemResponseDTO)
                .collect(Collectors.toList());
    }

}
