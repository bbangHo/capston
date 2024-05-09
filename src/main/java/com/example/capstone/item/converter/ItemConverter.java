package com.example.capstone.item.converter;

import com.example.capstone.item.Item;
import com.example.capstone.item.ItemImage;
import com.example.capstone.item.dto.ItemResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.util.Optionals;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.capstone.item.converter.ItemImageConverter.toItemImageList;

public class ItemConverter {

    public static ItemResponseDTO.Item toItemResponseDTO(Item item) {
        return ItemResponseDTO.Item.builder()
                .id(item.getId())
                .name(item.getName())
                .category(item.getCategory().getName())
                .stock(item.getStock())
                .price(item.getPrice())
                .discountPrice(0)   // TODO: 할인 어떻게?
                .imageUrl(item.getItemImages().isEmpty() ? null : item.getItemImages().get(0).getImageUrl())
                .deadline(item.getDeadline())
                .build();
    }

    public static ItemResponseDTO.ItemList toItemList (Page<Item> itemPage) {
        List<ItemResponseDTO.Item> itemList = itemPage.stream()
                .map(ItemConverter::toItemResponseDTO)
                .toList();

        return ItemResponseDTO.ItemList.builder()
                .listSize(itemPage.getSize())
                .page(itemPage.getTotalPages())
                .totalElement(itemPage.getTotalElements())
                .isFirst(itemPage.isFirst())
                .isLast(itemPage.isLast())
                .itemList(itemList)
                .build();
    }

    public static ItemResponseDTO.DetailsOfItem toDetailsOfItemResponseDTO(Item item) {
        return ItemResponseDTO.DetailsOfItem.builder()
                .id(item.getId())
                .name(item.getName())
                .category(item.getCategory().getName())
                .stock(item.getStock())
                .price(item.getPrice())
                .discountPrice(0)   // TODO: 할인 어떻게?
                .ItemDetailsImageUrl(item.getItemDetailsImageUrl())
                .imageUrl(toItemImageList(item.getItemImages()))
                .deadline(item.getDeadline())
                .build();
    }


}
