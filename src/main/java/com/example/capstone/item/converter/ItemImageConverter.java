package com.example.capstone.item.converter;

import com.example.capstone.item.Item;
import com.example.capstone.item.ItemImage;
import com.example.capstone.item.dto.ItemImageResponseDTO;
import com.example.capstone.item.dto.ItemResponseDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public class ItemImageConverter {

    public static ItemImageResponseDTO.ItemImage toItemImageDTO (ItemImage itemImage) {

        return ItemImageResponseDTO.ItemImage.builder()
                .id(itemImage.getId())
                .itemId(itemImage.getItem().getId())
                .imageUrl(itemImage.getImageUrl())
                .build();
    }

    public static List<ItemImageResponseDTO.ItemImage> toItemImageList (List<ItemImage> itemImages) {
        return itemImages.stream()
                .map(ItemImageConverter::toItemImageDTO)
                .toList();
    }

}
