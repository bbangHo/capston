package com.example.capstone.item.service;

import com.example.capstone.apiPayload.code.status.ErrorStatus;
import com.example.capstone.exception.GeneralException;
import com.example.capstone.item.Category;
import com.example.capstone.item.GroupPurchaseItem;
import com.example.capstone.item.Item;
import com.example.capstone.item.converter.GroupItemConverter;
import com.example.capstone.item.converter.ItemConverter;
import com.example.capstone.item.dto.GroupItemResponseDTO;
import com.example.capstone.item.dto.ItemResponseDTO;
import com.example.capstone.item.repository.CategoryRepository;
import com.example.capstone.item.repository.GroupPurchaseItemRepository;
import com.example.capstone.item.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class GroupPurchaseItemServiceImpl implements GroupPurchaseItemService {

    private final GroupPurchaseItemRepository groupPurchaseItemRepository;

    public GroupItemResponseDTO.GroupItemList getGroupItemList(Integer page, Integer size) {
        Page<GroupPurchaseItem> groupItemPage = groupPurchaseItemRepository.searchGroupPurchaseItem(PageRequest.of(page, size));
        return GroupItemConverter.toGroupItemList(groupItemPage);
    }
}
