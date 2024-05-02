package com.example.capstone.item.service;

import com.example.capstone.item.Category;
import com.example.capstone.item.Item;
import com.example.capstone.item.common.ItemType;
import com.example.capstone.item.dto.ItemResponseDTO;
import com.example.capstone.item.repository.CategoryRepository;
import com.example.capstone.item.repository.ItemRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ItemServiceTest {

    @Autowired
    ItemService itemService;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ItemRepository itemRepository;

    void setUp() {
        category1 = categoryRepository.save(new Category(1L, "과일"));
        Category category2 = categoryRepository.save(new Category(2L, "야채"));

        for (int i = 0; i < 10; i++) {
            Item item = itemRepository.save(Item.builder()
                    .category(category1)
                    .type(ItemType.COMMON)
                    .name("상품" + i)
                    .price(i + 10000)
                    .deliveryCharge(i + 1000)
                    .stock(i)
                    .deadline(LocalDateTime.parse("2024-12-31T00:00:00.000000"))
                    .ItemDetailsImageUrl("img")
                    .build());
        }

        for (int i = 10; i < 20; i++) {
            Item item = itemRepository.save(Item.builder()
                    .category(category2)
                    .type(ItemType.COMMON)
                    .name("상품" + i)
                    .price(i + 10000)
                    .deliveryCharge(i + 1000)
                    .stock(i)
                    .deadline(LocalDateTime.parse("2024-12-31T00:00:00.000000"))
                    .ItemDetailsImageUrl("img")
                    .build());
        }
    }

    static Category category1;

    @Test
    @DisplayName("카테고리 별로 아이템 리스트를 가져온다.")
    @Transactional
    void getItemListTest() {
        setUp();

        ItemResponseDTO.ItemList itemList = itemService.getItemList(category1.getId(), 0, 10);

        for (ItemResponseDTO.Item item : itemList.getItemList()) {
            assertThat(item.getCategory()).isEqualTo(category1.getName());
        }
    }
}