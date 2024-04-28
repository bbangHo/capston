package com.example.capstone.item.service;

import com.example.capstone.apiPayload.code.status.ErrorStatus;
import com.example.capstone.exception.GeneralException;
import com.example.capstone.item.Category;
import com.example.capstone.item.Item;
import com.example.capstone.item.converter.ItemConverter;
import com.example.capstone.item.dto.ItemResponseDTO;
import com.example.capstone.item.repository.CategoryRepository;
import com.example.capstone.item.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ItemService {
    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;

    public List<ItemResponseDTO.Item> getItemList(Long categoryId, Integer page, Integer size) {
        Category category = categoryValid(categoryId);
        Page<Item> itemPage = itemRepository.findByCategoryId(category.getId(), PageRequest.of(page, size));
        return ItemConverter.toItemList(itemPage);
    }

    private Category categoryValid(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.ITEM_CATEGORY_NOT_FOUND));
    }
}
