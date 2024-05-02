package com.example.capstone.item.service;

import com.example.capstone.item.dto.ItemResponseDTO;

public interface ItemQueryService {
    public ItemResponseDTO.ItemList searchItemList(String keyword, Integer page, Integer size);
}
