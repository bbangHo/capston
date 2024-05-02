package com.example.capstone.item.service;

import com.example.capstone.item.Item;
import com.example.capstone.item.converter.ItemConverter;
import com.example.capstone.item.dto.ItemResponseDTO;
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
@Transactional(readOnly = true)
public class ItemQueryServiceImpl implements ItemQueryService {
    private final ItemRepository itemRepository;

    /**
     *
     * @param keyword 찾을 상품의 단어 중 일부입니다.
     * @param page 현재 페이지 번호입니다.
     * @param size 가져올 상품의 개수입니다.
     * @return
     */
    @Override
    public ItemResponseDTO.ItemList searchItemList(String keyword, Integer page, Integer size) {
        Page<Item> itemPage = itemRepository.findByNameContaining(keyword, PageRequest.of(page, size));
        return ItemConverter.toItemList(itemPage);
    }
}
