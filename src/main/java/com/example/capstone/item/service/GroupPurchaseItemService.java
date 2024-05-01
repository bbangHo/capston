package com.example.capstone.item.service;

import com.example.capstone.item.dto.GroupItemResponseDTO;

public interface GroupPurchaseItemService {
    GroupItemResponseDTO.GroupItemList getGroupItemList(Integer page, Integer size);
}
